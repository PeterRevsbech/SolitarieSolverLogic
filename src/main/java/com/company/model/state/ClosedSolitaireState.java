package com.company.model.state;

import com.company.model.*;

public class ClosedSolitaireState implements ISolitaireState{


    @Override
    public Card getWasteTop() {
        return null;
    }

    @Override
    public WastePile getWastePile() {
        return null;
    }

    @Override
    public StockPile getStockPile() {
        return null;
    }

    @Override
    public Foundation getFoundation() {
        return null;
    }

    @Override
    public Tableau getTableau() {
        return null;
    }

    @Override
    public boolean isStockEmpty() {
        return false;
    }

    @Override
    public ISolitaireState clone(){
        return this.clone();
    }
}
