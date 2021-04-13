package com.company.model.move;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.exceptions.CardNotFoundException;
import com.company.model.exceptions.InvalidMoveException;
import com.company.model.exceptions.SolitarieException;
import com.company.model.move.movestypes.*;
import com.company.model.state.ISolitaireState;

import java.util.List;

public class MoveExecuter {
    public static void executeMove(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //change state according to move
        MoveType moveType = move.getMoveType();

        if (moveType instanceof StockMove) {
            stockMove(state);
        } else if (moveType instanceof WasteToTableau) {
            wasteToTableu(state, move);
        } else if (moveType instanceof WasteToFoundation) {
            wasteToFoundation(state);
        } else if (moveType instanceof TableauToFoundation) {
            tableauToFoundation(state, move);
        } else if (moveType instanceof TableauToTableau) {
            tableauToTableau(state, move);
        } else if (moveType instanceof FoundationToTableau) {
            foundationToTableau(state, move);
        } else {
            throw new SolitarieException("Unknown move type");
        }
    }

    public static void foundationToTableau(ISolitaireState state, SpecificMove move) throws SolitarieException {
        Card parentCard = move.getFromParent();
        Card toChild = move.getToCard();
        // find the corresponding pile in foundation
        Pile foundationPile = state.getFoundation().getPileContainingCard(parentCard);
        if (foundationPile == null) {
            throw new InvalidMoveException("tried to move a card from the foundationpiles, but the corresponding pile wasn't found, maybe they are all empty");
        }

        // if toChild is null and fromParent is a king then we want to move to an empty tableaupile
        if (toChild == null && parentCard.getValue() == Card.KING) {
            Pile emptyPile = state.getTableau().getFirstEmptyPile();
            emptyPile.addCard(parentCard);
            foundationPile.removeTopCard();
        } else {
            // toChild is not null: we want to add the card to an existing pile
            Pile tableauPile = state.getTableau().getPileContainingCard(toChild);
            if (tableauPile != null) {
                foundationPile.removeTopCard(); // only remove the card from the foundation if it has somewhere to go in the tableau
                tableauPile.addCard(parentCard);

            } else {
                throw new InvalidMoveException("couldnt find the card" + toChild + "in the tableau");
            }
        }


    }

    public static void tableauToTableau(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //Find fromPile in tableau
        Pile fromPile = state.getTableau().getPileContainingCard(move.getFromParent());
        Pile toPile;

        if (move.getToCard() == null) {//If moving til empty pile
            //If move.toChild is null, it means move to an empty pile

            //Assure that card being moved is a king
            if (move.getFromParent().getValue() != 13) {
                throw new SolitarieException(String.format("Trying to move %s to empty pile", move.getFromParent().toString()));
            }

            toPile = state.getTableau().getFirstEmptyPile();
            if (toPile == null) {
                throw new SolitarieException(String.format("Trying to move %s to empty pile, but there is no empty pile", move.getFromParent().toString()));
            }

        } else {
            //Find toPile in tableau
            toPile = state.getTableau().getPileContainingCard(move.getToCard());

            //Assure that from and toPile are not the same
            if (toPile == fromPile) {
                throw new SolitarieException(String.format("Trying to move %s to same pile as it was is", move.getFromParent().toString()));
            }

            //Assure that toChild is actually top if its pile
            if (!toPile.getTopCard().equals(move.getToCard())) {
                throw new SolitarieException(String.format("Trying to move %s onto %s, but toCard was not top of it card as it was is", move.getFromParent().toString(), move.getToCard()));
            }
        }

        //Move cards
        List<Card> movedCards = fromPile.getChildren(move.getFromParent());
        fromPile.removeCards(movedCards);

        //Set next card faceUp in fromPile
        if (!fromPile.isEmpty()) {
            fromPile.getTopCard().setFaceUp(true);
        }

        toPile.addCards(movedCards);
    }

    //Does not use toChild
    public static void tableauToFoundation(ISolitaireState state, SpecificMove move) throws SolitarieException {
        Card card = move.getFromParent();

        //Draw card from tableau
        Pile tableuPile = state.getTableau().getPileContainingCard(card);

        //Draw the card and check if it is on top of pile
        if (!tableuPile.removeTopCard().equals(card)) {
            throw new SolitarieException(String.format("Tried to draw %s from tableu to foundation, but it was not the top card.", card.toString()));
        }

        //Set next card to faceUp
        if (!tableuPile.isEmpty()) {
            tableuPile.getTopCard().setFaceUp(true);
        }

        //Find correct pile in foundation
        Pile foundationPile = state.getFoundation().getFoundationPileFromCard(card);

        //put in pile
        foundationPile.addCard(card);
    }

    public static void stockMove(ISolitaireState state) throws CardNotFoundException {
        Card topCard = null;
        if (!state.getKnownStockWaste().contains(state.getWasteTop())){ // if known stock does not contain top card

        }
        if (state.getStockPile().getTopCard() == null) { // if stock is empty
            //Take all cards from waste in reverse order
            List<Card> newStockPile = state.getWastePile().takeTurnedPile();
            //Put them in stock
            state.getStockPile().setCards(newStockPile);
        } else {
            //If stock was not empty
            topCard = state.getStockPile().removeTopCard();
            topCard.setFaceUp(true);
            state.getWastePile().addCard(topCard);
            if (!state.getKnownStockWaste().contains(topCard)) {
                //If topcard has not been seen yet
                state.getKnownStockWaste().add(topCard);
                state.setRevealedStockWaste(state.getRevealedStockWaste()+1);
            }

        }


    }

    //Makes no use of fromparent
    public static void wasteToTableu(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //Draw from waste
        Card card = state.getWastePile().draw();
        Pile tableuPile;
        if (move.getToCard() != null) { // If we are not moving a king to an empty pile
            //Find correct tableu-pile
            tableuPile = state.getTableau().getPileContainingCard(move.getToCard());

            //Check that card is top in tableuPile
            if (!tableuPile.getTopCard().equals(move.getToCard())) {
                throw new SolitarieException("Tried to move to card, that was not top of pile.");
            }
        } else {
            //IF we are moving a king to next empty pile
            if (move.getFromParent() != null && move.getFromParent().getValue() != Card.KING) {
                throw new SolitarieException("Tried to move to card that is not king from waste to empty pile in tableau.");
            }

            tableuPile = state.getTableau().getFirstEmptyPile();
        }

        //Add to pile
        tableuPile.addCard(card);

        //Remove the card from the StockWaste list
        state.getKnownStockWaste().remove(card);
    }

    //NOTE: doesnt actually use fromParent or toChild
    public static void wasteToFoundation(ISolitaireState state) throws SolitarieException {
        //Draw card from waste
        Card card = state.getWastePile().draw();

        Pile pile = state.getFoundation().getFoundationPileFromCard(card);

        //Put in pile
        pile.addCard(card);

        //Remove the card from the StockWaste list
        state.getKnownStockWaste().remove(card);
    }
}
