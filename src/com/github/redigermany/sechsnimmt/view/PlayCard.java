package com.github.redigermany.sechsnimmt.view;

import com.github.redigermany.sechsnimmt.controller.Card;
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

    public PlayCard(GUI gui) {
        this(gui, Color.TRANSPARENT);
    }

    public PlayCard(GUI gui, Color color) {
        this.gui = gui;
        this.getChildren().addAll(numberLabel, oxNum);
        setCardStyle(color);
        setNumberLabelStyle();
        setOxNumberLabelStyle();
        setListeners();
    }

    private void setListeners() {
        setOnMouseClicked(e->{
            if(gui.isAllowedToChoose()){
                System.out.println(card);
            }
        });
        setOnMouseEntered(e -> {
            if (isLocked()) return;
            setCursor(Cursor.HAND);
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100),this);
            scaleTransition.setToX(hoverScaleFactor);
            scaleTransition.setToY(hoverScaleFactor);
            scaleTransition.setFromX(1);
            scaleTransition.setFromY(1);
            scaleTransition.setCycleCount(1);
            scaleTransition.play();
        });
        setOnMouseExited(e -> {
            if (isLocked()) return;
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(100),this);
            scaleTransition.setFromX(hoverScaleFactor);
            scaleTransition.setFromY(hoverScaleFactor);
            scaleTransition.setToX(1);
            scaleTransition.setToY(1);
            scaleTransition.setCycleCount(1);
            scaleTransition.play();
        });
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
        this.card = card;
        unlock();
        oxNum.setText(card.getOx().toString());
        numberLabel.setText(card.getNumber().toString());
    }

    public void setText(String text) {
        numberLabel.setText(text);
    }

    public void setOx(String text) {
        oxNum.setText(text);
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

}
