package com.lukash.game.controller;

import com.lukash.game.model.*;

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
        GameFactory.createNewGame(players, new Field(10));
    }

    public Summary getSummary() {
        return new Summary(game.getPlayers(), gameState.getActivePlayer(), gameState.getWinner());
    }

    public void findWinner() {
        Player activePlayer = gameState.getActivePlayer();
        Hero enemyHero = gameState.getEnemyPlayer().getHero();

        if (enemyHero.getHp() == 0) {
            gameState.setWinner(activePlayer);
        }
    }

    public List<Player> getPlayers() {
        return game.getPlayers();
    }

    public Player getActivePlayer() {
        return gameState.getActivePlayer();
    }

    public void endTurn() {
        gameState.endTurn();
    }

    protected Field getField() {
        return game.getField();
    }

    protected void minusSteps(int steps) {
        gameState.minusSteps(steps);
    }

    protected int getSteps() {
        return gameState.getSteps();
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
}
