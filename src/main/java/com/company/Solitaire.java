package com.company;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
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


    public void initGame(){
        states = new ArrayList<>();
        states.add(OpenSolitaireState.newGame());
        gameWon = false;
        gameLost = false;
        stockIsKnown = false;
    }

    public ISolitaireState makeMove(ISolitaireState state, SpecificMove move) throws SolitarieException {
        // make a copy of the current state and use that
        state = state.clone();

        //change state according to move
        MoveType moveType = move.getMoveType();
        if (moveType instanceof StockMove){
            Card topCard;
            if (state.getStockPile().getTopCard()==null){ // if stock is empty
                //Take all cards from waste in reverse order
                List<Card> newStockPile = state.getWastePile().takeTurnedPile();
                //Put them in stock
                state.getStockPile().setCards(newStockPile);
            } else {
                topCard = state.getStockPile().removeTopCard();
                topCard.setFaceUp(true);
                state.getWastePile().addCard(topCard);
            }
        } else if (moveType instanceof WasteToTableau) {
            //Draw from waste
            Card card = state.getWastePile().draw();

            //Find correct tableu-pile
            Pile tableuPile = state.getTableau().getPileContainingCard(move.getToChild());

            //Check that card is top in tableuPile
            if (!tableuPile.getTopCard().equals(move.getToChild())){
                throw new SolitarieException("Tried to move to card, that was not top of pile.");
            }

            //Add to pile
            tableuPile.addCard(card);

        } else if (moveType instanceof WasteToFoundation) {
            //TODO
        } else if (moveType instanceof TableauToFoundation) {
            //TODO
        } else if (moveType instanceof TableauToTableau) { //MADS
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

    private void evaluateGameLost() {
        //TODO Check if no progress in a long amount of time
        //If no NEW cards have been added to foundation...
    }

    private void evaluateGameWon() {
        //TODO 4 kings in foundation
    }

    public boolean makeNextMove(){
        // get latest state, call solver to find next move
        ISolitaireState currentState = states.get(states.size()-1);
        SpecificMove nextMove = solver.bestPossibleMove(this);

        // call the method makeMove, add the state to list of states
        try {
            ISolitaireState nextState = makeMove(currentState,nextMove);
            states.add(nextState);
        } catch (SolitarieException e){
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
