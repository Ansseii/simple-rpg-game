package com.lukash.game.model;

import java.util.HashMap;
import java.util.Map;

import static com.lukash.game.model.Equipment.*;

public class Hero {

    private int hp;
    private Point position;
    private final Figure figure;
    private final Map<Equipment, Integer> inventory;

    public Hero(Figure figure, Point position) {
        this.hp = 15;
        this.figure = figure;
        this.position = position;
        this.inventory = initInventory();
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.min(Math.max(hp, 0), 15);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Figure getFigure() {
        return figure;
    }

    public boolean isInventoryContains(Equipment equipment) {
        return inventory.get(equipment) > 0;
    }

    public void takeFromInventory(Equipment equipment) {
        switch (equipment) {
            case STONE, POTION -> inventory.computeIfPresent(equipment, (k, v) -> Math.max(v - 1, 0));
            case SWORD -> {
            }
        }
    }

    private Map<Equipment, Integer> initInventory() {
        Map<Equipment, Integer> storage = new HashMap<>();

        storage.put(SWORD, 1);
        storage.put(STONE, 2);
        storage.put(POTION, 1);

        return storage;
    }
}
