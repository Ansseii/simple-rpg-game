package com.lukash.game.model;

public enum Equipment {
    SWORD(new Effect(0, 10)),
    STONE(new Effect(3, 6)),
    POTION(new Effect(7, 7));

    private final Effect effect;

    Equipment(Effect effect) {
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }

    public record Effect(int max, int min) {}
}
