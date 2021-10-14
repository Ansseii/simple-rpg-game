module com.lukash.game {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens com.lukash.game.view.fx to javafx.fxml, javafx.graphics;
    exports com.lukash.game;
}