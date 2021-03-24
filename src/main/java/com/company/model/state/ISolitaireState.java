package com.company.model.state;

import com.company.model.*;

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

    public ISolitaireState clone();

}
