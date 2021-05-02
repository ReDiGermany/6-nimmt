package com.github.redigermany.sechsnimmt.view;

import com.github.redigermany.sechsnimmt.controller.Card;
import com.github.redigermany.sechsnimmt.controller.GameMaster;
import com.github.redigermany.sechsnimmt.model.GameState;
import com.github.redigermany.sechsnimmt.model.MovesState;
import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

class PlayCard extends Pane {
    private final int HEIGHT = 180;
    private final int WIDTH = 120;
    private GUI gui;
    private boolean locked = true;
    private Label numberLabel = new Label("");
    private Label oxNum = new Label("");
    private Card card;
    private double hoverScaleFactor = 0.9;
    private GameMaster gm;
    private MovesState ms;
    private GameState gs;

    public PlayCard(GUI gui) {
        this(gui, Color.TRANSPARENT);
    }

    public PlayCard(GUI gui, Color color) {
        this.gui = gui;
        gm = gui.getGm();
        ms = gm.getMovesState();
        gs = gm.getGameState();
        this.getChildren().addAll(numberLabel, oxNum);
        setCardStyle(color);
        setNumberLabelStyle();
        setOxNumberLabelStyle();
        setListeners();
    }

    private void setListeners() {
        setOnMouseClicked(e->{
            if(gui.isAllowedToChoose()){
                for(int i=0;i<10;i++){
                    Card c = gui.getGm().getGameState().getPlayer().getCard(i);
                    if(c!=null && c.equals(card)){
                        Card card = gui.getGm().getGameState().getPlayer().chooseCard(i);
                        ms.addMove(gs.getPlayer(),card);
                        gm.letAiChooseHandCard();
                        gm.setPlayerChooseTableRow(realPlayer->{
                            gui.statusLabel.setText("Status: Choose row!");
                            System.out.println("Choose row!");
//                            // TODO: show current table
//                            boolean exit = false;
//                            do{
//                                System.out.print("Bitte Kartenreihe wählen: ");
//                                int n = sc.nextInt();
//                                if(n>0 && n<5){
//                                    realPlayer.setRow(n);
//                                    exit = true;
//                                }else{
//                                    System.out.println("Falsche Zahl!");
//                                }
//                            }while(!exit);
                        });
                        gm.playRound();
                        System.out.println(card+": card num="+i);
                        gui.updateTable();
                        break;
                    }
                }
            }else{
                gui.statusLabel.setText("Status: Not your turn!");
            }
        });
        setOnMouseEntered(e -> {
            if (isLocked()) return;
            setCursor(Cursor.HAND);
            setCardAnimation(1,hoverScaleFactor);
        });
        setOnMouseExited(e -> {
            if (isLocked()) return;
            setCardAnimation(hoverScaleFactor,1);
        });
    }

    private void setCardAnimation(double from,double to){
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300),this);
        scaleTransition.setFromX(from);
        scaleTransition.setFromY(from);
        scaleTransition.setToX(to);
        scaleTransition.setToY(to);
        scaleTransition.setCycleCount(1);
        scaleTransition.play();
    }

    private void setCardStyle(Color color) {
        this.setMinHeight(HEIGHT);
        this.setMinWidth(WIDTH);
        this.setBackground(new Background(new BackgroundFill(color, new CornerRadii(10), new Insets(5))));
    }

    private void setOxNumberLabelStyle() {
        oxNum.setMinHeight(50);
        oxNum.setMinWidth(WIDTH);
        oxNum.setFont(new Font("Arial", 20));
        oxNum.setTextFill(Color.WHITE);
        oxNum.setTextAlignment(TextAlignment.CENTER);
        oxNum.setAlignment(Pos.CENTER);
    }

    private void setNumberLabelStyle() {
        numberLabel.setMinHeight(HEIGHT);
        numberLabel.setMinWidth(WIDTH);
        numberLabel.setFont(new Font("Arial", 50));
        numberLabel.setTextFill(Color.WHITE);
        numberLabel.setTextAlignment(TextAlignment.CENTER);
        numberLabel.setAlignment(Pos.CENTER);
    }

    public void setCard(Card card) {
        if(card==null) {
            clearCard();
            return;
        }
        this.card = card;
        unlock();
        setOx(card.getOx());
        setText(card.getNumber());
    }

    public void setText(String text) {
        numberLabel.setText(text);
    }

    public void setOx(String text) {
        oxNum.setText(text);
    }

    public void setText(Integer text) {
        setText(text.toString());
    }

    public void setOx(Integer text) {
        setOx(text.toString());
    }

    public void lock() {
        this.locked = true;
    }

    private void unlock() {
        this.locked = false;
    }

    public boolean isLocked() {
        return locked;
    }

    private void clearCard() {
        setCardAnimation(1,1);
        setText("");
        setOx("");
        lock();
    }
}
