package com.lukash.game.model;

public class Hero {

    private Point position;
    private final Figure figure;

    public Hero(Figure figure, Point position) {
        this.figure = figure;
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Figure getFigure() {
        return figure;
    }
}
