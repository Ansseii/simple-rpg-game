package com.lukash.game.controller;

import com.lukash.game.exceptions.InvalidPointException;
import com.lukash.game.model.Field;
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
        this.gameController = new GameController();
        this.movementController = new MovementController();
    }

    @Test
    void checkStartPosition() {
        Point first = new Point(0, 0);
        Point second = new Point(9, 9);
        Field field = gameController.getField();

        assertEquals(Figure.FIGURE_1, field.getFigure(first));
        assertEquals(Figure.FIGURE_2, field.getFigure(second));
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