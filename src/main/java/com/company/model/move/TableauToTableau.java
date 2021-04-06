package com.company.model.move;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.Tableau;
import com.company.model.state.ISolitaireState;

import java.util.ArrayList;
import java.util.List;

public class TableauToTableau extends MoveType {
    @Override
    public SpecificMove getMove(ISolitaireState state) {

        SpecificMove move = new SpecificMove(new TableauToTableau());

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
                        move.setToCard(compatiblePile.getTopCard());
                        move.setFromParent(card);
                        return move;
                    }
                }
            }
        }

        //If it was not possible, return null
        return null;
    }

    public static List<SpecificMove> getAllMoves(ISolitaireState state){
        List<SpecificMove> specificMoveList = new ArrayList<>();


        Tableau tableau = state.getTableau();

        //For each pile in tableau
        for (Pile pile: tableau.getPiles()) {
            //If pile is empty, it cannot contain the fromCard
            for(Card card : pile.getCards()){
                //For each moveable card
                if(card.isFaceUp()){
                    //Check if we can move this card somewhere else in tableau
                    List<Pile> compatiblePiles = tableau.getAllCompatiblePiles(card,pile);
                    for (Pile compatiblePile: compatiblePiles) {
                        SpecificMove move = new SpecificMove(new TableauToTableau());

                        move.setToCard(compatiblePile.getTopCard());
                        move.setFromParent(card);
                        specificMoveList.add(move);
                    }
                }
            }
        }

        return specificMoveList;
    }

    @Override
    public String toString() {
        return "TableauToTableau";
    }
}
