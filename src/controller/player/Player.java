package controller.player;

import controller.Card;
import controller.Deck;

abstract public class Player {
    protected Deck deck;
    protected int ox = 0;
    public Player(){
        deck=new Deck(10,true);
    }
    public void sortCards(){
        deck.sort();
    }
    public Deck getDeck(){
        return deck;
    }
    public void addCard(Card card){
        deck.addCard(card);
    }
    public Card getCard(int num){
        return deck.getCard(num);
    }

    public boolean addOxDeck(Card[] cards){
        for (Card card : cards) {
            ox += card.getOx();
        }
        return ox>=66;
    }

    public int getOx(){
        return ox;
    };
}
