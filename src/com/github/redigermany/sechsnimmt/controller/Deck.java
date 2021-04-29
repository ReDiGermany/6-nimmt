package com.github.redigermany.sechsnimmt.controller;
public class Deck {
    private Card[] cards;
    private int cardIndex=0;
    private boolean fixedLength=false;

    public Card getLastCard(){
        for(int i=cards.length-1;i>=0;i--){
            if(cards[i]!=null){
                return cards[i];
            }
        }
        return null;
    }
    public int getDeckSize(){
        return cards.length;
    }
    public Card getCard(int n){
        if(n>=cards.length) return null;
        if(n<0) return null;
        return cards[n];
    }

    public int getCardIndex() {
        return cardIndex;
    }

    public Card getNextCard(){
        return cards[cardIndex++];
    }
    public boolean hasNextCard(){
        return cardIndex<cards.length && cards[cardIndex]!=null;
    }
    public void setCardIndex(int n){
        if(n>=cards.length) return;
        if(n<0) return;
        cardIndex = n;
    }
    public Deck(int cardsNum){
        cards = new Card[cardsNum];
    }
    public Deck(int cardsNum,boolean fixedLength){
        this(cardsNum);
        this.fixedLength = fixedLength;
    }
    public boolean addCard(Card card){
        if(cardIndex>=cards.length) return false;
        cards[cardIndex++] = card;
        return true;
    }

    public void Shuffle(){
        FisherYatesShuffle();
    }
    private void SwapCards(int from,int to){
        Card s = cards[from];
        cards[from] = cards[to];
        cards[to] = s;
    }
    private void FisherYatesShuffle(){
        int len = cards.length;
        for(int i=len-1;i>0;i--){
            SwapCards(i,(int)(Math.random()*len));
        }
    }
    public void GenerateCards(){
        for(int i=0;i<cards.length;i++){
            cards[i] = new Card(i+1);
        }
    }
    public void sort(){
        for(int i=0;i<cards.length;i++){
            for(int j=i+1;j<cards.length;j++){
                if(cards[j].getNumber()<cards[i].getNumber()){
                    SwapCards(i,j);
                }
            }
        }
    }
    public void removeCard(int n){
        Card[] oldCards = cards;
        cards = new Card[oldCards.length-1];
        cardIndex--;
        if(cardIndex>0){
            int counter = 0;
            for (int i = 0; i < n; i++) {
                cards[counter++] = oldCards[i];
            }
            for (int i = n + 1; i < oldCards.length; i++) {
                cards[counter++] = oldCards[i];
            }
        }
    }

    public Card[] getAllCardsAndReset(Card card) {
        Card[] ret = cards;
        cards = new Card[ret.length];
        cards[0] = card;
        cardIndex = 1;
        return ret;
    }

}
