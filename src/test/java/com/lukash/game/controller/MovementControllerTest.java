package com.lukash.game.controller;

import com.lukash.game.exceptions.InvalidPointException;
import com.lukash.game.model.Figure;
import com.lukash.game.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementControllerTest {

    private GameController gameController;
    private MovementController movementController;

    @BeforeEach
    void setup() {
        GameController.initNewGame(new Point(0, 0), new Point(9, 9));
        this.gameController = GameController.getInstance();
        this.movementController = MovementController.getInstance();
    }

    @Test
    void moveOn4Cells() {
        Point startPoint = new Point(0, 0);
        Point newPoint1 = new Point(1, 1);
        Point newPoint2 = new Point(2, 2);

        movementController.move(newPoint1, System.out::println);
        assertNull(gameController.getField().getFigure(startPoint));
        assertEquals(Figure.FIGURE_1, gameController.getField().getFigure(newPoint1));

        movementController.move(newPoint2, System.out::println);
        assertNull(gameController.getField().getFigure(newPoint1));
        assertEquals(Figure.FIGURE_1, gameController.getField().getFigure(newPoint2));
    }

    @Test
    void moveMoreThanOn4Cells() {
        Point newPoint1 = new Point(6, 6);

        assertThrows(InvalidPointException.class, () -> movementController.move(newPoint1, System.out::println));
    }
}