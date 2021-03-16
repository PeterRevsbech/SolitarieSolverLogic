package com.company.model.move;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

public class TableauToFoundationReveal extends TableauToFoundation {
    public SpecificMove getMove(ISolitaireState state) {
        //Only needs to specify fromParent - toChild is implicit

        SpecificMove move = new SpecificMove(new TableauToFoundationReveal());

        for (Pile pile : state.getTableau().getPiles()) {
            Card card = pile.getTopCard();

            // Must fit in foundation and must be face down

            if (card != null && pile.isPossibleReveal(card)){
                if (card.getValue()==Card.ACE){ //If card is ace
                    move.setFromParent(card);
                    return move;

                } else{ //If card is not ace - check if it fits in foundation
                    //Ask foundation, if it can recieve this card
                    if(state.getFoundation().cardMatchesFoundation(card)){
                        move.setFromParent(card);
                        return move;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "TableauToFoundationReveal";
    }
}
