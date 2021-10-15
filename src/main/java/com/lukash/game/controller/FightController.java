package com.lukash.game.controller;

import com.lukash.game.exceptions.GameStateException;
import com.lukash.game.exceptions.RunOutOfEquipmentException;
import com.lukash.game.model.Equipment;
import com.lukash.game.model.Hero;
import com.lukash.game.util.FieldUtil;

import static com.lukash.game.model.Equipment.*;

public class FightController extends Controller {

    public boolean useInventory(Equipment equipment) {
        return switch (equipment) {
            case SWORD, STONE -> attackEnemy(equipment);
            case POTION -> usePotion();
        };
    }

    private boolean attackEnemy(Equipment weapon) {
        Hero active = gameState.getActivePlayer().getHero();
        Hero enemy = gameState.getEnemyPlayer().getHero();
        int distance = FieldUtil.getDistanceBetweenPoints(active.getPosition(), enemy.getPosition());

        Effect damage = switch (weapon) {
            case SWORD -> useSword(distance);
            case STONE -> useStone(distance);
            default -> throw new IllegalStateException("Unexpected value: " + weapon);
        };

        boolean success = Math.random() <= damage.chance();
        if (success) enemy.setHp(enemy.getHp() - damage.value());

        return success;
    }

    private Effect useSword(int distance) {
        if (distance == 1) {
            return SWORD.getEffect().max();
        }
        throw new GameStateException("Enemy is too far");
    }

    private Effect useStone(int distance) {
        Hero hero = gameState.getActivePlayer().getHero();
        if (hero.isInventoryContains(STONE)) {
            hero.takeFromInventory(STONE);
            return switch (distance) {
                case 1, 2, 3 -> STONE.getEffect().max();
                case 4, 5, 6 -> STONE.getEffect().min();
                default -> throw new GameStateException("Enemy is too far");
            };
        }
        throw new RunOutOfEquipmentException("No one stone left");
    }

    private boolean usePotion() {
        Hero hero = gameState.getActivePlayer().getHero();
        if (hero.isInventoryContains(POTION)) {
            hero.takeFromInventory(POTION);
            hero.setHp(hero.getHp() + POTION.getEffect().max().value());

            return true;
        }
        throw new RunOutOfEquipmentException("No one potion left");
    }
}
