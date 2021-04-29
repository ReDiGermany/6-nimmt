package com.github.redigermany.sechsnimmt.controller.player;

import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.Deck;
import com.github.redigermany.sechsnimmt.controller.GameMaster;

abstract public class Player {
    protected Deck deck;
    protected int ox = 0;
    protected int row=1;

    public void setRow(int row) {
        if(row>0 && row <5) this.row = row;
    }

    public int getRow() {
        return row;
    }

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

    public abstract void chooseRow(GameMaster gm);

    public boolean addOxDeck(Card[] cards){
        for (Card card : cards) {
            if(card!=null) ox += card.getOx();
        }
        return ox>=66;
    }

    public Integer getOx(){
        return ox;
    };

    public int getCardsNumber(){
        return deck.getDeckSize();
    }
}
