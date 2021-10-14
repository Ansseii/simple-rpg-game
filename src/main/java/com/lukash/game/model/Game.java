package com.lukash.game.model;

import com.lukash.game.exceptions.GameStateException;

import java.util.List;
import java.util.Optional;

public class Game {

    private static Game INSTANCE;

    private final String name;
    private final List<Player> players;
    private final Field field;
    private final GameState state;

    private Game(String name, List<Player> players, Field field) {
        this.name = name;
        this.players = players;
        this.field = field;
        this.state = new GameState(players);
        initStartPosition();
    }

    public static void createNewGame(String name, List<Player> players, Field field) {
//        if (Objects.nonNull(INSTANCE)) {
//            throw new GameStateException("The game has been already started");
//        }
        INSTANCE = new Game(name, players, field);
    }

    public static Game getActiveGame() {
        return Optional.ofNullable(INSTANCE)
                .orElseThrow(() -> new GameStateException("No active game available"));
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Field getField() {
        return field;
    }

    public GameState getState() {
        return state;
    }

    private void initStartPosition() {
        players.forEach(p -> {
            Hero hero = p.getHero();
            field.setFigure(hero.getPosition(), hero.getFigure());
        });
    }
}
