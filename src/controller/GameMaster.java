package controller;

import controller.player.AiPlayer;
import controller.player.Player;
import model.GameState;
import model.MovesState;

public class GameMaster {
    private final Deck mainDeck;
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
        mainDeck = gs.getMainDeck();
        mainDeck.Shuffle();
        Distribute();
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
    public void letAiChoose() {
        Player[] players = gs.getPlayers();
        for (int i=1;i<players.length;i++) {
            ms.addMove(players[i], ((AiPlayer)players[i]).chooseCard());
        }
    }

    public void playRound() {
        MovesState moves = getMovesState();
        moves.sort();
        while(moves.hasNextMove()){
            Move move = moves.getNextMove();
            for(int rowNum=0;rowNum<4;rowNum++){
                Card lastCard = gs.getTable()[rowNum].getLastCard();
                if(lastCard!=null && lastCard.getNumber()>move.getCard().getNumber()){
                    if(!gs.getTable()[rowNum].addCard(move.getCard())) {
                        Card[] cards = gs.getTable()[rowNum].getAllCardsAndReset(move.getCard());
                        if(move.getPlayer().addOxDeck(cards)){
//                            game over!
                            System.out.println("Game Over!");
                        }
                    }
                    return;
                }else{
                    // TODO: Implement logic if chosen card is to little, let real player choose via ui
                    System.out.println("");
                }
            }
        }
    }
}
