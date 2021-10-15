package com.lukash.game.controller;

import com.lukash.game.exceptions.GameStateException;
import com.lukash.game.exceptions.RunOutOfEquipmentException;
import com.lukash.game.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lukash.game.model.Equipment.*;
import static org.junit.jupiter.api.Assertions.*;

class FightControllerTest {

    private FightController controller;

    @Test
    void canUseInventoryOnlyOnceInOneTurn() {
        setup(new Point(1, 1), new Point(1, 2));

        controller.useInventory(POTION);
        assertThrows(GameStateException.class, () -> controller.useInventory(POTION));
    }

    @Test
    void swordHitsWhenPlayersInShortRangeDistance() {
        setup(new Point(1, 1), new Point(1, 2));

        Hero activeHero = controller.gameState.getActivePlayer().getHero();
        Hero enemyHero = controller.gameState.getEnemyPlayer().getHero();

        boolean attacked;
        do {
            attacked = controller.useInventory(SWORD);
            controller.gameState.endTurn();
        } while (!attacked);

        assertEquals(10, enemyHero.getHp());
    }

    @Test
    void potionHealsHeroOn7Hp() {
        setup(new Point(1, 1), new Point(1, 2));

        Hero activeHero = controller.gameState.getActivePlayer().getHero();
        Hero enemyHero = controller.gameState.getEnemyPlayer().getHero();

        int hits = 0;
        do {
            boolean attacked = controller.useInventory(SWORD);
            controller.gameState.endTurn();
            controller.gameState.endTurn();
            if (attacked) hits += 1;
        } while (hits < 2);

        assertEquals(5, enemyHero.getHp());
        controller.gameState.endTurn();
        controller.useInventory(POTION);

        assertEquals(12, enemyHero.getHp());
    }

    @Test
    void onlyOnePotionPerGame() {
        setup(new Point(1, 1), new Point(1, 2));

        controller.useInventory(POTION);

        controller.gameState.endTurn();
        controller.gameState.endTurn();

        assertThrows(RunOutOfEquipmentException.class, () -> controller.useInventory(POTION));

        controller.gameState.endTurn();
        controller.gameState.endTurn();

        assertThrows(RunOutOfEquipmentException.class, () -> controller.useInventory(POTION));
    }

    @Test
    void onlyTwoStonesPerGame() {
        setup(new Point(1, 1), new Point(1, 2));

        controller.useInventory(STONE);

        controller.gameState.endTurn();
        controller.gameState.endTurn();

        controller.useInventory(STONE);

        controller.gameState.endTurn();
        controller.gameState.endTurn();

        assertThrows(RunOutOfEquipmentException.class, () -> controller.useInventory(STONE));
    }

    @Test
    void attackWhenPlayersInLongRangeDistance() {
        setup(new Point(1, 1), new Point(9, 9));

        Hero activeHero = controller.gameState.getActivePlayer().getHero();
        Hero enemyHero = controller.gameState.getEnemyPlayer().getHero();

        assertThrows(GameStateException.class, () -> controller.useInventory(SWORD));
        assertThrows(GameStateException.class, () -> controller.useInventory(STONE));
    }

    private void setup(Point activePlayer, Point enemyPlayer) {
        List<Player> players = List.of(
                new Player("PLAYER_1", Figure.FIGURE_1, activePlayer),
                new Player("PLAYER_2", Figure.FIGURE_2, enemyPlayer)
        );
        Game.createNewGame("Test Game", players, new Field(10));
        this.controller = new FightController();
    }
}