package com.lukash.game.controller;

import com.lukash.game.model.Figure;
import com.lukash.game.model.Player;
import com.lukash.game.model.Point;
import com.lukash.game.model.Summary;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @Test
    void getSummary() {
        setup();
        Summary summary = gameController.getSummary();

        assertEquals(15, summary.getPlayer1State().hp());
        assertEquals(15, summary.getPlayer2State().hp());
        assertEquals(2, summary.getPlayer1State().stones());
        assertEquals(2, summary.getPlayer2State().stones());
        assertEquals(1, summary.getPlayer1State().potions());
        assertEquals(1, summary.getPlayer2State().potions());
    }

    @Test
    void getPlayers() {
        setup();
        List<Player> players = List.of(
                new Player("PLAYER_1", Figure.FIGURE_1, new Point(1, 1)),
                new Player("PLAYER_2", Figure.FIGURE_2, new Point(2, 2))
        );

        assertEquals(players, gameController.getPlayers());
    }

    @Test
    void getActivePlayer() {
        setup();

        assertEquals(new Player("PLAYER_1", Figure.FIGURE_1, new Point(1, 1)), gameController.getActivePlayer());

        gameController.endTurn();

        assertEquals(new Player("PLAYER_2", Figure.FIGURE_2, new Point(2, 2)), gameController.getActivePlayer());
    }

    @Test
    void endTurn() {
        setup();
        assertEquals(new Player("PLAYER_1", Figure.FIGURE_1, new Point(1, 1)), gameController.getActivePlayer());
        assertEquals(new Player("PLAYER_2", Figure.FIGURE_2, new Point(2, 2)), gameController.getEnemyPlayer());

        gameController.endTurn();

        assertEquals(new Player("PLAYER_2", Figure.FIGURE_2, new Point(2, 2)), gameController.getActivePlayer());
        assertEquals(new Player("PLAYER_1", Figure.FIGURE_1, new Point(1, 1)), gameController.getEnemyPlayer());
    }

    @Test
    void getEnemyPlayer() {
        setup();

        assertEquals(new Player("PLAYER_2", Figure.FIGURE_2, new Point(2, 2)), gameController.getEnemyPlayer());

        gameController.endTurn();

        assertEquals(new Player("PLAYER_1", Figure.FIGURE_1, new Point(1, 1)), gameController.getEnemyPlayer());
    }

    private void setup() {
        GameController.initNewGame(new Point(1, 1), new Point(2, 2));
        this.gameController = new GameController();
    }
}