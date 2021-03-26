package com.company;

import com.company.model.Card;
import com.company.model.Pile;
import com.company.model.SpecificMove;
import com.company.model.exceptions.CardNotFoundException;
import com.company.model.exceptions.InvalidMoveException;
import com.company.model.exceptions.SolitarieException;
import com.company.model.move.*;
import com.company.model.state.ClosedSolitaireState;
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
    private boolean printing;
    int turnsPlayed =0;
    private final static int MAX_NUM_OF_MOVES = 250;


    public void initGame(boolean isShuffled, boolean printing) {
        states = new ArrayList<>();
        states.add(OpenSolitaireState.newGame(isShuffled));
        gameWon = false;
        gameLost = false;
        stockIsKnown = false;
        this.printing=printing;
    }

    public void initClosedGame(ClosedSolitaireState startState){
        states = new ArrayList<>();
        states.add(startState);
        gameWon = false;
        gameLost = false;
        stockIsKnown = false;
    }

    public boolean playGame(){
        while (!(gameLost||gameWon)){
            makeNextMove();
        }
        return gameWon;
    }

    public String getNextMove(){
        return solver.bestPossibleMove(this).toString();
    }

    public void addNextClosedState(ClosedSolitaireState newState){
        //Set knownStockWaste to be copy of previous states knownStockWaste
        ISolitaireState previousState = states.get(states.size()-1);

        //Make local clone of card list
        List<Card> clone = new ArrayList<>();
        for (Card card:previousState.getKnownStockWaste()) {
            clone.add(new Card(card.getSuit(),card.getValue(),card.isFaceUp()));
        }

        //Set newStates knownStockWaste to the clone and update it
        newState.setKnownStockWaste(clone);
        updateKnownStockWaste(newState);

        //Add the new state to list of states
        states.add(newState);

    }

    public ISolitaireState playTurn(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //fromParent is irellevant, since we draw from waste
        //If top of wastepile is an ace - toChild should be null

        // make a copy of the current state and use that
        state = state.clone();

        //Execute move
        executeMove(state,move);

        //Update knownStockWaste
        updateKnownStockWaste(state);

        // evaluates if the game is won or lost
        evaluateGameWon(state);
        evaluateGameLost();

        //Update turnsPlayed
        turnsPlayed++;

        //return new state
        return state;
    }

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
        } else{
            throw new SolitarieException("Unknown move type");
        }
    }

    public static void foundationToTableau(ISolitaireState state, SpecificMove move) throws SolitarieException {
        Card parentCard = move.getFromParent();
        Card toChild = move.getToCard();
        // find the corresponding pile in foundation
        Pile foundationPile = state.getFoundation().getFoundationPileFromCard(parentCard);
        if (foundationPile == null) {
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
        if (!fromPile.isEmpty()){
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
            throw new SolitarieException(String.format("Thried to draw %s from tableu to foundation, but it was not the top card.", card.toString()));
        }

        //Set next card to faceUp
        if (!tableuPile.isEmpty()){
            tableuPile.getTopCard().setFaceUp(true);
        }

        //Find correct pile in foundation
        Pile foundationPile = state.getFoundation().getFoundationPileFromCard(card);

        //put in pile
        foundationPile.addCard(card);
    }

    public static void stockMove(ISolitaireState state) throws CardNotFoundException {
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

    //Makes no use of fromparent
    public static void wasteToTableu(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //Draw from waste
        Card card = state.getWastePile().draw();
        Pile tableuPile;
        if (move.getToCard() != null){ // If we are not moving a king to an empty pile
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
    }

    //NOTE: doesnt actually use fromParent or toChild
    public static void wasteToFoundation(ISolitaireState state) throws SolitarieException {
        //Draw card from waste
        Card card = state.getWastePile().draw();

        Pile pile = state.getFoundation().getFoundationPileFromCard(card);

        //Put in pile
        pile.addCard(card);
    }

    private void evaluateGameLost() {
        //TODO Check if no progress in a long amount of time
        //If no NEW cards have been added to foundation...
        if (turnsPlayed >MAX_NUM_OF_MOVES){
            gameLost=true;
        }
    }

    //Method that checks if the 4 tops cards in the foundation piles are kings, if so, the game is won
    private void evaluateGameWon(ISolitaireState state) {
        for (int i = 0; i < 3; i++) {
            if (!state.getFoundation().getPiles()[i].isEmpty() && state.getFoundation().getPiles()[i].getTopCard().getValue() == 13) {
                gameWon = true;
            } else if (!state.getFoundation().getPiles()[i].isEmpty() && state.getFoundation().getPiles()[i].getTopCard().getValue() != 13) {
                gameWon = false;
            }
        }
    }

    //Method that checks if every card on the tableau is faceup, if they are, the game is winable.
    private boolean isWinable(ISolitaireState state) {
        boolean isWinable = false;
        for (int i = 0; i < 6; i++) {
            for (Card c : state.getTableau().getPile(i).getCards()) {
                isWinable = c.isFaceUp();
            }
        }
        return isWinable;
    }

    public void makeNextMove() {
        // get latest state, call solver to find next move
        ISolitaireState currentState = states.get(states.size() - 1);
        SpecificMove nextMove = solver.bestPossibleMove(this);
        if (nextMove==null){
            gameLost=true;
        }

        // call the method makeMove, add the state to list of states
        try {
            ISolitaireState nextState = playTurn(currentState, nextMove);
            states.add(nextState);
        } catch (SolitarieException e) {
            e.printStackTrace();
        }
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

    public ISolitaireState getLastState(){
        return states.get(states.size()-1);
    }

    public void setPrinting(boolean printing){
        this.printing=printing;
    }

    public boolean isPrinting() {
        return printing;
    }

    public int getTurnsPlayed() {
        return turnsPlayed;
    }

    public void setTurnsPlayed(int turnsPlayed) {
        this.turnsPlayed = turnsPlayed;
    }

    public static void updateKnownStockWaste(ISolitaireState state){
        //If we added a card to the list
            //Card must be visible on top of WastePile
        Card wasteTop = state.getWasteTop();

        if (wasteTop != null && !state.getKnownStockWaste().contains(wasteTop)){
            //If topcard has not been seen yet
            state.getKnownStockWaste().add(wasteTop);
        } else {
            //If we removed a card from the list
                //Card must be visible on top of a tableau-pile or in foundation
                // ==> should be removed from knownStockWaste

            //First check tableau
            for (Pile tableauPile:state.getTableau().getPiles()) {
                Card topCard =tableauPile.getTopCard();
                if (topCard != null && state.getKnownStockWaste().contains(topCard)) {
                    //If card is not null, and it is present in knownStockWaste as well
                    state.getKnownStockWaste().remove(topCard);
                    return;

                }
            }

            //Then check foundation
            for (Pile foundaionPile:state.getFoundation().getPiles()) {
                Card topCard =foundaionPile.getTopCard();
                if (topCard != null && state.getKnownStockWaste().contains(topCard)) {
                    //If card is not null, and it is present in knownStockWaste as well
                    state.getKnownStockWaste().remove(topCard);
                    return;
                }
            }
        }


    }
}
