package com.lukash.game;

import com.lukash.game.model.Game;
import com.lukash.game.model.Figure;
import com.lukash.game.model.Field;
import com.lukash.game.model.Player;
import com.lukash.game.model.Point;
import com.lukash.game.view.fx.FXView;

import java.util.List;

public class GameLauncher {

    public static void main(String[] args) {

        List<Player> players = List.of(
                new Player("PLAYER_1", Figure.FIGURE_1, new Point(0, 0)),
                new Player("PLAYER_2", Figure.FIGURE_2, new Point(9, 9))
        );

        Game.createNewGame("Game №1", players, new Field(10));
        new FXView().show();
    }
}