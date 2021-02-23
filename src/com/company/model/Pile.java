package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class Pile {

    private List<Card> cards;
    private boolean fanned;


    public List<Card> getChildren(Card card){
        int index = cards.indexOf(card);
        return getChildren(index);
    }

    public List<Card> getChildren(int i){
        List<Card> result = new ArrayList<>();
        for (int j = i; j < cards.size(); j++) {
            result.add(cards.get(j));
        }

        return result;
    }

    public void removeCards(List<Card> cardsToRemove){
        for (Card card: cardsToRemove) {
            cards.remove(card);
        }
    }

    public void addCards(List<Card> cardsToAdd){
        for (Card card: cardsToAdd) {
            cards.add(card);
        }
    }


    public boolean isEmpty(){
        return cards.isEmpty();
    }


    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public boolean isFanned() {
        return fanned;
    }

    public void setFanned(boolean fanned) {
        this.fanned = fanned;
    }
}
