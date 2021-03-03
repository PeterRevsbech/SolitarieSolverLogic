package com.company.model.state;

import com.company.model.*;
import com.company.utils.PrintGameState;

public class OpenSolitaireState implements ISolitaireState, Cloneable {

    private WastePile wastePile;
    private StockPile stockPile;
    private Tableau tableau;
    private Foundation foundation;


    public static OpenSolitaireState newGame() {
        OpenSolitaireState state = new OpenSolitaireState();
        CardDeque deque = new CardDeque();

        //Initialize Tableau
        state.setTableau(new Tableau());
        for (int i = 0; i < 7; i++) {

            Pile pile = state.getTableau().getPiles()[i];

            for (int j = 0; j < i + 1; j++) {
                pile.addCard(deque.draw());
                if (j == i) {
                    //Turn the uppermost card in each pile face up
                    pile.getCards().get(i).setFaceUp(true);
                }
            }
        }

        //Initialize Foundation
        state.setFoundation(new Foundation());

        //Initialize WastePile
        state.setWastePile(new WastePile());

        //Initialize StockPile
        state.setStockPile(new StockPile());
        while (!deque.getCardsList().isEmpty()) {
            state.getStockPile().addCard(deque.draw());
        }
        return state;
    }

    public WastePile getWastePile() {
        return wastePile;
    }

    public void setWastePile(WastePile wastePile) {
        this.wastePile = wastePile;
    }

    public StockPile getStockPile() {
        return stockPile;
    }

    public void setStockPile(StockPile stockPile) {
        this.stockPile = stockPile;
    }

    public Tableau getTableau() {
        return tableau;
    }

    @Override
    public boolean isStockEmpty() {
        return stockPile.isEmpty();
    }

    public void setTableau(Tableau tableau) {
        this.tableau = tableau;
    }

    @Override
    public Card getWasteTop() {
        if (wastePile.getCards().size() > 0) {
            return wastePile.getCards().get(wastePile.getCards().size() - 1);
        } else return null;
    }

    public Foundation getFoundation() {
        return foundation;
    }

    public void setFoundation(Foundation foundation) {
        this.foundation = foundation;
    }

    @Override
    public ISolitaireState clone() {
        try {
            return (ISolitaireState) super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
