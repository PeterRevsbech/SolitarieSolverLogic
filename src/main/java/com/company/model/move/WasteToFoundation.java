package com.company.model.move;

import com.company.model.Card;
import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

import java.util.List;

public class WasteToFoundation extends MoveType {
    @Override
    public SpecificMove getMove(ISolitaireState state) {
        SpecificMove move = new SpecificMove(new WasteToFoundation());

        Card wasteCard = state.getWasteTop();
        if (wasteCard == null) {
            //not possible, if waste is empty
            return null;
        }

        //If card matches foundation - return move
        if (state.getFoundation().cardMatchesFoundation(wasteCard)) {
            return move;
        }

        //Else return null
        return null;
    }

    public static List<SpecificMove> getAllMoves(ISolitaireState state){
        return null;
    }

    @Override
    public String toString() {
        return "WasteToFoundation";
    }
}
