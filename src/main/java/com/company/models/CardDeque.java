
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

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
        final List<Card> cardsList2 = new ArrayList<>();
        HashMap<String, Card> cardMap = new HashMap<>();

        int counter = 0;

        //Adding all 52 cards to a hash maps which uses the shortened name as key.
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = 0; i <= 12; i++) {
                cardsList2.add(new Card(suit, i + 1));
                cardMap.put(cardsList2.get(counter).toString(), cardsList2.get(counter));
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
