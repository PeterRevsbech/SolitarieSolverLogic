
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.moves.movestypes;

import com.company.models.Card;
import com.company.models.piles.Pile;
import com.company.models.SpecificMove;
import com.company.models.moves.MoveType;
import com.company.models.states.ISolitaireState;

import java.util.ArrayList;
import java.util.List;

public class WasteToTableau extends MoveType {
    @Override
    public SpecificMove getMove(ISolitaireState state) {
        SpecificMove move = new SpecificMove(new WasteToTableau());

        Card wasteCard = state.getWasteTop();
        if (wasteCard == null) {
            //not possible, if waste is empty
            return null;
        }
        move.setFromParent(wasteCard);

        Pile toPile = state.getTableau().getCompatiblePile(wasteCard, null);
        if (toPile != null && !toPile.isEmpty()) {
            //If move is possible, and card to be moved is not king
            move.setToCard(toPile.getTopCard());
        } else if (toPile != null) {
            //Else if fromCard is a king - the toPile is empty and there is no toCard - so we don't set toCard
            return move;
        }
        return null;
    }

    public static List<SpecificMove> getAllMoves(ISolitaireState state) {
        SpecificMove move = new SpecificMove(new WasteToTableau());
        List<SpecificMove> specificMoveList = new ArrayList<>();

        Card wasteCard = state.getWasteTop();
        if (wasteCard == null) {
            //Not possible if waste is empty
            return specificMoveList;
        }
        move.setFromParent(wasteCard);

        List<Pile> pileList = state.getTableau().getAllCompatiblePiles(wasteCard, null);
        if (pileList != null) {
            for (Pile toPile : pileList) {
                if (toPile != null && !toPile.isEmpty()) {
                    //If move is possible, and card to be moved is not king
                    move.setToCard(toPile.getTopCard());
                    specificMoveList.add(move);

                } else if (toPile != null) {
                    //Else if fromCard is a king - the toPile is empty and there is no toCard - so we don't set toCard
                    move.setToCard(null);
                    specificMoveList.add(move);
                }

            }
        }
        return specificMoveList;
    }

    @Override
    public String toString() {
        return "Waste -> Tableau";
    }
}
