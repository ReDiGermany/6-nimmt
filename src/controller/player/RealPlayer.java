package controller.player;

import controller.Card;
import controller.Deck;

public class RealPlayer extends Player {
    public Card chooseCard(int n){
        Card card = deck.getCard(n);
        deck.removeCard(n);
        return card;
    }
}
