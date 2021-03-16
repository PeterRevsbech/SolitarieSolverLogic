package com.company.model.move;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

public class WasteToTableau extends MoveType{
    @Override
    public SpecificMove getMove(ISolitaireState state) {
        SpecificMove move = new SpecificMove(new WasteToTableau());

        Card wasteCard = state.getWasteTop();
        if (wasteCard == null){
            //not possible, if waste is empty
            return null;
        }
        move.setFromParent(wasteCard);

        Pile toPile = state.getTableau().getCompatiblePile(wasteCard,null);
        if (!toPile.isEmpty()){
            move.setToCard(toPile.getTopCard());
        }
        //Else if fromCard is a king - the toPile is empty and there is no toCard - so we don't set toCard

        return move;

    }

    @Override
    public String toString() {
        return "WasteToTableau";
    }
}
