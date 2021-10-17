package com.lukash.game.controller;

import com.lukash.game.model.*;

import java.util.List;

public class GameController {

    public static void initNewGame(Point activePlayer, Point enemyPlayer) {
        List<Player> players = List.of(
                new Player("PLAYER_1", Figure.FIGURE_1, activePlayer),
                new Player("PLAYER_2", Figure.FIGURE_2, enemyPlayer)
        );
        GameFactory.createNewGame(players, new Field(10));
    }

    public Summary getSummary() {
        return new Summary(
                GameFactory.getActiveGame().getPlayers(),
                GameFactory.getActiveGame().getState().getActivePlayer(),
                GameFactory.getActiveGame().getState().getWinner()
        );
    }

    public void findWinner() {
        Player activePlayer = GameFactory.getActiveGame().getState().getActivePlayer();
        Hero enemyHero = GameFactory.getActiveGame().getState().getEnemyPlayer().getHero();

        if (enemyHero.getHp() == 0) {
            GameFactory.getActiveGame().getState().setWinner(activePlayer);
        }
    }

    public List<Player> getPlayers() {
        return GameFactory.getActiveGame().getPlayers();
    }

    public Player getActivePlayer() {
        return GameFactory.getActiveGame().getState().getActivePlayer();
    }

    public void endTurn() {
        GameFactory.getActiveGame().getState().endTurn();
    }

    protected Field getField() {
        return GameFactory.getActiveGame().getField();
    }

    protected void minusSteps(int steps) {
        GameFactory.getActiveGame().getState().minusSteps(steps);
    }

    protected int getSteps() {
        return GameFactory.getActiveGame().getState().getSteps();
    }

    protected Player getEnemyPlayer() {
        return GameFactory.getActiveGame().getState().getEnemyPlayer();
    }

    protected boolean isInventoryUsed() {
        return GameFactory.getActiveGame().getState().isInventoryUsed();
    }

    protected void setInventoryUsed() {
        GameFactory.getActiveGame().getState().setInventoryUsed();
    }
}
