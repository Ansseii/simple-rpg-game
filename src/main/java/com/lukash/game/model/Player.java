package com.lukash.game.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name) && hero.equals(player.hero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hero);
    }
}
