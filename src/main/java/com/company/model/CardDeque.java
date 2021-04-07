package com.company.model;

import javax.management.relation.RelationNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardDeque {

    private List<Card> cardsList = new ArrayList<>();

    public CardDeque(boolean isShuffled, int dataSeed) {
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 1; i <= 13; i++) {
                cardsList.add(new Card(suit, i));
            }
        }

        if (isShuffled) {
            shuffle(dataSeed);
        }
    }

    public void shuffle(int dataSeed) {
        Random rand = new Random();
        if (dataSeed != -1) {
            rand = new Random(dataSeed);
        }


        for (int i = 0; i < 1000; i++) {
            int a, b;
            a = (int) (rand.nextDouble() * cardsList.size());
            b = (int) (rand.nextDouble() * cardsList.size());

            Card card1 = cardsList.get(a);
            Card card2 = cardsList.get(b);

            cardsList.set(a, card2);
            cardsList.set(b, card1);
        }
    }

    public Card draw() {
        return cardsList.remove(0);
    }

    public List<Card> getCardsList() {
        return cardsList;
    }

    public void setCardsList(List<Card> cardsList) {
        this.cardsList = cardsList;
    }
}
