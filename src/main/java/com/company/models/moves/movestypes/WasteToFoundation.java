
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.moves.movestypes;

import com.company.models.Card;
import com.company.models.SpecificMove;
import com.company.models.moves.MoveType;
import com.company.models.states.ISolitaireState;

import java.util.ArrayList;
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

    public static List<SpecificMove> getAllMoves(ISolitaireState state) {
        List<SpecificMove> specificMoveList = new ArrayList<>();
        SpecificMove move = new SpecificMove(new WasteToFoundation());

        Card wasteCard = state.getWasteTop();
        if (wasteCard == null) {
            //Not possible, if waste is empty
            return specificMoveList;
        }

        //If card matches foundation - return move
        if (state.getFoundation().cardMatchesFoundation(wasteCard)) {
            move.setFromParent(state.getWasteTop());
            specificMoveList.add(move);
        }

        return specificMoveList;
    }

    @Override
    public String toString() {
        return "Waste -> Foundation";
    }
}
