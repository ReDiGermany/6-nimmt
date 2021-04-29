package com.github.redigermany.sechsnimmt.controller;

import com.github.redigermany.sechsnimmt.controller.player.Player;

public class Move {
    private final Player player;
    private final Card card;
    public Move(Player player, Card card){
        this.card = card;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }
}
