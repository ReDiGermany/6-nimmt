package com.github.redigermany.sechsnimmt.view;
import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.Deck;
import com.github.redigermany.sechsnimmt.controller.GameMaster;
import com.github.redigermany.sechsnimmt.model.GameState;
import com.github.redigermany.sechsnimmt.model.MovesState;

import java.util.Scanner;

public class ConsoleUI {
    private Scanner sc = new Scanner(System.in);
    private GameMaster gm;
    enum MenuItems {
        ShowBoard,
        ChooseCard,
        ShowGame,
        ExitGame
    }
    public static MenuItems fetchValue(int i) {
        for (MenuItems item : MenuItems.values()) {
            if (item.ordinal()==i) {
                return item;
            }
        }
        return null;
    }
    private void ListMenuItems(){
        for(int i=0;i<MenuItems.values().length;i++){
            System.out.printf("%d) %s%n",i+1,MenuItems.values()[i].name());
        }
    }
    private void MainMenu(){
        gm.checkNewRound();
        System.out.println("Hauptmenü\nWas möchten Sie tun?");
        ListMenuItems();
        if(sc.hasNextInt()){
            int menuItem = sc.nextInt()-1;
            if(menuItem<0 || menuItem>=MenuItems.values().length) return;
            ShowMenuItemOption(menuItem);
        }
    }
    private void getCurrentTable(GameState gs){
        Deck[] table = gs.getTable();
        for(int tableRow=0;tableRow<table.length;tableRow++){
            System.out.printf("Row %d: ",tableRow+1);
            listCardsOfTable(table[tableRow]);
            System.out.println();
        }
    }
    private void listCardsOfTable(Deck deck){
        listCardsOfTable(deck,false);
    }
    private void listCardsOfTable(Deck deck,boolean showIndexes){
        int lastCardIndex = deck.getCardIndex();
        deck.setCardIndex(0);
        while(deck.hasNextCard()){
            Card card = deck.getNextCard();
//            int info = showIndexes?(deck.getCardIndex()):card.getOx();
//            System.out.printf("%d (%d), ",card.getNumber(),info);
            System.out.printf("%d, ",card.getNumber());
        }
        deck.setCardIndex(lastCardIndex);
    }
    private void showOxInfo(){
        GameState gs = gm.getGameState();
        System.out.println("Ox Info: ");
        for(int i=0;i<gs.getPlayers().length;i++){
            System.out.printf("Player %d: %d%n",i,gs.getPlayers()[i].getOx());
        }
    }
    private void showBoard(){
        System.out.println("Current Table:");
        GameState gs = gm.getGameState();
        getCurrentTable(gs);
        System.out.println("Your Hand:");
        Deck playerDeck = gs.getPlayer().getDeck();
        listCardsOfTable(playerDeck,true);
    }

    private void chooseCard(){
        System.out.println("Choose one Card to play");
        GameState gs = gm.getGameState();
        MovesState ms = gm.getMovesState();

        Deck playerDeck = gs.getPlayer().getDeck();
        listCardsOfTable(playerDeck);
        System.out.println();
        if(sc.hasNextInt()){
            int cardNum = sc.nextInt();
            if(cardNum<=gs.getPlayer().getCardsNumber() && cardNum>0){
                Card card = gs.getPlayer().chooseCard(cardNum-1);
                ms.addMove(gs.getPlayer(),card);
                gm.letAiChooseHandCard();
                gm.setPlayerChooseTableRow(realPlayer->{
                    // TODO: show current table
                    boolean exit = false;
                    do{
                        System.out.print("Bitte Kartenreihe wählen: ");
                        int n = sc.nextInt();
                        if(n>0 && n<5){
                            realPlayer.setRow(n);
                            exit = true;
                        }else{
                            System.out.println("Falsche Zahl!");
                        }
                    }while(!exit);
                });
                gm.playRound();
            }else{
                System.out.println("Out of range total="+gs.getPlayer().getCardsNumber()+" cn="+cardNum);
            }
        }
    }
    private void exitGame(){
        System.out.println("Exit.");
        System.exit(0);
    }
    private void ShowMenuItemOption(int menuItem){
//        System.out.printf("Eingabe %d (%s)%n",menuItem,MenuItems.values()[menuItem].name());
        MenuItems item = fetchValue(menuItem);
        switch (item){
            case ShowBoard:{
                showBoard();
                break;
            }
            case ShowGame: {
                showOxInfo();
                break;
            }
            case ChooseCard: {
                chooseCard();
                break;
            }
            case ExitGame: {
                exitGame();
                break;
            }
        }
    }
    public ConsoleUI(){
        System.out.println("Willkommen bei 6 Nimmt!");
        gm = new GameMaster(2,66);
        while(true) {
            MainMenu();
        }
    }
}
