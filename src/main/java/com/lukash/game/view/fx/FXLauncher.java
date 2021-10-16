package com.lukash.game.view.fx;

import com.lukash.game.GameLauncher;
import com.lukash.game.controller.GameController;
import com.lukash.game.model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FXLauncher extends Application {

    private final GameController gameController = GameController.getInstance();

    public static void launch() {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameLauncher.class.getResource("fx-ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        GridPane gridPane = (GridPane) scene.lookup("#gridPane");
        placePlayers(gridPane, gameController.getPlayers());

//        stage.setTitle(activeGame.getName());
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void placePlayers(GridPane gridPane, List<Player> players) {
        players.forEach(p -> FXUtil.drawFigure(gridPane, p.getHero().getPosition(), p.getHero().getFigure()));
    }
}
