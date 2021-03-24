package com.company.model;

import com.company.model.exceptions.InvalidMoveException;
import com.company.model.exceptions.SolitarieException;

import java.io.Serializable;
import java.util.Arrays;

public class Foundation implements Serializable {

    private Pile[] piles = new Pile[4];

    public Foundation() {
        for (int i = 0; i < 4; i++) {
            piles[i] = new Pile();
        }
    }

    public Pile[] getPiles() {
        return piles;
    }

    public void setPiles(Pile[] piles) {
        this.piles = piles;
    }

    @Override
    public String toString() {
        return Arrays.toString(piles);
    }

    public Pile getFoundationPileFromCard(Card card) throws SolitarieException {
        Pile pile = null;
        //Find corresponding foundation-pile
        if (card.getValue() == Card.ACE) {
            //Find first vacant pile from left and put it there
            for (int i = 0; i < 4; i++) {
                //Find first empty pile
                pile = piles[i];
                if (pile.isEmpty()) {
                    break;
                } else if (i == 3) {
                    //If we found no empty pile - throw an exception
                    throw new SolitarieException(String.format("Tried to put %s in foundation, but there was no empty pile.", card.toString()));
                }
            }

        } else { //If card is not ace
            //Find corresponding pile
            for (int i = 0; i < 4; i++) {
                pile = piles[i];


                if (!pile.isEmpty() && pile.getTopCard().getSuit() == card.getSuit()) {
                    break;
                } else if (i == 3) {
                    //If we found no empty pile - throw an exception
                    throw new SolitarieException(String.format("Tried to put %s in foundation, but no other cards of same suit were present", card.toString()));
                }
            }

            //Check if child is not there
            if (pile.getTopCard().getValue() != card.getValue() - 1) {
                throw new InvalidMoveException(String.format("Tried to put %s in foundation, but no other cards of same suit were present", card.toString()));
            }
        }
        return pile;
    }

    public boolean cardMatchesFoundation(Card card){
        for (Pile pile:piles) {
            if (pile.getTopCard() != null){
                //Check that suit mathces
                if(pile.getTopCard().getSuit()==card.getSuit()){
                    //Check that value is 1 higher
                    if (pile.getTopCard().getValue()==card.getValue()-1){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
