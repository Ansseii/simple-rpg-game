package com.lukash.game.model;

public enum Equipment {
    SWORD(new Range(new Effect(0.7, 5), new Effect(1., 0))),
    STONE(new Range(new Effect(0.6, 4), new Effect(0.45, 3))),
    POTION(new Range(new Effect(1., 7), new Effect(1., 7)));

    private final Range range;

    Equipment(Range range) {
        this.range = range;
    }

    public Range getEffect() {
        return range;
    }

    public record Range(Effect max, Effect min) {
    }

    public record Effect(double chance, int value) {
    }
}
