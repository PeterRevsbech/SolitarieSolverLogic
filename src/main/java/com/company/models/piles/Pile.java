
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.piles;

import com.company.models.Card;
import com.company.models.exceptions.CardNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pile implements Serializable {

    protected List<Card> cards;
    protected boolean fanned;

    public Pile() {
        this.cards = new ArrayList<>();
        this.fanned = false;
    }

    public List<Card> getChildren(Card card) {
        int index = indexOf(card);
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

    //Returns the north most face up card or null if pile is empty
    public Card getFirstFaceUpCard() {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).isFaceUp()) {
                return cards.get(i);
            }
        }
        return null;
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

    public int getIndexOfCard(Card card) { //Given a card, this method finds the index of that card.

        for (int i = 0; i < cards.size(); i++) {

            if (cards.get(i).equals(card))
                return i;
        }
        return -1;
    }

    public boolean isPossibleReveal(Card card) { //Returns a boolean depending on if the given card can reveal.

        int cardIndex = getIndexOfCard(card);

        if (cardIndex == 0)
            return false;

        if (cards.get(cardIndex - 1).isFaceUp())
            return false;

        return true;
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

    public boolean equals(Pile pile) {
        if (pile == null) {
            return false;
        }

        if (pile.getCards().size() != this.getCards().size()) {
            return false;
        }
        //For each card
        for (int i = 0; i < pile.getCards().size(); i++) {
            //If cards are different - return false
            if (!pile.getCards().get(i).equals(this.cards.get(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(Card card) {
        for (Card pileCard : cards) {
            if (pileCard.getSuit() == card.getSuit() && pileCard.getValue() == card.getValue() && pileCard.isFaceUp() == card.isFaceUp()) {
                return true;
            }
        }
        return false;
    }

    public Pile clone() {
        Pile clone = new Pile();
        for (int i = 0; i < this.cards.size(); i++) {
            clone.addCard(this.cards.get(i).clone());
        }
        return clone;
    }

    public int indexOf(Card card) {
        for (int i = 0; i < cards.size(); i++) {
            if (card.equals(cards.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
