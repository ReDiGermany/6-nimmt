package com.github.redigermany.sechsnimmt.view;
import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.Deck;
import com.github.redigermany.sechsnimmt.controller.GameMaster;
import com.github.redigermany.sechsnimmt.model.WindowState;
import com.github.redigermany.sechsnimmt.controller.player.RealPlayer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {
    private final GridPane root = new GridPane();
    private final PlayCard[] handCards = new PlayCard[10];
    private PlayCard oxCard;
    private GameMaster gm;
    private RealPlayer player;
    private final PlayCard[][] table = new PlayCard[4][5];
    private boolean allowedToChoose = true;
    private WindowState windowState;

    public GameMaster getGm() {
        return gm;
    }

    private void generateHandBar(){
        for(int i=0;i<10;i++){
            PlayCard playCard = new PlayCard(this,Color.DARKBLUE);
            playCard.onPick(card->{
                System.out.println("picked "+card);
            });
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

    public void updateTable(){
        oxCard.setText(player.getOx().toString());
        Deck deck = player.getDeck();
        for(int i=0;i<10;i++){
            handCards[i].setCard(deck.getCard(i));
        }
        Deck[] tableData = gm.getGameState().getTable();
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                table[i][j].setCard(tableData[i].getCard(j));
            }
        }
    }
    public Label statusLabel = new Label("Status: Initializing...");

    @Override
    public void start(Stage stage) throws Exception {
        gm = new GameMaster(2,66);
        player = gm.getGameState().getPlayer();

        statusLabel.setTextFill(Color.WHITE);
        root.add(statusLabel,0,0);

        windowState = new WindowState("main.cfg");

        generateHandBar();
        generateTable();

        updateTable();
        root.setStyle("-fx-background-color:#000");

        statusLabel.setText("Status: Your turn!");
        stage.setX(windowState.getX());
        stage.setY(windowState.getY());
        stage.setTitle("6 nimmt!");
        stage.setScene(new Scene(root));
        stage.show();
        stage.xProperty().addListener((obs, oldVal, newVal) -> windowState.setX((double) newVal));
        stage.yProperty().addListener((obs, oldVal, newVal) -> windowState.setY((double) newVal));

    }

    public boolean isAllowedToChoose() {
        return allowedToChoose;
    }
}
