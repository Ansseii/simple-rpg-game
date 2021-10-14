package com.lukash.game.model;

import com.lukash.game.exceptions.InvalidPointException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.lukash.game.model.Figure.FIGURE_1;
import static org.junit.jupiter.api.Assertions.*;

class FieldTest {

    @Test
    void getSize() {
        Field field = new Field(10);

        assertEquals(10, field.getSize());
    }

    @Test
    void getAvatarOnEmptyCell() {
        Field field = new Field(10);
        Point coordinates = new Point(0, 0);

        assertNull(field.getFigure(coordinates));
    }

    @Test
    void setAvatar() {
        Field field = new Field(10);
        Point coordinates = new Point(2, 3);
        field.setFigure(coordinates, FIGURE_1);

        assertEquals(FIGURE_1, field.getFigure(coordinates));
    }

    @Test
    void getAvatarWithInvalidCoordinates() {
        Field field = new Field(10);

        Point invalidX1 = new Point(-1, 4);
        Point invalidX2 = new Point(10, 4);

        Point invalidY1 = new Point(3, -1);
        Point invalidY2 = new Point(4, 10);

        Point invalidBoth = new Point(-1, 10);

        assertThrows(InvalidPointException.class, () -> field.getFigure(invalidX1));
        assertThrows(InvalidPointException.class, () -> field.getFigure(invalidX2));
        assertThrows(InvalidPointException.class, () -> field.getFigure(invalidY1));
        assertThrows(InvalidPointException.class, () -> field.getFigure(invalidY2));
        assertThrows(InvalidPointException.class, () -> field.getFigure(invalidBoth));
    }

    @Test
    void getAvailablePoints() {
        Field field = new Field(10);

        Point centerPoint = new Point(4, 4);
        Point borderXPoint = new Point(0, 4);
        Point borderYPoint = new Point(4, 0);
        Point invalidPointMax = new Point(10, 10);
        Point invalidPointMin = new Point(-1, -1);

        assertEquals(
                Set.of(
                        new Point(4, 4),
                        new Point(4, 5),
                        new Point(3, 4),
                        new Point(4, 3),
                        new Point(5, 4)
                ), field.getAvailablePoints(centerPoint, 1));
        assertEquals(
                Set.of(
                        new Point(4, 4),
                        new Point(5, 5),
                        new Point(3, 3),
                        new Point(4, 6),
                        new Point(3, 5),
                        new Point(2, 4),
                        new Point(4, 2),
                        new Point(5, 3),
                        new Point(6, 4),
                        new Point(4, 5),
                        new Point(3, 4),
                        new Point(4, 3),
                        new Point(5, 4)
                ), field.getAvailablePoints(centerPoint, 2));
        assertEquals(
                Set.of(
                        new Point(0, 3),
                        new Point(1, 4),
                        new Point(0, 4),
                        new Point(0, 5)
                ), field.getAvailablePoints(borderXPoint, 1));
        assertEquals(
                Set.of(
                        new Point(5, 0),
                        new Point(4, 0),
                        new Point(4, 1),
                        new Point(3, 0)
                ), field.getAvailablePoints(borderYPoint, 1));

        assertEquals(Set.of(new Point(9, 9)), field.getAvailablePoints(invalidPointMax, 1));
        assertEquals(Set.of(new Point(0, 0)), field.getAvailablePoints(invalidPointMin, 1));
    }
}