package com.github.redigermany.sechsnimmt.view;
import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.Deck;
import com.github.redigermany.sechsnimmt.controller.GameMaster;
import com.github.redigermany.sechsnimmt.controller.player.RealPlayer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GUI extends Application {
    private GridPane root = new GridPane();
    private PlayCard[] handCards = new PlayCard[10];
    private PlayCard oxCard;
    private GameMaster gm;
    private RealPlayer player;
    private PlayCard[][] table = new PlayCard[4][5];
    private boolean allowedToChoose = true;

    private void generateHandBar(){
        for(int i=0;i<10;i++){
            PlayCard playCard = new PlayCard(this,Color.DARKBLUE);
            handCards[i] = playCard;
            root.add(handCards[i],i,5);
        }
        oxCard = new PlayCard(this,Color.GRAY);
        oxCard.lock();
        root.add(oxCard,10,5);
    }

    private void generateTable(){
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                table[i][j] = new PlayCard(this,Color.GRAY);
                root.add(table[i][j],3+j,i+1);
            }
        }
    }

    private void updateTable(){
        oxCard.setText(player.getOx().toString());
        Deck deck = player.getDeck();
        for(int i=0;i<10;i++){
            handCards[i].setCard(deck.getCard(i));
        }
        Deck[] tableData = gm.getGameState().getTable();
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                Card card = tableData[i].getCard(j);
                if(card!=null){
                    table[i][j].setCard(card);
                }
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("6 nimmt!");
        generateHandBar();
        generateTable();
        gm = new GameMaster(2,66);
        player = gm.getGameState().getPlayer();
        updateTable();
        root.setBackground(new Background(new BackgroundFill(Color.BLACK,new CornerRadii(0),new Insets(0))));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public boolean isAllowedToChoose() {
        return allowedToChoose;
    }
}
