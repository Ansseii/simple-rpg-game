package com.lukash.game.controller;

import com.lukash.game.exceptions.GameStateException;
import com.lukash.game.exceptions.RunOutOfEquipmentException;
import com.lukash.game.model.Equipment;
import com.lukash.game.model.Hero;
import com.lukash.game.util.FieldUtil;

import static com.lukash.game.model.Equipment.*;

public class FightController {

    private final GameController gameController = new GameController();

    public boolean useInventory(Equipment equipment) {
        if (gameController.isInventoryUsed()) throw new GameStateException("The inventory can be used only once");

        boolean success = switch (equipment) {
            case SWORD, STONE -> attackEnemy(equipment);
            case POTION -> usePotion();
        };
        gameController.setInventoryUsed();

        return success;
    }

    private boolean attackEnemy(Equipment weapon) {
        Hero active = gameController.getActivePlayer().getHero();
        Hero enemy = gameController.getEnemyPlayer().getHero();
        int distance = FieldUtil.getDistanceBetweenPoints(active.getPosition(), enemy.getPosition());

        Effect damage = switch (weapon) {
            case SWORD -> useSword(distance);
            case STONE -> useStone(distance);
            default -> throw new IllegalStateException("Unexpected value: " + weapon);
        };

        boolean success = Math.random() <= damage.chance();
        if (success) enemy.setHp(enemy.getHp() - damage.value());
        gameController.findWinner();

        return success;
    }

    private Effect useSword(int distance) {
        if (distance == 1) {
            return SWORD.getEffect().max();
        }
        throw new GameStateException("Enemy is too far");
    }

    private Effect useStone(int distance) {
        Hero hero = gameController.getActivePlayer().getHero();
        if (hero.isInventoryContains(STONE)) {
            Effect damage = switch (distance) {
                case 1, 2, 3 -> STONE.getEffect().max();
                case 4, 5, 6 -> STONE.getEffect().min();
                default -> throw new GameStateException("Enemy is too far");
            };
            hero.takeFromInventory(STONE);
            return damage;
        }
        throw new RunOutOfEquipmentException("No one stone left");
    }

    private boolean usePotion() {
        Hero hero = gameController.getActivePlayer().getHero();
        if (hero.isInventoryContains(POTION)) {
            hero.takeFromInventory(POTION);
            hero.setHp(hero.getHp() + POTION.getEffect().max().value());

            return true;
        }
        throw new RunOutOfEquipmentException("No one potion left");
    }
}
