package com.lukash.game.controller;

import com.lukash.game.exceptions.InvalidPointException;
import com.lukash.game.model.Hero;
import com.lukash.game.model.Point;
import com.lukash.game.util.FieldUtil;
import com.lukash.game.view.Action;

import java.util.Set;
import java.util.stream.Collectors;

public class MovementController {

    private final GameController gameController = new GameController();

    public void move(Point newPosition, Action drawAction) {
        Hero activeHero = gameController.getActivePlayer().getHero();
        Point currentPosition = activeHero.getPosition();
        int distance = FieldUtil.getDistanceBetweenPoints(currentPosition, newPosition);

        validatePoint(newPosition, distance);
        gameController.getField().setFigure(currentPosition, null);
        gameController.getField().setFigure(newPosition, activeHero.getFigure());

        gameController.minusSteps(distance);

        drawAction.apply();
        activeHero.setPosition(newPosition);
    }

    public Set<Point> getAvailableArea() {
        Point position = gameController.getActivePlayer().getHero().getPosition();
        Point enemyPosition = gameController.getEnemyPlayer().getHero().getPosition();

        return gameController.getField().getAvailablePoints(position, gameController.getSteps()).stream()
                .filter(p -> !p.equals(enemyPosition))
                .collect(Collectors.toSet());
    }

    private void validatePoint(Point point, int distance) {
        Point enemyPosition = gameController.getEnemyPlayer().getHero().getPosition();

        if (distance > gameController.getSteps()) {
            throw new InvalidPointException("New point is too far. Pick another one");
        }

        if (point.equals(enemyPosition)) {
            throw new InvalidPointException("Point is already occupied by enemy");
        }
    }
}
