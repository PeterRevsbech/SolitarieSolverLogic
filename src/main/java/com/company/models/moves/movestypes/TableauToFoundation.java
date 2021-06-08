package com.company.models.moves.movestypes;

import com.company.models.Card;
import com.company.models.piles.Pile;
import com.company.models.SpecificMove;
import com.company.models.moves.MoveType;
import com.company.models.states.ISolitaireState;

import java.util.ArrayList;
import java.util.List;

public class TableauToFoundation extends MoveType {
    @Override
    public SpecificMove getMove(ISolitaireState state) {
        //Only needs to specify fromParent - toChild is implicit

        SpecificMove move = new SpecificMove(new TableauToFoundation());

        for (Pile pile : state.getTableau().getPiles()) {
            Card card = pile.getTopCard();
            if (card != null) {
                if (card.getValue() == Card.ACE) { //If card is ace
                    move.setFromParent(card);
                    return move;

                } else { //If card is not ace - check if it fits in foundation
                    //Ask foundation, if it can recieve this card
                    if (state.getFoundation().cardMatchesFoundation(card)) {
                        move.setFromParent(card);
                        return move;
                    }
                }
            }
        }
        return null;
    }

    public static List<SpecificMove> getAllMoves(ISolitaireState state) {
        List<SpecificMove> moves = new ArrayList<>();

        for (Pile pile : state.getTableau().getPiles()) {
            Card card = pile.getTopCard();
            if (card != null) {
                if (card.getValue() == Card.ACE) { //If card is ace
                    SpecificMove move = new SpecificMove(new TableauToFoundation());
                    move.setFromParent(card);
                    moves.add(move);

                } else { //If card is not ace - check if it fits in foundation
                    //Ask foundation, if it can recieve this card
                    if (state.getFoundation().cardMatchesFoundation(card)) {
                        SpecificMove move = new SpecificMove(new TableauToFoundation());
                        move.setFromParent(card);
                        moves.add(move);
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return "Tableau -> Foundation";
    }
}
