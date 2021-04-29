package com.github.redigermany.sechsnimmt.controller;

import com.github.redigermany.sechsnimmt.controller.player.AiPlayer;
import com.github.redigermany.sechsnimmt.controller.player.Player;
import com.github.redigermany.sechsnimmt.controller.player.RealPlayer;
import com.github.redigermany.sechsnimmt.model.GameState;
import com.github.redigermany.sechsnimmt.model.MovesState;

public class GameMaster {
    private Deck mainDeck;
    private final GameState gs;
    private final MovesState ms;

    public MovesState getMovesState() {
        return ms;
    }

    public GameState getGameState(){
        return gs;
    }
    public GameMaster(int playersNum,int maxOx){
        gs = new GameState(playersNum,maxOx);
        ms = new MovesState(playersNum);
        ResetGame();
    }

    public void ResetGame(){
        gs.ResetGame();
        mainDeck = gs.getMainDeck();
        mainDeck.Shuffle();
        Distribute();
        System.out.println("[GameMaster] Reset");
    }

    private void Distribute(){
        for(int cardIndex = 0; cardIndex<10; cardIndex++) {
            for (int playerIndex = 0; playerIndex < gs.getPlayersNum(); playerIndex++) {
                gs.addToPlayerDeck(playerIndex,mainDeck.getNextCard());
            }
        }
        for (int playerIndex = 0; playerIndex < gs.getPlayersNum(); playerIndex++) {
            gs.sortPlayerCards(playerIndex);
        }
        gs.addToTableRow(0,mainDeck.getNextCard());
        gs.addToTableRow(1,mainDeck.getNextCard());
        gs.addToTableRow(2,mainDeck.getNextCard());
        gs.addToTableRow(3,mainDeck.getNextCard());
        gs.sortTable();
    }
    public void letAiChooseHandCard() {
        Player[] players = gs.getPlayers();
        for (int i=1;i<players.length;i++) {
            ms.addMove(players[i], ((AiPlayer)players[i]).chooseCard());
        }
    }

    private PlayerChooseTableRow listener;
    public void setPlayerChooseTableRow (PlayerChooseTableRow listener) {
        this.listener = listener;
    }
    public void letRealPlayerChoose(RealPlayer rp){
        if (listener != null) {
            listener.onChooseRow(rp);
        }
    }
    public void GameOver(){
        System.out.println("Game is Over!");
        int i=0;
        for(Player player:gs.getPlayers()){
            System.out.println("Player "+(i++)+": "+player.getOx());
        }
        System.exit(0);
    }
    public void playRound() {
        MovesState moves = getMovesState();
        moves.sort();
        int moveN = 0;
        while(moves.hasNextMove()){
            moveN++;
            Move move = moves.getNextMove();
            boolean found = false;
            for(int rowNum=3;rowNum>=0;rowNum--){
                Card lastCard = gs.getTable()[rowNum].getLastCard();
                if(lastCard!=null) {
                    if (lastCard.getNumber() < move.getCard().getNumber()) {
                        System.out.println("Player "+moveN+" Planting Card "+move.getCard().getNumber()+" into row "+(rowNum+1));
                        if (!gs.getTable()[rowNum].addCard(move.getCard())) {
                            Card[] cards = gs.getTable()[rowNum].getAllCardsAndReset(move.getCard());
                            if (move.getPlayer().addOxDeck(cards)) {
                                GameOver();
                            }
                        }
                        found = true;
                        break;
                    }
                }else{
                    System.out.println("Error! LastCard=null");
                }
            }
            if(!found){
                move.getPlayer().chooseRow(this);
                Card[] cards = gs.getTable()[move.getPlayer().getRow()-1].getAllCardsAndReset(move.getCard());
                if (move.getPlayer().addOxDeck(cards)) {
                    GameOver();
                }
            }
        }
        moves.newRound();
    }

    public void checkNewRound() {
        if(gs.getPlayer().getCardsNumber()==0){
            ResetGame();
        }
    }
}
