package com.company.model.move;

import com.company.Solitaire;
import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.Tableau;
import com.company.model.exceptions.SolitarieException;
import com.company.model.state.ISolitaireState;

public class TableauToTableauReveal1 extends MoveType{

    public SpecificMove getMove(ISolitaireState state) {

        SpecificMove nextMove;

        Tableau tableau = state.getTableau();

        //For each pile in tableau
        for (Pile pile : tableau.getPiles()) {

            //If pile is empty, it cannot contain the fromCard
            for (Card card : pile.getCards()){
                //For each moveable card
                if (card.isFaceUp()){
                    //Check if we can move this card somewhere else in tableau
                    Pile compatiblePile = tableau.getCompatiblePile(card,pile);
                    if (compatiblePile != null){
                        //If move is possible - check if we can reveal in next move

                        //Find candidateMove
                        ISolitaireState cloneState = state.clone();
                        SpecificMove candidateMove = new SpecificMove(new TableauToTableau());
                        candidateMove.setFromParent(card);
                        candidateMove.setToCard(compatiblePile.getTopCard());

                        //Execute candidateMove
                        try{
                            Solitaire.tableauToTableau(cloneState,candidateMove);
                        } catch (SolitarieException e ){
                            System.out.println("TableauToTableauReveal1 error in cloned state");
                            e.printStackTrace();
                        }

                        //Check if reveal is possible - either FoundationToTableauReveal or TableauToTableauReveal
                        TableauToTableauReveal tableauToTableauReveal = new TableauToTableauReveal();
                        nextMove = tableauToTableauReveal.getMove(cloneState);
                        if (nextMove != null) {
                            return candidateMove;
                        }

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
