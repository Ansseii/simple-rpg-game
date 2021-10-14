package com.lukash.game.view.fx;

import com.lukash.game.GameLauncher;
import com.lukash.game.model.Game;
import com.lukash.game.model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FXLauncher extends Application {

    public static void launch() {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameLauncher.class.getResource("fx-ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Game activeGame = Game.getActiveGame();
        GridPane gridPane = (GridPane) scene.lookup("#gridPane");
        placePlayers(gridPane, activeGame.getPlayers());

        stage.setTitle(activeGame.getName());
        stage.setScene(scene);
        stage.show();
    }

    private void placePlayers(GridPane gridPane, List<Player> players) {
        players.forEach(p -> FXUtil.drawFigure(gridPane, p.getHero().getPosition(), p.getHero().getFigure()));
    }
}
