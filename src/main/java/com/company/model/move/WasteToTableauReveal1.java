package com.company.model.move;


import com.company.Solitaire;
import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.exceptions.SolitarieException;
import com.company.model.state.ISolitaireState;

import java.util.List;

public class WasteToTableauReveal1 extends WasteToTableau {
    //This is when the topcard in wastepile makes either a TableauToTableauReveal or TableauToFoundationReveal1 possible in 1 move

    @Override
    public SpecificMove getMove(ISolitaireState state) {
        SpecificMove candidateMove = new SpecificMove(new WasteToTableauReveal1());
        SpecificMove candidateRevealMove = null;
        Card topCard = state.getWasteTop();
        if (topCard==null){
            return null;
        }

        //If topCard was moved to somewhere in tableau - would TableauToTableauReveal be possible
        List<Pile> compatiblePiles = state.getTableau().getAllCompatiblePiles(topCard, null);
        for (Pile pile : compatiblePiles) {


            //Move cloneTop onto compatible pile
            candidateMove = new SpecificMove(new WasteToTableauReveal1());
            candidateMove.setFromParent(null); //Not needed, but indicates that fromparent is irellevant
            candidateMove.setToCard(pile.getTopCard());

            //Make clones
            ISolitaireState newState = state.simulateMoveWithClone(state,candidateMove);

            //See if Reveal nextMove possible
            //TableauToTabeleauReveal
            TableauToTableauReveal tableauToTableauReveal = new TableauToTableauReveal();
            candidateRevealMove = tableauToTableauReveal.getMove(newState);
            if (candidateRevealMove != null) {
                candidateMove.setToCard(pile.getTopCard());
                return candidateMove;
            }

        }
        return null;
    }

    @Override
    public String toString() {
        return "WasteToTableauReveal1";
    }
}
