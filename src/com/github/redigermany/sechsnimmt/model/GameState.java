package com.github.redigermany.sechsnimmt.model;

import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.Deck;
import com.github.redigermany.sechsnimmt.controller.player.AiPlayer;
import com.github.redigermany.sechsnimmt.controller.player.Player;
import com.github.redigermany.sechsnimmt.controller.player.RealPlayer;

public class GameState {
    private final int maxOx;
    private Deck mainDeck;
    private RealPlayer player;
    private Player[] players;
    private final Deck[] table = new Deck[4];
    private final int cardsNum = 104;

    public Player[] getPlayers() {
        return players;
    }

    public Deck[] getTable(){
        return table;
    }
    public RealPlayer getPlayer(){
        return player;
    }

    public GameState(int playersNum,int maxOx){
        this.maxOx = maxOx;
        players = new Player[playersNum];
//        ResetGame();
    }

    public void ResetGame(){
        mainDeck = new Deck(cardsNum);
        mainDeck.GenerateCards();
        player = new RealPlayer(player);
        players[0] = (Player)player;
        for(int i=1;i<players.length;i++){
            players[i] = new AiPlayer(players[i]);
        }
        for(int i=0;i<table.length;i++){
            table[i]=new Deck(5,true);
        }
        System.out.println("[GameState] Reset");
    }

    public void sortTable(){
        for(int i=0;i<4;i++){
            for(int j=i+1;j<4;j++){
                if(table[j].getCard(0).getNumber() < table[i].getCard(0).getNumber()){
                    Deck temp = table[j];
                    table[j] = table[i];
                    table[i] = temp;
                }
            }
        }
    }

    public void sortPlayerCards(int playerIndex){
        if(playerIndex>players.length) return;
        if(playerIndex<0) return;
        players[playerIndex].sortCards();
    }

    public int getPlayersNum() {
        return players.length;
    }

    public int getMaxOx() {
        return maxOx;
    }

    public Deck getTableRow(int i){
        return table[i];
    }

    public void addToTableRow(int i, Card card){
        table[i].addCard(card);
    }

    public Deck getPlayerDeck(int playerId){
        return players[playerId].getDeck();
    }
    public void addToPlayerDeck(int playerId,Card card){
        players[playerId].addCard(card);
    }

    public Deck getMainDeck() {
        return mainDeck;
    }
}
