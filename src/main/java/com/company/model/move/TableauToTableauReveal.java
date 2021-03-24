package com.company.model.move;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.Tableau;
import com.company.model.state.ISolitaireState;

public class TableauToTableauReveal extends TableauToTableau {
    public SpecificMove getMove(ISolitaireState state) {

        SpecificMove move = new SpecificMove(new TableauToTableauReveal());

        Tableau tableau = state.getTableau();

        //For each pile in tableau
        for (Pile pile : tableau.getPiles()) {
            //If pile is empty, it cannot contain the fromCard

            //Gets the north most card which are faceup
            Card currentCard = pile.getFirstFaceUpCard();
            if (currentCard != null && pile.isPossibleReveal(currentCard)) {
                //If the north most face up card can reveal a new card if moved
                //Check if we can move this card somewhere else in tableau
                Pile compatiblePile = tableau.getCompatiblePile(currentCard, pile);
                if (compatiblePile != null) {
                    move.setToCard(compatiblePile.getTopCard());
                    move.setFromParent(currentCard);
                    return move;
                }
            }
        }

        //If it was not possible, return null
        return null;
    }

    @Override
    public String toString() {
        return "TableauToTableauReveal";
    }
}



