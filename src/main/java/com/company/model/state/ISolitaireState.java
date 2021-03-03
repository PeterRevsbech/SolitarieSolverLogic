package com.company.model.state;

import com.company.model.*;

public interface ISolitaireState extends Cloneable {

    Card getWasteTop();

    WastePile getWastePile();

    StockPile getStockPile();

    Foundation getFoundation();

    Tableau getTableau();

    boolean isStockEmpty();

    String toString();

    ISolitaireState clone();

}
