package com.github.redigermany.sechsnimmt.controller.player;

import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.GameMaster;

public class AiPlayer extends Player {
    public AiPlayer(Player oldPlayer){
        if(oldPlayer!=null) this.ox = oldPlayer.getOx();
    }
    public Card chooseCard(){
//        int n = (int)(Math.random()*deck.getDeckSize());
        int n = 0;
        Card card = deck.getCard(n);
        deck.removeCard(n);
        return card;
    }

    @Override
    public void chooseRow(GameMaster gm) {
//        int n = (int)(Math.random()*4);
        int n = 1;
        this.setRow(n);
        System.out.println("AI Grabbing row "+n);
    }
}
