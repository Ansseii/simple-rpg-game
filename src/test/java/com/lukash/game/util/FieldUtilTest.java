package com.lukash.game.util;

import com.lukash.game.model.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldUtilTest {

    @Test
    void getDistanceBetweenPoints() {
        assertEquals(0, FieldUtil.getDistanceBetweenPoints(new Point(0, 0), new Point(0, 0)));
        assertEquals(2, FieldUtil.getDistanceBetweenPoints(new Point(0, 0), new Point(1, 1)));
        assertEquals(2, FieldUtil.getDistanceBetweenPoints(new Point(1, 1), new Point(0, 0)));
        assertEquals(1, FieldUtil.getDistanceBetweenPoints(new Point(1, 1), new Point(0, 1)));
        assertEquals(3, FieldUtil.getDistanceBetweenPoints(new Point(4, 1), new Point(1, 1)));
    }
}