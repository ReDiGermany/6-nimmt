package com.github.redigermany.sechsnimmt.controller.player;

import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.GameMaster;

public class RealPlayer extends Player {
    public RealPlayer(Player oldPlayer){
        if(oldPlayer!=null) this.ox = oldPlayer.getOx();
    }
    public Card chooseCard(int n){
        Card card = deck.getCard(n);
        deck.removeCard(n);
        return card;
    }

    @Override
    public void chooseRow(GameMaster gm) {
        gm.letRealPlayerChoose(this);
//        this.setRow(1);
    }
}
