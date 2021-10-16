package com.lukash.game.model;

public enum Figure {
    FIGURE_1("♖"), FIGURE_2("♜");

    private final String logo;

    Figure(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }
}
