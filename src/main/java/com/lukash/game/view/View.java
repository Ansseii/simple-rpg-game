package com.lukash.game.view;

import com.lukash.game.controller.GameController;
import com.lukash.game.controller.MovementController;
import com.lukash.game.model.Point;

import java.util.HashSet;
import java.util.Set;

public abstract class View {

    protected final Set<Point> area = new HashSet<>();
    protected final GameController gameController = new GameController();
    protected final MovementController movementController = new MovementController();

    public abstract void show();
}
