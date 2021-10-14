package com.lukash.game.controller;

import com.lukash.game.model.Player;

// TODO test
public class GameController extends Controller {

    public Player getActivePlayer() {
        return gameState.getActivePlayer();
    }

    public void endTurn() {
        gameState.endTurn();
    }
}
