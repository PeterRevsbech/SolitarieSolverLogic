package com.company.model.move;

import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

import java.util.List;

public class StockMove extends MoveType {

    @Override
    public SpecificMove getMove(ISolitaireState state) {
        if (state.isStockEmpty() && state.getWastePile().isEmpty()) {
            return null;
        } else {
            SpecificMove move = new SpecificMove();
            move.setMoveType(new StockMove());
            return move;
        }
    }

    @Override
    public String toString() {
        return "StockMove";
    }


    public static List<SpecificMove> getAllMoves(ISolitaireState state){
        List<SpecificMove> specificMoveList = null;

        if (state.isStockEmpty() && state.getWastePile().isEmpty()) {
            return specificMoveList;
        } else {
            SpecificMove move = new SpecificMove();
            move.setMoveType(new StockMove());
            specificMoveList.add(move);
        }


        return specificMoveList;
    }
}
