package com.company.model.state;

import com.company.model.*;
import com.company.model.move.MoveType;

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

    void setKnownStockWaste(List<Card> stockWaste);

    ISolitaireState clone();

    default void swapStockTopCard(Card card) {
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

        //Now movedCard should be removed
        getWastePile().addCard(movedCard);
    }
}
