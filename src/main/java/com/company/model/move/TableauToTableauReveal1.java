package com.company.model.move;

import com.company.Solitaire;
import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.Tableau;
import com.company.model.exceptions.SolitarieException;
import com.company.model.state.ISolitaireState;

public class TableauToTableauReveal1 extends TableauToTableau {

    public SpecificMove getMove(ISolitaireState state) {

        SpecificMove nextMove;

        Tableau tableau = state.getTableau();

        //For each pile in tableau
        for (Pile pile : tableau.getPiles()) {

            //If pile is empty, it cannot contain the fromCard
            for (Card card : pile.getCards()) {
                //For each moveable card
                if (card.isFaceUp()) {
                    //Check if we can move this card somewhere else in tableau
                    Pile compatiblePile = tableau.getCompatiblePile(card, pile);
                    if (compatiblePile != null) {
                        //If move is possible - check if we can reveal in next move

                        //Create candidateMove
                        SpecificMove candidateMove = new SpecificMove(new TableauToTableauReveal1());
                        candidateMove.setFromParent(card);
                        candidateMove.setToCard(compatiblePile.getTopCard());

                        //Simluate the move in CloneState
                        ISolitaireState cloneState = state.simulateMoveWithClone(state,candidateMove);

                        //Check if reveal is possible - either TableauToTableauReveal or TableauToFoundationReveal
                        //TableauToTableauReveal
                        TableauToTableauReveal tableauToTableauReveal = new TableauToTableauReveal();
                        nextMove = tableauToTableauReveal.getMove(cloneState);
                        if (nextMove != null) {
                            return candidateMove;
                        }

                        //TableauToFoundationReveal
                        TableauToFoundationReveal tableauToFoundationReveal = new TableauToFoundationReveal();
                        nextMove = tableauToFoundationReveal.getMove(cloneState);
                        if (nextMove != null) {
                            return candidateMove;
                        }
                    }
                }
            }
        }

        //If it was not possible, return null
        return null;
    }

    @Override
    public String toString() {
        return "TableauToTableauReveal1";
    }
}
