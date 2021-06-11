package com.company.models.moves.movestypes;

import com.company.models.SpecificMove;
import com.company.models.moves.MoveType;
import com.company.models.states.ISolitaireState;

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
            move.setToCard(state.getWasteTop());
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
