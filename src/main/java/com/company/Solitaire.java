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
            //TODO
        } else if (moveType instanceof FoundationToTableau) {
            //TODO
        }

        // evaluates if the game is won or lost
        evaluateGameWon();
        evaluateGameLost();


        //return new state
        return state;
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

    private void evaluateGameWon() {
        //TODO 4 kings in foundation
    }

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
