package com.company.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CardDeque {

    public CardDeque() {

    }

    private List<Card> cardsList = new ArrayList<>();

    public List<Card> buildDeque(String[] cards) {
        HashMap<String, Card> cardMap = new HashMap<>();

        int counter = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 0; i <= 12; i++) {
                cardsList.add(new Card(suit, i + 1));
                cardMap.put(cardsList.get(counter).toString(), cardsList.get(counter));
                counter++;
            }
        }

        List<Card> cards1 = new ArrayList<>();
        for (String card : cards) {
            cards1.add(cardMap.get(card));
        }

        return cards1;
    }


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
