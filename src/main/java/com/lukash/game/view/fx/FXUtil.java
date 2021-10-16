package com.lukash.game.view.fx;

import com.lukash.game.model.Figure;
import com.lukash.game.model.Point;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class FXUtil {

    protected static void setValue(Label label, String text) {
        label.setText(text);
    }

    protected static void drawFigure(GridPane gridPane, Point point, Figure figure) {
        Label label = lookupByPoint(gridPane, point);
        setValue(label, Optional.ofNullable(figure)
                .map(Figure::getLogo)
                .orElse(""));
    }

    protected static void highlight(GridPane gridPane, Point point) {
        Label label = lookupByPoint(gridPane, point);

        label.getStyleClass().add("area");
        label.getStyleClass().remove("cell");
    }

    protected static void clear(GridPane gridPane, Point point) {
        Label label = lookupByPoint(gridPane, point);

        label.getStyleClass().add("cell");
        label.getStyleClass().remove("area");
    }

    private static Label lookupByPoint(GridPane gridPane, Point point) {
        return gridPane.getChildren().stream()
                .filter(node -> GridPane.getColumnIndex(node) == point.x() && GridPane.getRowIndex(node) == point.y())
                .map(Label.class::cast)
                .findFirst()
                .orElseThrow();
    }
}
