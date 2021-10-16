package com.lukash.game.view;

import com.lukash.game.controller.FightController;
import com.lukash.game.controller.GameController;
import com.lukash.game.controller.MovementController;
import com.lukash.game.model.Point;

import java.util.HashSet;
import java.util.Set;

public abstract class View {

    protected final Set<Point> area = new HashSet<>();
    protected final GameController gameController;
    protected final MovementController movementController;
    protected final FightController fightController;

    public View() {
        GameController.initNewGame(new Point(0, 0), new Point(9, 9));
        this.gameController = new GameController();
        this.movementController = new MovementController();
        this.fightController = new FightController();
    }

    public abstract void show();
}
