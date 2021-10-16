package com.lukash.game.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeroTest {

    private Hero hero;

    @BeforeEach
    void setup() {
        this.hero = new Hero(Figure.FIGURE_1, new Point(0, 0));
    }

    @Test
    void takeFromInventory() {
        assertTrue(hero.isInventoryContains(Equipment.SWORD));
        assertTrue(hero.isInventoryContains(Equipment.STONE));
        assertTrue(hero.isInventoryContains(Equipment.POTION));

        hero.takeFromInventory(Equipment.POTION);
        hero.takeFromInventory(Equipment.STONE);
        hero.takeFromInventory(Equipment.STONE);

        assertFalse(hero.isInventoryContains(Equipment.STONE));
        assertFalse(hero.isInventoryContains(Equipment.POTION));
    }
}