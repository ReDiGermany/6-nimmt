package com.github.redigermany.sechsnimmt.controller;

import java.util.Objects;

public class Card {
    private final int number;
    private int ox = 1;

    public Card(int number){
        this.number = number;
        setOx();
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getOx() {
        return ox;
    }

    private void setOx(){
        if(number==55) ox = 7;
        else if(number%11==0) ox = 5;
        else if(number%10==0) ox = 3;
        else if(number%5==0) ox = 2;
    }

    @Override
    public boolean equals(Object o) {
//        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number == card.number && ox == card.ox;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, ox);
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", ox=" + ox +
                '}';
    }
}
