
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
import java.util.Arrays;
import java.util.List;

public class Tableau implements Serializable {

    private Pile[] piles = new Pile[7];

    public Tableau() {
        for (int i = 0; i < 7; i++) {
            piles[i] = new Pile();
            piles[i].setFanned(true);
        }
    }

    public Pile[] getPiles() {
        return piles;
    }

    public Pile getPile(int index) {
        return piles[index];
    }

    public void setPiles(Pile[] piles) {
        this.piles = piles;
    }

    @Override
    public String toString() {
        return Arrays.toString(piles);
    }

    //Return the one of the seven piles that contains card
    //If none of them do - throw an error
    public Pile getPileContainingCard(Card card) throws CardNotFoundException {
        for (Pile pile : piles) {
            if (pile.contains(card)) {
                return pile;
            }
        }
        throw new CardNotFoundException(String.format("Card %s not found in tableuPile", card.toString()));
    }

    public int getMaxTableauLength() {
        int max = 0;
        for (Pile pile : piles) {
            if (pile.getCards().size() > max) {
                max = pile.getCards().size();
            }
        }
        return max;
    }

    public Pile getFirstEmptyPile() {
        //Returns first empty pile or null if no empty pile present
        for (int i = 0; i < 7; i++) {
            if (piles[i].isEmpty()) {
                return piles[i];
            }
        }
        return null;
    }

    //Finds a pile, where fromCard can be moved to, if there is any - otherwise returns null
    //If fromPile is not null, means card was moved from this pile, and cannot be move TO this pile
    public Pile getCompatiblePile(Card fromCard, Pile fromPile) {
        List<Pile> compatiblePiles = getAllCompatiblePiles(fromCard, fromPile);
        return compatiblePiles.isEmpty() ? null : compatiblePiles.get(0);
    }

    //Returns list of piles in tableau, that card can be moved to.
    //Frompile indicates the pile, that the card was moved from - if it is null, it was not moved from tableau
    public List<Pile> getAllCompatiblePiles(Card fromCard, Pile fromPile) {
        List<Pile> compatiblePiles = new ArrayList<>();

        if (fromCard == null) {
            return compatiblePiles;
        }

        for (Pile pile : piles) {
            //Must not be fromPile

            if (!pile.equals(fromPile)) {
                //If the pile is empty - only king can be placed

                Card topCard = pile.getTopCard();
                boolean compatible = fromCard.isTableauCompatibleOn(topCard);

                if (pile.isEmpty()) {
                    if (fromCard.getValue() == Card.KING) {
                        compatiblePiles.add(pile);
                    }
                }

                //Otherwise if fromCard can be placed on top of top card in pile
                else if (compatible) {
                    compatiblePiles.add(pile);
                }
            }
        }
        return compatiblePiles;
    }

    public Tableau clone() {
        Tableau clone = new Tableau();
        for (int i = 0; i < this.piles.length; i++) {
            clone.piles[i] = this.piles[i].clone();
        }
        return clone;
    }
}
