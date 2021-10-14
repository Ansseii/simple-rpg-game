package com.lukash.game.model;

public class Player {

    private final String name;
    private final Hero hero;

    public Player(String name, Figure figure, Point startPoint) {
        this.name = name;
        this.hero = new Hero(figure, startPoint);
    }

    public String getName() {
        return name;
    }

    public Hero getHero() {
        return hero;
    }
}
