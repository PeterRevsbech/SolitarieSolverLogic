package com.company;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.exceptions.CardNotFoundException;
import com.company.model.exceptions.InvalidMoveException;
import com.company.model.exceptions.SolitarieException;
import com.company.model.move.*;
import com.company.model.state.ISolitaireState;
import com.company.model.state.OpenSolitaireState;

import java.util.ArrayList;
import java.util.List;

public class Solitaire {

    private SolitaireSolver solver = new SolitaireSolver();
    private List<ISolitaireState> states;
    private boolean gameWon;
    private boolean gameLost;
    private boolean stockIsKnown;


    public void initGame() {
        states = new ArrayList<>();
        states.add(OpenSolitaireState.newGame());
        gameWon = false;
        gameLost = false;
        stockIsKnown = false;
    }

    public ISolitaireState makeMove(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //fromParent is irellevant, since we draw from waste
        //If top of wastepile is an ace - toChild should be null

        // make a copy of the current state and use that
        state = state.clone();

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
        }

        // evaluates if the game is won or lost
        evaluateGameWon(state);
        evaluateGameLost();

        //return new state
        return state;

    }

    private void foundationToTableau(ISolitaireState state, SpecificMove move) throws SolitarieException {
        Card parentCard = move.getFromParent();
        Card toChild = move.getToChild();
        // find the corresponding pile in foundation
        Pile foundationPile = state.getFoundation().getFoundationPileFromCard(parentCard);
        if(foundationPile == null){
            throw new InvalidMoveException("tried to move a card from the foundationpiles, but the corresponding pile wasn't found, maybe they are all empty");
        }

        // if toChild is null and fromParent is a king then we want to move to an empty tableaupile
        if (toChild == null && parentCard.equals(13)) {
            Pile emptyPile = state.getTableau().getFirstEmptyPile();
            emptyPile.addCard(parentCard);
            foundationPile.removeTopCard();
        }

        // toChild is not null we want to add the card to an existing pile
        Pile tableauPile = state.getTableau().getPileContainingCard(toChild);
        if (tableauPile != null) {
            foundationPile.removeTopCard(); // only remove the card from the foundation if it has somewhere to go in the tableau
            tableauPile.addCard(parentCard);

        } else {
            throw new InvalidMoveException("couldnt find the card" + toChild + "in the tableau");
        }
    }

    private void tableauToTableau(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //Find fromPile in tableau
        Pile fromPile = state.getTableau().getPileContainingCard(move.getFromParent());
        Pile toPile;

        if (move.getToChild() == null) {//If moving til empty pile
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
            toPile = state.getTableau().getPileContainingCard(move.getToChild());

            //Assure that from and toPile are not the same
            if (toPile == fromPile) {
                throw new SolitarieException(String.format("Trying to move %s to same pile as it was is", move.getFromParent().toString()));
            }

            //Assure that toChild is actually top if its pile
            if (!toPile.getTopCard().equals(move.getToChild())) {
                throw new SolitarieException(String.format("Trying to move %s onto %s, but toCard was not top of it card as it was is", move.getFromParent().toString(), move.getToChild()));
            }
        }

        //Move cards
        List<Card> movedCards = fromPile.getChildren(move.getFromParent());
        fromPile.removeCards(movedCards);
        toPile.addCards(movedCards);
    }

    private void tableauToFoundation(ISolitaireState state, SpecificMove move) throws SolitarieException {
        Card card = move.getFromParent();

        //Draw card from tableau
        Pile tableuPile = state.getTableau().getPileContainingCard(card);

        //Draw the card and check if it is on top of pile
        if (!tableuPile.removeTopCard().equals(card)) {
            throw new SolitarieException(String.format("Thried to draw %s from tableu to foundation, but it was not the top card.", card.toString()));
        }

        //Find correct pile in foundation
        Pile foundationPile = state.getFoundation().getFoundationPileFromCard(card);

        //put in pile
        foundationPile.addCard(card);
    }

    private void stockMove(ISolitaireState state) throws CardNotFoundException {
        Card topCard;
        if (state.getStockPile().getTopCard() == null) { // if stock is empty
            //Take all cards from waste in reverse order
            List<Card> newStockPile = state.getWastePile().takeTurnedPile();
            //Put them in stock
            state.getStockPile().setCards(newStockPile);
        } else {
            topCard = state.getStockPile().removeTopCard();
            topCard.setFaceUp(true);
            state.getWastePile().addCard(topCard);
        }
    }

    private void wasteToTableu(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //Draw from waste
        Card card = state.getWastePile().draw();

        //Find correct tableu-pile
        Pile tableuPile = state.getTableau().getPileContainingCard(move.getToChild());

        //Check that card is top in tableuPile
        if (!tableuPile.getTopCard().equals(move.getToChild())) {
            throw new SolitarieException("Tried to move to card, that was not top of pile.");
        }

        //Add to pile
        tableuPile.addCard(card);
    }

    private void wasteToFoundation(ISolitaireState state) throws SolitarieException {
        //Draw card from waste
        Card card = state.getWastePile().draw();

        Pile pile = state.getFoundation().getFoundationPileFromCard(card);

        //Put in pile
        pile.addCard(card);
    }

    private void evaluateGameLost() {
        //TODO Check if no progress in a long amount of time
        //If no NEW cards have been added to foundation...
    }

    private void evaluateGameWon(ISolitaireState state) {
        //TODO implemententer eventuelt en løkke der tjekker om alt på tableau er faceup, da spillet er "winable" hvis dette er tilfældet.

        for (int i = 0; i < 3; i++) {
            if (!state.getFoundation().getPiles()[i].isEmpty() && state.getFoundation().getPiles()[i].getTopCard().getValue() == 13) {
                gameWon = true;
            } else if (!state.getFoundation().getPiles()[i].isEmpty() && state.getFoundation().getPiles()[i].getTopCard().getValue() != 13) {
                gameWon = false;
            }
        }
    }

/*    private void isWinable(ISolitaireState state) {
        for (Card c : state.g) {

        }


    }*/

    public boolean makeNextMove() {
        // get latest state, call solver to find next move
        ISolitaireState currentState = states.get(states.size() - 1);
        SpecificMove nextMove = solver.bestPossibleMove(this);

        // call the method makeMove, add the state to list of states
        try {
            ISolitaireState nextState = makeMove(currentState, nextMove);
            states.add(nextState);
        } catch (SolitarieException e) {
            e.printStackTrace();
        }

        // returns true if the game is over in any way
        return gameWon || gameLost;
    }

    public SolitaireSolver getSolver() {
        return solver;
    }

    public void setSolver(SolitaireSolver solver) {
        this.solver = solver;
    }

    public List<ISolitaireState> getStates() {
        return states;
    }

    public void setStates(List<ISolitaireState> states) {
        this.states = states;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public boolean isGameLost() {
        return gameLost;
    }

    public void setGameLost(boolean gameLost) {
        this.gameLost = gameLost;
    }

    public boolean isStockIsKnown() {
        return stockIsKnown;
    }

    public void setStockIsKnown(boolean stockIsKnown) {
        this.stockIsKnown = stockIsKnown;
    }
}
