package com.lukash.game.controller;

import com.lukash.game.exceptions.InvalidPointException;
import com.lukash.game.model.Hero;
import com.lukash.game.model.Point;
import com.lukash.game.util.FieldUtil;
import com.lukash.game.view.Action;

import java.util.Set;
import java.util.stream.Collectors;

public class MovementController extends Controller {

    public void move(Point newPosition, Action drawAction) {
        Hero activeHero = gameState.getActivePlayer().getHero();
        Point currentPosition = activeHero.getPosition();
        int distance = FieldUtil.getDistanceBetweenPoints(currentPosition, newPosition);

        validatePoint(newPosition, distance);
        field.setFigure(currentPosition, null);
        field.setFigure(newPosition, activeHero.getFigure());

        gameState.minusSteps(distance);

        drawAction.apply();
        activeHero.setPosition(newPosition);
    }

    public Set<Point> getAvailableArea() {
        Point position = gameState.getActivePlayer().getHero().getPosition();
        Point enemyPosition = gameState.getEnemyPlayer().getHero().getPosition();

        return field.getAvailablePoints(position, gameState.getSteps()).stream()
                .filter(p -> !p.equals(enemyPosition))
                .collect(Collectors.toSet());
    }

    private void validatePoint(Point point, int distance) {
        Point enemyPosition = gameState.getEnemyPlayer().getHero().getPosition();

        if (distance > gameState.getSteps()) {
            throw new InvalidPointException("New point is too far. Pick another one");
        }

        if (point.equals(enemyPosition)) {
            throw new InvalidPointException("Point is already occupied by enemy");
        }
    }
}
