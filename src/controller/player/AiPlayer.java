package controller.player;

import controller.Card;

public class AiPlayer extends Player {
    public Card chooseCard(){
        int n = (int)(Math.random()*deck.getDeckSize());
        Card card = deck.getCard(n);
        deck.removeCard(n);
        return card;
    }
}
