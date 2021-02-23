package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class CardDeque {

    private List<Card> cardsList = new ArrayList<>();

    public CardDeque(){
        for (Card.Suit suit : Card.Suit.values()){
            for (int i = 1; i <= 13; i++) {
                cardsList.add(new Card(suit,i));
            }
        }
        shuffle();
    }

    public void shuffle(){
        for (int i = 0; i < 1000; i++) {
            int a, b;
            a= (int)(Math.random()*cardsList.size());
            b= (int)(Math.random()*cardsList.size());

            Card card1 = cardsList.get(a);
            Card card2 = cardsList.get(b);

            cardsList.set(a,card2);
            cardsList.set(b,card1);
        }
    }

    public Card draw(){
        return cardsList.remove(0);
    }

    public List<Card> getCardsList() {
        return cardsList;
    }

    public void setCardsList(List<Card> cardsList) {
        this.cardsList = cardsList;
    }
}
