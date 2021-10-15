package com.lukash.game.model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

// TODO tests
public class GameState {

    private static final int AVAILABLE_STEPS = 4;

    private final Deque<Player> order = new LinkedList<>();
    private Player activePlayer;
    private Player enemyPlayer;
    private boolean inventoryUsed;
    private int steps;

    protected GameState(List<Player> players) {
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
