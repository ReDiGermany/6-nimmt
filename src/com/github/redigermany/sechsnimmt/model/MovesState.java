package com.github.redigermany.sechsnimmt.model;

import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.Move;
import com.github.redigermany.sechsnimmt.controller.player.Player;

public class MovesState {
    private Move[] moves;
    private final int players;
    private int currentPlayer = 0;
    private int moveCount = 0;
    public MovesState(int players){
        this.players = players;
        newRound();
    }
    public void newRound(){
        moves = new Move[players];
        currentPlayer = 0;
        moveCount = 0;
    }
    public void addMove(Player player, Card card){
        addMove(new Move(player,card));
    }
    public void addMove(Move move){
        if(currentPlayer>=moves.length) return;
        moves[currentPlayer++] = move;
    }
    public void sort(){
        for(int i=0;i<moves.length;i++){
            for(int j=i+1;j<moves.length;j++){
                Card c1 = moves[j].getCard();
                Card c2 = moves[i].getCard();
                if(c1==null ||c2==null){
                    System.out.println("?!?");
                }
                if(c1.getNumber()<c2.getNumber()){
                    Move temp = moves[i];
                    moves[i] = moves[j];
                    moves[j] = temp;
                }
            }
        }
    }

    public boolean hasNextMove(){
        return moveCount<moves.length;
    }

    public Move getNextMove(){
        if(moveCount>=moves.length) return null;
        return moves[moveCount++];
    }
}
