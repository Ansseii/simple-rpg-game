package com.lukash.game.controller;

import com.lukash.game.exceptions.GameStateException;
import com.lukash.game.exceptions.RunOutOfEquipmentException;
import com.lukash.game.model.Hero;
import com.lukash.game.model.Point;
import org.junit.jupiter.api.Test;

import static com.lukash.game.model.Equipment.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FightControllerTest {

    private GameController gameController;
    private FightController fightController;

    @Test
    void canUseInventoryOnlyOnceInOneTurn() {
        setup(new Point(1, 1), new Point(1, 2));

        fightController.useInventory(POTION);
        assertThrows(GameStateException.class, () -> fightController.useInventory(POTION));
    }

    @Test
    void swordHitsWhenPlayersInShortRangeDistance() {
        setup(new Point(1, 1), new Point(1, 2));

        Hero enemyHero = gameController.getEnemyPlayer().getHero();

        boolean attacked;
        do {
            attacked = fightController.useInventory(SWORD);
            gameController.endTurn();
        } while (!attacked);

        assertEquals(10, enemyHero.getHp());
    }

    @Test
    void potionHealsHeroOn7Hp() {
        setup(new Point(1, 1), new Point(1, 2));

        Hero enemyHero = gameController.getEnemyPlayer().getHero();

        int hits = 0;
        do {
            boolean attacked = fightController.useInventory(SWORD);
            gameController.endTurn();
            gameController.endTurn();
            if (attacked) hits += 1;
        } while (hits < 2);

        assertEquals(5, enemyHero.getHp());
        gameController.endTurn();
        fightController.useInventory(POTION);

        assertEquals(12, enemyHero.getHp());
    }

    @Test
    void onlyOnePotionPerGame() {
        setup(new Point(1, 1), new Point(1, 2));

        fightController.useInventory(POTION);

        gameController.endTurn();
        gameController.endTurn();

        assertThrows(RunOutOfEquipmentException.class, () -> fightController.useInventory(POTION));

        gameController.endTurn();
        gameController.endTurn();

        assertThrows(RunOutOfEquipmentException.class, () -> fightController.useInventory(POTION));
    }

    @Test
    void onlyTwoStonesPerGame() {
        setup(new Point(1, 1), new Point(1, 2));

        fightController.useInventory(STONE);

        gameController.endTurn();
        gameController.endTurn();

        fightController.useInventory(STONE);

        gameController.endTurn();
        gameController.endTurn();

        assertThrows(RunOutOfEquipmentException.class, () -> fightController.useInventory(STONE));
    }

    @Test
    void attackWhenPlayersInLongRangeDistance() {
        setup(new Point(1, 1), new Point(9, 9));

        assertThrows(GameStateException.class, () -> fightController.useInventory(SWORD));
        assertThrows(GameStateException.class, () -> fightController.useInventory(STONE));
    }

    private void setup(Point activePlayer, Point enemyPlayer) {
        GameController.initNewGame(activePlayer, enemyPlayer);
        this.gameController = GameController.getInstance();
        this.fightController = FightController.getInstance();
    }
}