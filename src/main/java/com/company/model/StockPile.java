package com.company.model;

public class StockPile extends Pile{

    @Override
    public StockPile clone() {
        StockPile clone = new StockPile();
        for (int i = 0; i < this.cards.size(); i++) {
            clone.addCard(this.cards.get(i).clone());
        }
        return clone;
    }
}
