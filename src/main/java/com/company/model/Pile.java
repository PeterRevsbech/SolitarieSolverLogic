package com.company.model;

import com.company.model.exceptions.CardNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Pile {

    protected List<Card> cards;
    protected boolean fanned;

    public Pile() {
        this.cards = new ArrayList<>();
        this.fanned = false;
    }

    public List<Card> getChildren(Card card) {
        int index = cards.indexOf(card);
        return getChildren(index);
    }

    public List<Card> getChildren(int i) {
        List<Card> result = new ArrayList<>();
        for (int j = i; j < cards.size(); j++) {
            result.add(cards.get(j));
        }

        return result;
    }

    public void removeCards(List<Card> cardsToRemove) {
        for (Card card : cardsToRemove) {
            cards.remove(card);
        }
    }

    public void addCards(List<Card> cardsToAdd) {
        for (Card card : cardsToAdd) {
            cards.add(card);
        }
    }

    public Card removeTopCard() throws CardNotFoundException {
        Card card = getTopCard();

        if (card == null) {
            throw new CardNotFoundException("Tried to remove top card from empty pile");
        }

        cards.remove(card);

        return card;
    }

    //Returns null if no cards in pile
    public Card getTopCard() {
        if (cards.isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getCard(int index) {
        return cards.get(index);
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

    @Override
    public String toString() {
        return cards.toString();
    }
    
    public boolean equals(Pile pile){
        if (pile.getCards().size() != this.getCards().size()){
            return false;
        }
        //For each card
        for (int i = 0; i < pile.getCards().size(); i++) {
            //If cards are different - return false
            if (!pile.getCards().get(i).equals(this.cards.get(i))){
                return false;
            }
        }
        return true;
    }
}
