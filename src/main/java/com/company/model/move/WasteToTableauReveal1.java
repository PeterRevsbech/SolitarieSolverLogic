package com.company.model.move;


import com.company.Solitaire;
import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.exceptions.SolitarieException;
import com.company.model.state.ISolitaireState;

import java.util.List;

public class WasteToTableauReveal1 extends WasteToTableau{
    //This is when the topcard in wastepile makes either a TableauToTableauReveal or TableauToFoundationReveal1 possible in 1 move

    @Override
    public SpecificMove getMove(ISolitaireState state) {
        SpecificMove move = new SpecificMove(new WasteToTableauReveal1());
        SpecificMove candidateMove = null;
        Card topCard = state.getWasteTop();

        //If topCard was moved to somewhere in tableau - would TableauToTableauReveal or TableauToFoundationReveal1 be possible
        List<Pile> compatiblePiles = state.getTableau().getAllCompatiblePiles(topCard,null);
        for (Pile pile: compatiblePiles) {
            //Make clones
            ISolitaireState cloneState = state.clone();

            //Move cloneTop onto compatible pile
            candidateMove = new SpecificMove(new WasteToTableau());
            candidateMove.setFromParent(null); //Not needed, but indicates that fromparent is irellevant
            candidateMove.setToCard(pile.getTopCard());

            try {
                Solitaire.wasteToTableu(cloneState,candidateMove);
            } catch (SolitarieException e){
                System.out.println("TableauToTableauReveal1 error in cloned state");
                e.printStackTrace();
            }

            //See if Reveal move possible
            //TableauToTabeleauReveal
            TableauToTableauReveal tableauToTableauReveal = new TableauToTableauReveal();
            candidateMove = tableauToTableauReveal.getMove(cloneState);
            if (candidateMove != null){
                move.setToCard(pile.getTopCard());
                return move;
            }

            //See if reveal is possbile, with one tableauToTableauMove
            TableauToTableauReveal1 tableauToTableauReveal1 = new TableauToTableauReveal1();
            candidateMove = tableauToTableauReveal1.getMove(cloneState);
            if (candidateMove != null){
                move.setFromParent(null); //Moving from waste top - so null
                move.setToCard(pile.getTopCard()); //Moving to top of the pile, that topCard was compatible with
                return move;
            }

        }

        return null;

    }

    @Override
    public String toString() {
        return "WasteToTableauReveal1";
    }
}
