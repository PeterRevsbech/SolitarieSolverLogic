package com.company.models;

import com.company.models.exceptions.SolitarieException;

import java.io.Serializable;

public class Card implements Serializable {
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
        if (value == 0 && suit == null) {
            return "*";
        }

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
    public boolean isTableauCompatibleOn(Card bottomCard) {
        if (bottomCard == null) {
            return false;
        }
        boolean suitMatches = bottomCard.isBlack() != this.isBlack();
        boolean valueMatches = this.getValue() == bottomCard.getValue() - 1;
        return suitMatches && valueMatches;
    }

    public Card clone() {
        return new Card(this.getSuit(), this.getValue(), this.isFaceUp());
    }


    public static int valueFromString(String input) throws SolitarieException {
        if (input.contains("A")) {
            return Card.ACE;
        } else if (input.contains("2")) {
            return 2;
        } else if (input.contains("3")) {
            return 3;
        } else if (input.contains("4")) {
            return 4;
        } else if (input.contains("5")) {
            return 5;
        } else if (input.contains("6")) {
            return 6;
        } else if (input.contains("7")) {
            return 7;
        } else if (input.contains("8")) {
            return 8;
        } else if (input.contains("9")) {
            return 9;
        } else if (input.contains("1 0")) {
            return 10;
        } else if (input.contains("J")) {
            return Card.JACK;
        } else if (input.contains("Q")) {
            return Card.QUEEN;
        } else if (input.contains("K")) {
            return Card.KING;
        }
        throw new SolitarieException("Unkown card string - cannot match value of card" + input);
    }

    public static Suit suitFromString(String input) throws SolitarieException {
        if (input.contains("H")) {
            return Suit.Hearts;
        } else if (input.contains("D")) {
            return Suit.Diamonds;
        } else if (input.contains("C")) {
            return Suit.Clubs;
        } else if (input.contains("S")) {
            return Suit.Spades;
        }
        throw new SolitarieException("Unkown suit string - cannot match suit of card" + input);
    }
}
