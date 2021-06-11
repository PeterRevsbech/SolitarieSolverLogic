package com.company.models.moves.movestypes;

import com.company.models.Card;
import com.company.models.piles.Pile;
import com.company.models.SpecificMove;
import com.company.models.moves.MoveType;
import com.company.models.states.ISolitaireState;

import java.util.ArrayList;
import java.util.List;

public class FoundationToTableau extends MoveType {
    @Override
    public SpecificMove getMove(ISolitaireState state) {
        SpecificMove move = new SpecificMove(new FoundationToTableau());

        for (Pile foundationPile : state.getFoundation().getPiles()) {
            if (!foundationPile.isEmpty()) {
                //See if top card can be moved to somewhere in tableau
                Card topCard = foundationPile.getTopCard();

                Pile toPile = state.getTableau().getCompatiblePile(topCard, null);

                if (toPile != null) {
                    if (toPile.isEmpty()) {
                        //IF we are moving a king back to an empty pile
                        move.setFromParent(topCard);
                        move.setToCard(null);
                        return move;
                    } else {
                        //If we are moving card onto non-empty pile
                        move.setFromParent(topCard);
                        move.setToCard(toPile.getTopCard());
                        return move;
                    }
                }
            }
        }
        return null;
    }

    public static List<SpecificMove> getAllMoves(ISolitaireState state) {

        List<SpecificMove> specificMoveList = new ArrayList<>();
        SpecificMove move = new SpecificMove(new FoundationToTableau());

        for (Pile foundationPile : state.getFoundation().getPiles()) {
            if (!foundationPile.isEmpty()) {
                //See if top card can be moved somewhere in tableau
                Card topCard = foundationPile.getTopCard();
                Pile toPile = state.getTableau().getCompatiblePile(topCard, null);

                if (toPile != null) {
                    if (toPile.isEmpty()) {
                        //If we are moving a king back to an empty pile
                        move.setFromParent(topCard);
                        move.setToCard(null);
                        //Adding the move to the list
                        specificMoveList.add(move);
                    } else {
                        //If we are moving card onto non-empty pile
                        move.setFromParent(topCard);
                        move.setToCard(toPile.getTopCard());
                        //Adding the move to the list
                        specificMoveList.add(move);
                    }
                }
            }
        }
        return specificMoveList;
    }

    @Override
    public String toString() {
        return "Foundation -> Tableau";
    }
}
