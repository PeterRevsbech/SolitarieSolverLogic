package com.company.model.move.movestypes;

import com.company.model.SpecificMove;
import com.company.model.move.MoveType;
import com.company.model.state.ISolitaireState;

import java.util.ArrayList;
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

    public static List<SpecificMove> getAllMoves(ISolitaireState state) {
        List<SpecificMove> moves = new ArrayList<>();
        StockMove stockMove = new StockMove();
        if (stockMove.getMove(state) != null) {
            moves.add(stockMove.getMove(state));
        }
        return moves;
    }
}