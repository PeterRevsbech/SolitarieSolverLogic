
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.piles;

import java.io.Serializable;

public class StockPile extends Pile implements Serializable {

    @Override
    public StockPile clone() {
        StockPile clone = new StockPile();
        for (int i = 0; i < this.cards.size(); i++) {
            clone.addCard(this.cards.get(i).clone());
        }
        return clone;
    }
}
