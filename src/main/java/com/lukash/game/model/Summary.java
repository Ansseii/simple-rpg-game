package com.lukash.game.model;

import java.util.List;
import java.util.Optional;

public class Summary {

    private final Player winner;
    private final Player activePlayer;
    private HeroState player1;
    private HeroState player2;

    public Summary(List<Player> players, Player activePlayer, Player winner) {
        players.forEach(p -> {
            switch (p.getName()) {
                case "PLAYER_1" -> {
                    Hero hero = p.getHero();
                    player1 = new HeroState(hero.getHp(), hero.getCount(Equipment.STONE), hero.getCount(Equipment.POTION));
                }
                case "PLAYER_2" -> {
                    Hero hero = p.getHero();
                    player2 = new HeroState(hero.getHp(), hero.getCount(Equipment.STONE), hero.getCount(Equipment.POTION));
                }
            }
        });
        this.activePlayer = activePlayer;
        this.winner = winner;
    }

    public HeroState getPlayer1State() {
        return player1;
    }

    public HeroState getPlayer2State() {
        return player2;
    }

    public Optional<Player> getWinner() {
        return Optional.ofNullable(winner);
    }

    public String getWinnerMessage() {
        return String.format("%s WON!", winner.getName());
    }

    public String getActivePlayerMessage() {
        return String.format("%s TURN", activePlayer.getName());
    }

    public record HeroState(int hp, int stones, int potions) {
    }
}
