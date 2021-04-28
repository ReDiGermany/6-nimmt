package controller;

public class Card {
    private final int number;
    private int ox = 1;

    public Card(int number){
        this.number = number;
        setOx();
    }

    public int getNumber() {
        return number;
    }

    public int getOx() {
        return ox;
    }

    private void setOx(){
        if(number==55) ox = 7;
        else if(number%11==0) ox = 5;
        else if(number%10==0) ox = 3;
        else if(number%5==0) ox = 2;
    }

    @Override
    public String toString() {
        return "Card{" +
                "number=" + number +
                ", ox=" + ox +
                '}';
    }
}
