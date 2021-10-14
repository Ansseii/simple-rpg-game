package com.lukash.game.controller;

import com.lukash.game.exceptions.InvalidPointException;
import com.lukash.game.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovementControllerTest {

    private MovementController controller;

    @BeforeEach
    void setup() {
        List<Player> players = List.of(
                new Player("PLAYER_1", Figure.FIGURE_1, new Point(0, 0)),
                new Player("PLAYER_2", Figure.FIGURE_2, new Point(9, 9))
        );
        Game.createNewGame("Test Game", players, new Field(10));
        this.controller = new MovementController();
    }

    @Test
    void moveOn4Cells() {
        Point startPoint = new Point(0, 0);
        Point newPoint1 = new Point(1, 1);
        Point newPoint2 = new Point(2, 2);

        controller.move(newPoint1, System.out::println);
        assertNull(controller.field.getFigure(startPoint));
        assertEquals(Figure.FIGURE_1, controller.field.getFigure(newPoint1));

        controller.move(newPoint2, System.out::println);
        assertNull(controller.field.getFigure(newPoint1));
        assertEquals(Figure.FIGURE_1, controller.field.getFigure(newPoint2));
    }

    @Test
    void moveMoreThanOn4Cells() {
        Point newPoint1 = new Point(6, 6);

        assertThrows(InvalidPointException.class, () -> controller.move(newPoint1, System.out::println));
    }
}