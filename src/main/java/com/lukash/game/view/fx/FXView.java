package com.lukash.game.view.fx;

import com.lukash.game.exceptions.InvalidPointException;
import com.lukash.game.model.Hero;
import com.lukash.game.model.Player;
import com.lukash.game.model.Point;
import com.lukash.game.view.View;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class FXView extends View {

    @FXML
    private Label warnings;

    @FXML
    private Label info;

    @FXML
    private GridPane gridPane;

    @Override
    public void show() {
        FXLauncher.launch();
    }

    @FXML
    private void onMouseMoved() {
        FXUtil.setValue(info, getActivePlayerMessage());
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
                gameController.endTurn();
            } catch (InvalidPointException e) {
                FXUtil.setValue(warnings, e.getMessage());
            }
        }
    }

    private void highlightArea() {
        area.addAll(movementController.getAvailableArea());
        area.forEach(p -> FXUtil.highlight(gridPane, p));
    }

    private void clearArea() {
        area.forEach(p -> FXUtil.clear(gridPane, p));
        area.clear();
    }

    private String getActivePlayerMessage() {
        Player activePlayer = gameController.getActivePlayer();
        return String.format("%s (%s) TURN", activePlayer.getName(), activePlayer.getHero().getFigure().getLogo());
    }
}
