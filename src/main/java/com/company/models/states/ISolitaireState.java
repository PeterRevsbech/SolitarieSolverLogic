
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.states;

import com.company.models.*;
import com.company.models.exceptions.SolitarieException;
import com.company.models.moves.MoveExecuter;
import com.company.models.moves.movestypes.*;
import com.company.models.piles.*;

import java.util.ArrayList;
import java.util.List;

public interface ISolitaireState extends Cloneable {

    //Return null if waste is empty - otherwise returns top card
    Card getWasteTop();

    WastePile getWastePile();

    StockPile getStockPile();

    Foundation getFoundation();

    Tableau getTableau();

    boolean isStockEmpty();

    String toString();

    List<Card> getKnownStockWaste();

    void setRevealedStockWaste(int knownCards);

    int getRevealedStockWaste();

    void setKnownStockWaste(List<Card> stockWaste);

    ISolitaireState clone();

    default void swapStockTopCard(Card card) throws SolitarieException {
        Card movedCard = null;
        //Find and remove the card from stock or waste
        for (int i = 0; i < getWastePile().getCards().size(); i++) {
            if (card.equals(getWastePile().getCard(i))) {
                movedCard = getWastePile().getCard(i);
                getWastePile().removeCard(movedCard);
            }
        }
        for (int i = 0; i < getStockPile().getCards().size(); i++) {
            if (card.equals(getStockPile().getCard(i))) {
                movedCard = getStockPile().getCard(i);
                getStockPile().removeCard(movedCard);
            }
        }

        if (movedCard == null) {
            throw new SolitarieException("Tried to swap card to wasteTop, but card was not in stock or waste.");
        }

        //Now movedCard should be removed
        getWastePile().addCard(movedCard);
    }

    default ISolitaireState simulateMoveWithClone(ISolitaireState state, SpecificMove move) {
        ISolitaireState cloneState = state.clone();

        //Clone fromcard and to card to avoid interfering with original cards
        if (move.getToCard() != null) {
            move.setToCard(move.getToCard().clone());
        }
        if (move.getFromParent() != null) {
            move.setFromParent(move.getFromParent().clone());
        }

        try {
            MoveExecuter.executeMove(cloneState, move);
        } catch (SolitarieException e) {
            System.out.println("Error in simulating move.");
            e.printStackTrace();
        }

        return cloneState;
    }

    default int getNumberOfFaceDownCards() {
        Tableau tableau = getTableau();
        int count = 0;
        for (Pile pile : tableau.getPiles()) {
            for (Card card : pile.getCards()) {
                if (!card.isFaceUp()) {
                    count++;
                }
            }
        }

        return count;
    }

    default List<SpecificMove> getAllPossibleMoves() {
        List<SpecificMove> moves = new ArrayList<>();
        moves.addAll(StockMove.getAllMoves(this));
        moves.addAll(FoundationToTableau.getAllMoves(this));
        moves.addAll(TableauToFoundation.getAllMoves(this));
        moves.addAll(TableauToTableau.getAllMoves(this));
        moves.addAll(WasteToFoundation.getAllMoves(this));
        moves.addAll(WasteToTableau.getAllMoves(this));
        return moves;
    }


    default boolean isStockKnown() {
        return getRevealedStockWaste() >= 24;
    }


}
