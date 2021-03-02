package com.company.model.state;

import com.company.model.*;

public interface ISolitaireState extends Cloneable {

    public Card getWasteTop();
    public WastePile getWastePile();
    public StockPile getStockPile();
    public Foundation getFoundation();
    public Tableau getTableau();
    public boolean isStockEmpty();
    public String toString();
    public ISolitaireState clone();

}
