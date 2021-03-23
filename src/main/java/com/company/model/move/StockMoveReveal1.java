package com.company.model.move;

import com.company.model.Card;
import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

public class StockMoveReveal1 {

    //TODO This is when a number of stockmoves can be made, that make WasteToTableauReveal1 possible
    // ==> find one card in stock, that it it were on top, would make WasteToTableauReveal1 possible ==> make stock move

    //Shall make it possible for TableauToTableuReveal or TableuaToFaundationReveal

    public SpecificMove getMove(ISolitaireState state) {
        if (state.isStockEmpty() && state.getWastePile().isEmpty()){ //if waste and stock is empty
            return null;
        } else {

            SpecificMove move = new SpecificMove();
            move.setMoveType(new StockMove());
            return move;
        }
    }

    @Override
    public String toString() {
        return "StockMoveReveal1";
    }

}
