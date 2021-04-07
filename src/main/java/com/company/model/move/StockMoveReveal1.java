package com.company.model.move;

import com.company.model.Card;
import com.company.model.SpecificMove;
import com.company.model.exceptions.SolitarieException;
import com.company.model.state.ISolitaireState;

import java.util.List;

public class StockMoveReveal1 extends StockMove {

    // This is when a number of stockmoves can be made, that make WasteToTableauReveal1 possible
    // ==> find one card in stock, that if it were on top, would make WasteToTableauReveal1 possible ==> make stock move

    public SpecificMove getMove(ISolitaireState state) {
        SpecificMove move = new SpecificMove(new StockMoveReveal1());

        if (!state.getKnownStockWaste().isEmpty()) { //If wasteStock is not empty
            WasteToTableauReveal1 wasteToTableauReveal1 = new WasteToTableauReveal1();

            //Get list of known stock waste cards
            List<Card> knownStockWaste = state.getKnownStockWaste();
            for (Card card : knownStockWaste) {
                //Check if it were top of waste - could we make wasteToTableau

                //Place card on top of waste
                ISolitaireState cloneState = state.clone();

                //Find the corresponding card in cloned cardDeck
                for (int i = 0; i < knownStockWaste.size(); i++) {
                    if (card.equals(knownStockWaste.get(i))) {
                        card = knownStockWaste.get(i);
                    }
                }

                try {
                    cloneState.swapStockTopCard(card);
                } catch (SolitarieException e) {
                    e.printStackTrace();
                    return null;
                }


                //See if WasteToTableauReveal1 is possible
                if (wasteToTableauReveal1.getMove(cloneState) != null) {
                    return move;
                }
            }
        }
        return null;

    }

    @Override
    public String toString() {
        return "StockMoveReveal1";
    }
}
