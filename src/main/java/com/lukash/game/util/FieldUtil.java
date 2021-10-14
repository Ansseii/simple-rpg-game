package com.lukash.game.util;

import com.lukash.game.model.Point;

public class FieldUtil {

    public static int getDistanceBetweenPoints(Point first, Point second) {
        int k1 = Math.abs(second.y() - first.y());
        int k2 = Math.abs(second.x() - first.x());

        return k1 + k2;
    }
}
