
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.piles;

import com.company.models.Card;
import com.company.models.exceptions.PileEmptyException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WastePile extends Pile implements Serializable {

    //Remove all cards from pile and return them in reverse order facing opposite way of before
    public List<Card> takeTurnedPile() {
        List<Card> newCardsList = new ArrayList<>();
        while (!cards.isEmpty()) {
            int index = cards.size() - 1;
            cards.get(index).setFaceUp(!cards.get(index).isFaceUp());
            newCardsList.add(cards.remove(index));
        }
        return newCardsList;
    }

    //Returns top card or throws error if pile is empty
    public Card draw() throws PileEmptyException {
        if (cards.size() == 0) {
            throw new PileEmptyException("Tried to draw from empty waste pile.");
        }
        //Remove and return top card
        return cards.remove(cards.size() - 1);
    }

    @Override
    public WastePile clone() {
        WastePile clone = new WastePile();
        for (int i = 0; i < this.cards.size(); i++) {
            clone.addCard(this.cards.get(i).clone());
        }
        return clone;
    }
}
