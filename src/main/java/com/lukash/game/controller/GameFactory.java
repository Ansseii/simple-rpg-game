package com.lukash.game.controller;

import com.lukash.game.exceptions.GameStateException;
import com.lukash.game.model.Field;
import com.lukash.game.model.Hero;
import com.lukash.game.model.Player;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class GameFactory {

    private static Game INSTANCE;

    protected static void createNewGame(List<Player> players, Field field) {
        INSTANCE = new Game(players, field);
    }

    protected static Game getActiveGame() {
        return Optional.ofNullable(INSTANCE)
                .orElseThrow(() -> new GameStateException("No active game available"));
    }

    protected static class Game {

        private final List<Player> players;
        private final Field field;
        private final GameState state;

        private Game(List<Player> players, Field field) {
            this.players = players;
            this.field = field;
            this.state = new GameState(players);
            initStartPosition();
        }

        protected List<Player> getPlayers() {
            return players;
        }

        protected Field getField() {
            return field;
        }

        protected GameState getState() {
            return state;
        }

        private void initStartPosition() {
            players.forEach(p -> {
                Hero hero = p.getHero();
                field.setFigure(hero.getPosition(), hero.getFigure());
            });
        }
    }

    protected static class GameState {
        private static final int AVAILABLE_STEPS = 4;

        private final Deque<Player> queue = new LinkedList<>();
        private Player winner;
        private Player activePlayer;
        private Player enemyPlayer;
        private boolean inventoryUsed;
        private int steps;

        private GameState(List<Player> players) {
            this.queue.addAll(players);
            endTurn();
        }

        protected boolean isInventoryUsed() {
            return inventoryUsed;
        }

        protected void setInventoryUsed() {
            this.inventoryUsed = true;
        }

        protected void endTurn() {
            swapPlayers();
            this.steps = AVAILABLE_STEPS;
            this.inventoryUsed = false;
        }

        protected Optional<Player> getWinner() {
            return Optional.ofNullable(winner);
        }

        protected void setWinner(Player winner) {
            this.winner = winner;
        }

        protected Player getActivePlayer() {
            return activePlayer;
        }

        protected Player getEnemyPlayer() {
            return enemyPlayer;
        }

        protected int getSteps() {
            return steps;
        }

        protected void minusSteps(int steps) {
            int updated = this.steps - steps;
            this.steps = Math.max(updated, 0);
        }

        private void swapPlayers() {
            Player active = queue.poll();
            this.enemyPlayer = queue.peek();
            queue.addLast(active);
            this.activePlayer = active;
        }
    }
}
