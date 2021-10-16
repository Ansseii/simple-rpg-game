package com.lukash.game.view.fx;

import com.lukash.game.GameLauncher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FXLauncher extends Application {

    public static void launch() {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GameLauncher.class.getResource("fx-ui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
