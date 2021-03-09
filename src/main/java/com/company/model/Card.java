package com.company.model;

public class Card {
    public enum Suit {
        Clubs, Spades, Hearts, Diamonds
    }

    private Suit suit;
    private int value;
    private boolean faceUp;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
        this.faceUp = false;
    }

    public Card(Suit suit, int value, boolean faceUp) {
        this.suit = suit;
        this.value = value;
        this.faceUp = faceUp;
    }

    public static final int KING = 13;
    public static final int QUEEN = 12;
    public static final int JACK = 11;
    public static final int ACE = 1;

    public boolean isBlack() {
        return (this.suit == Suit.Clubs || this.suit == Suit.Spades);
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFaceUp() {
        return faceUp;
    }

    public void setFaceUp(boolean faceUp) {
        this.faceUp = faceUp;
    }

    @Override
    public String toString() {
        if (value == 11) {
            return "J" + suit.toString().charAt(0);
        }
        if (value == 12) {
            return "Q" + suit.toString().charAt(0);
        }
        if (value == 13) {
            return "K" + suit.toString().charAt(0);
        }
        if (value == 1) {
            return "A" + suit.toString().charAt(0);
        } else {
            return value + "" + suit.toString().charAt(0);
        }
    }

    public boolean equals(Card card) {
        return (this.suit == card.suit) && (this.value == card.value);
    }

    //Returns true if "this" can be placed on top of bottomCard in the tableau
    public boolean isTableauCompatibleOn(Card bottomCard){
        boolean suitMatches = bottomCard.isBlack() != this.isBlack();
        boolean valueMatches = this.getValue() == bottomCard.getValue() - 1;
        return suitMatches && valueMatches;
    }
}
