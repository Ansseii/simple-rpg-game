package com.lukash.game.controller;

import com.lukash.game.model.Field;
import com.lukash.game.model.Figure;
import com.lukash.game.model.Player;
import com.lukash.game.model.Point;

import java.util.List;

// TODO test
public class GameController {

    private static GameController INSTANCE;

    private final GameFactory.Game game;
    private final GameFactory.GameState gameState;

    private GameController() {
        this.game = GameFactory.getActiveGame();
        this.gameState = GameFactory.getActiveGame().getState();
    }

    public static GameController getInstance() {
        return INSTANCE == null ? new GameController() : INSTANCE;
    }

    public static void initNewGame(Point activePlayer, Point enemyPlayer) {
        List<Player> players = List.of(
                new Player("PLAYER_1", Figure.FIGURE_1, activePlayer),
                new Player("PLAYER_2", Figure.FIGURE_2, enemyPlayer)
        );
        createNewGame(players, new Field(10));
    }

    private static void createNewGame(List<Player> players, Field field) {
        GameFactory.createNewGame(players, field);
    }

    protected Field getField() {
        return game.getField();
    }

    protected void minusSteps(int steps){
        gameState.minusSteps(steps);
    }

    protected int getSteps() {
        return gameState.getSteps();
    }

    public List<Player> getPlayers() {
        return game.getPlayers();
    }

    public Player getActivePlayer() {
        return gameState.getActivePlayer();
    }

    protected Player getEnemyPlayer() {
        return gameState.getEnemyPlayer();
    }

    protected boolean isInventoryUsed() {
        return gameState.isInventoryUsed();
    }

    protected void setInventoryUsed() {
        gameState.setInventoryUsed();
    }

    public void endTurn() {
        gameState.endTurn();
    }
}
