
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.moves.movestypes;

import com.company.logic.SolitaireSolver;
import com.company.models.Card;
import com.company.models.piles.Pile;
import com.company.models.SpecificMove;
import com.company.models.piles.Tableau;
import com.company.models.exceptions.SolitarieException;
import com.company.models.moves.MoveType;
import com.company.models.states.ISolitaireState;

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

    public static List<SpecificMove> getAllMoves(ISolitaireState state) {
        List<SpecificMove> specificMoveList = new ArrayList<>();
        Tableau tableau = state.getTableau();

        //For each pile in tableau
        for (Pile pile : tableau.getPiles()) {
            //If pile is empty, it cannot contain the fromCard
            for (Card card : pile.getCards()) {
                //For each moveable card
                if (card.isFaceUp()) {
                    //Check if we can move this card somewhere else in tableau
                    List<Pile> compatiblePiles = tableau.getAllCompatiblePiles(card, pile);
                    for (Pile compatiblePile : compatiblePiles) {
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
        return "Tableau -> Tableau";
    }

    //Method that should avoid taking an ace from waste and placing it on 2 in tableau
    //should also force aces and two's (but only those) into the foundation piles (the method could just do this if possible)
    /*public static boolean isUselessAceToTwoMove(SpecificMove move, ISolitaireState state) {
    }*/

/*    public static boolean isUselessMoveBackAndForthMove(){

    }*/


    //Tells if a move is a useless king move ==> moving a king from an empty tableau pile to another empty tableau pile
    public static boolean isUselessKingMove(SpecificMove move, ISolitaireState state) {

        if (!(move.getMoveType() instanceof TableauToTableau)) {
            return false;
        }
        if (move.getFromParent().getValue() != Card.KING) {
            return false;
        }
        if (move.getToCard() != null) {
            return false;
        }

        try {
            Card kingCard = move.getFromParent();
            Pile kingPile = state.getTableau().getPileContainingCard(kingCard);
            if (kingPile.getIndexOfCard(kingCard) != 0) {
                return false;
            }
        } catch (SolitarieException e) {
            System.out.println("Useless king Tableau to Tableau -move wasn't recognized");
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
