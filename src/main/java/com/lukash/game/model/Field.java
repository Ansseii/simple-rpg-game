package com.lukash.game.model;

import com.lukash.game.exceptions.InvalidPointException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Field {

    private final int size;
    private final Figure[][] storage;

    public Field(int size) {
        this.size = size;
        this.storage = new Figure[size][size];
    }

    public int getSize() {
        return size;
    }

    public Figure getFigure(Point point) {
        if (isInvalidPoint(point)) throw new InvalidPointException("Invalid point was specified");

        return storage[point.y()][point.x()];
    }

    public void setFigure(Point point, Figure figure) {
        if (isInvalidPoint(point)) throw new InvalidPointException("Invalid point was specified");

        storage[point.y()][point.x()] = figure;
    }

    public Set<Point> getAvailablePoints(Point start, int steps) {
        if (steps <= 0) return Collections.emptySet();
        if (steps == 1) return getNeighbours(start);

        return getNeighbours(start).stream()
                .flatMap(p -> getAvailablePoints(p, steps - 1).stream())
                .collect(Collectors.toSet());
    }

    private Set<Point> getNeighbours(Point start) {

        Point actual = new Point(roundToBorders(start.x()), roundToBorders(start.y()));
        Point up = new Point(roundToBorders(start.x()), roundToBorders(start.y() - 1));
        Point down = new Point(roundToBorders(start.x()), roundToBorders(start.y() + 1));
        Point right = new Point(roundToBorders(start.x() + 1), roundToBorders(start.y()));
        Point left = new Point(roundToBorders(start.x() - 1), roundToBorders(start.y()));

        return Stream.of(actual, up, down, right, left)
                .collect(Collectors.toSet());
    }

    private int roundToBorders(int coordinate) {
        if (coordinate < 0) return 0;
        if (coordinate >= size) return size - 1;
        return coordinate;
    }

    private boolean isInvalidPoint(Point point) {
        return isOutOfBorder(point.x()) || isOutOfBorder(point.y());
    }

    private boolean isOutOfBorder(int coordinate) {
        return coordinate < 0 || coordinate >= size;
    }
}
