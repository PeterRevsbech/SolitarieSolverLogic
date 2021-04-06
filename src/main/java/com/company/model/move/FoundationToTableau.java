package com.company.model.move;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

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
                    } else {
                        //If we are moving card onto non-empty pile
                        move.setFromParent(topCard);
                        move.setToCard(toPile.getTopCard());
                    }
                }
            }
        }

        return null;
    }

    public static List<SpecificMove> getAllMoves(ISolitaireState state){
        return null;
    }

    @Override
    public String toString() {
        return "FoundationToTableau";
    }
}
