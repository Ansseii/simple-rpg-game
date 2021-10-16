package com.lukash.game.view.fx;

import com.lukash.game.exceptions.GameStateException;
import com.lukash.game.exceptions.InvalidPointException;
import com.lukash.game.model.Equipment;
import com.lukash.game.model.Hero;
import com.lukash.game.model.Point;
import com.lukash.game.model.Summary;
import com.lukash.game.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class FXView extends View {

    @FXML
    private Pane gameInterface;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button newGameBtn;

    @FXML
    private Label winner;

    @FXML
    private Label player1Health;

    @FXML
    private Label player1Stones;

    @FXML
    private Label player1Potions;

    @FXML
    private Label player1CombatLog;

    @FXML
    private Label player2Health;

    @FXML
    private Label player2Stones;

    @FXML
    private Label player2Potions;

    @FXML
    private Label player2CombatLog;

    @FXML
    private Label warnings;

    @FXML
    private Label info;

    @Override
    public void show() {
        FXLauncher.launch();
    }

    @FXML
    private void startNewGame() {
        updateInfo();
        gameInterface.setDisable(false);
        newGameBtn.setVisible(false);
    }

    @FXML
    private void onGridEntered() {
        highlightArea();
    }

    @FXML
    private void onGridExited() {
        clearArea();
    }

    @FXML
    private void onGridClick(MouseEvent event) {
        FXUtil.setValue(warnings, null);
        Node source = event.getPickResult().getIntersectedNode();

        Integer colIdx = GridPane.getColumnIndex(source);
        Integer rowIdx = GridPane.getRowIndex(source);

        if (Objects.nonNull(colIdx) && Objects.nonNull(rowIdx)) {
            Point selected = new Point(colIdx, rowIdx);

            try {
                movementController.move(selected, () -> {
                    clearArea();
                    Hero activeHero = gameController.getActivePlayer().getHero();
                    Point current = activeHero.getPosition();
                    FXUtil.setValue(warnings, null);
                    FXUtil.drawFigure(gridPane, current, null);
                    FXUtil.drawFigure(gridPane, selected, activeHero.getFigure());
                });
                highlightArea();
            } catch (InvalidPointException e) {
                FXUtil.setValue(warnings, e.getMessage());
            }
        }
    }

    @FXML
    private void swordAttack() {
        useFromInventory(Equipment.SWORD);
    }

    @FXML
    private void throwStone() {
        useFromInventory(Equipment.STONE);
    }

    @FXML
    private void usePotion() {
        useFromInventory(Equipment.POTION);
    }

    @FXML
    private void endTurn() {
        FXUtil.setValue(warnings, null);
        FXUtil.setValue(player1CombatLog, null);
        FXUtil.setValue(player2CombatLog, null);
        gameController.endTurn();
        updateInfo();
    }

    private void useFromInventory(Equipment equipment) {
        try {
            FXUtil.setValue(warnings, null);
            boolean success = fightController.useInventory(equipment);
            updateCombatLog(success);
            updateInfo();
        } catch (GameStateException e) {
            FXUtil.setValue(warnings, e.getMessage());
        }
    }

    private void updateCombatLog(boolean success) {
        String text = success ? "Success" : "Miss";
        switch (gameController.getActivePlayer().getName()) {
            case "PLAYER_1" -> FXUtil.setValue(player1CombatLog, text);
            case "PLAYER_2" -> FXUtil.setValue(player2CombatLog, text);
        }
    }

    private void updateInfo() {
        Summary summary = gameController.getSummary();

        FXUtil.setValue(info, summary.getActivePlayerMessage());
        FXUtil.setValue(player1Health, String.valueOf(summary.getPlayer1State().hp()));
        FXUtil.setValue(player1Stones, String.valueOf(summary.getPlayer1State().stones()));
        FXUtil.setValue(player1Potions, String.valueOf(summary.getPlayer1State().potions()));

        FXUtil.setValue(player2Health, String.valueOf(summary.getPlayer2State().hp()));
        FXUtil.setValue(player2Stones, String.valueOf(summary.getPlayer2State().stones()));
        FXUtil.setValue(player2Potions, String.valueOf(summary.getPlayer2State().potions()));

        summary.getWinner().ifPresent(w -> {
            gameInterface.setDisable(true);
            FXUtil.setValue(winner, summary.getWinnerMessage());
        });
    }

    private void highlightArea() {
        area.addAll(movementController.getAvailableArea());
        area.forEach(p -> FXUtil.highlight(gridPane, p));
    }

    private void clearArea() {
        area.forEach(p -> FXUtil.clear(gridPane, p));
        area.clear();
    }
}
