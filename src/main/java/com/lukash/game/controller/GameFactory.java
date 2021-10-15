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
        }

        public List<Player> getPlayers() {
            return players;
        }

        public Field getField() {
            return field;
        }

        public GameState getState() {
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

        private final Deque<Player> order = new LinkedList<>();
        private Player activePlayer;
        private Player enemyPlayer;
        private boolean inventoryUsed;
        private int steps;

        private GameState(List<Player> players) {
            this.order.addAll(players);
            endTurn();
        }

        public boolean isInventoryUsed() {
            return inventoryUsed;
        }

        public void setInventoryUsed() {
            this.inventoryUsed = true;
        }

        public void endTurn() {
            swapPlayers();
            this.steps = AVAILABLE_STEPS;
            this.inventoryUsed = false;
        }

        public Player getActivePlayer() {
            return activePlayer;
        }

        public Player getEnemyPlayer() {
            return enemyPlayer;
        }

        public int getSteps() {
            return steps;
        }

        public void minusSteps(int steps) {
            int updated = this.steps - steps;
            this.steps = Math.max(updated, 0);
        }

        private void swapPlayers() {
            Player active = order.poll();
            this.enemyPlayer = order.peek();
            order.addLast(active);
            this.activePlayer = active;
        }
    }
}
