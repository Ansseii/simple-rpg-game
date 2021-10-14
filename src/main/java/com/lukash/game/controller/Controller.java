package com.lukash.game.controller;

import com.lukash.game.model.Field;
import com.lukash.game.model.Game;
import com.lukash.game.model.GameState;

public class Controller {

    protected final GameState gameState;
    protected final Field field;

    public Controller() {
        Game game = Game.getActiveGame();
        this.field = game.getField();
        this.gameState = game.getState();
    }
}
