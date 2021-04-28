package model;

import controller.Card;
import controller.Deck;
import controller.player.AiPlayer;
import controller.player.Player;
import controller.player.RealPlayer;

public class GameState {
    private final int maxOx;
    private final Deck mainDeck;
    private final RealPlayer player;
    private final Player[] players;
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
        this.mainDeck = new Deck(cardsNum);
        mainDeck.GenerateCards();
        players = new Player[playersNum];
        player = new RealPlayer();
        players[0] = (Player)player;
        for(int i=1;i<players.length;i++){
            players[i] = new AiPlayer();
        }
        for(int i=0;i<table.length;i++){
            table[i]=new Deck(5,true);
        }
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

    public void addToTableRow(int i,Card card){
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
