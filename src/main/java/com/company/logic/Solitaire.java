package com.company.logic;

import com.company.models.Card;
import com.company.models.SpecificMove;
import com.company.models.exceptions.SolitarieException;
import com.company.models.moves.*;
import com.company.models.moves.movestypes.StockMove;
import com.company.models.states.ClosedSolitaireState;
import com.company.models.states.ISolitaireState;
import com.company.models.states.OpenSolitaireState;

import java.util.ArrayList;
import java.util.List;

public class Solitaire {

    public static Card unkownCard; //Points to null: all cards are known ;    Points to a card with value null and suit null: the unknown card
    public static boolean simulating = false;
    public static final String NO_CARD = "NONE";
    private SolitaireSolver solver;
    private List<ISolitaireState> states;
    private boolean gameWon;
    private boolean gameLost;
    private boolean printing;
    int turnsPlayed = 0;
    private int stockMoveCounter=0;
    private final static int MAX_NUM_OF_MOVES = 250;
    SpecificMove nextMove;


    public void initGame(boolean isShuffled, boolean printing, int dataSeed, long timeLimitMillis, int fixedDepth) {
        solver = new SolitaireSolver(fixedDepth, timeLimitMillis);
        states = new ArrayList<>();
        states.add(OpenSolitaireState.newGame(isShuffled, dataSeed));
        gameWon = false;
        gameLost = false;
        this.printing = printing;
    }

    public boolean isAllCardsFaceUp(ISolitaireState state) {
        boolean isAllFaceup = true;

        //int tabLen = state.getTableau().getMaxTableauLength();
        for (int i = 0; i < 7; i++) { // pile
            for (int j = 0; j < 7; j++) { // cards
                int pileLen = state.getTableau().getPiles()[i].getCards().size();
                if (j >= pileLen) { //If there is no card at this place
                    break;
                }
                if (!state.getTableau().getPiles()[i].getCard(j).isFaceUp()) { //if the card is NOT face up
                    isAllFaceup = false;
                }
            }
        }


        return isAllFaceup;
    }

    public void updateClosedGame(String input) throws SolitarieException {
        //Input is either a card (as string)  or NO_CARD ("NONE")

        if (input.equals(NO_CARD)) {
            //Game is already up to date

            //Assert that unknownCard is null
            if (unkownCard != null) {//also unkown card must be true
                throw new SolitarieException("Got NONE from GUI, but expected to find a card.");
            }

        } else {//if input is not "NONE"
            if (unkownCard == null) {//also unkown card must be true
                throw new SolitarieException("Got " + input + " from GUI, but expected NONE.");
            }

            //Set value of unknown card from input
            unkownCard.setSuit(Card.suitFromString(input));
            unkownCard.setValue(Card.valueFromString(input));

            //Indicate, that game no longer contains an unknown card.
            unkownCard = null;
        }
    }

    /*
    public SpecificMove findNextMoveClosedGame(){
        //Assumes that game state is valid (no unknown card, that should not be unknown)

        //Find next move
        // get latest state, call solver to find next move
        ISolitaireState currentState = states.get(states.size() - 1);

        nextMove = solver.bestPossibleMove(this);
        if (nextMove == null) {
            gameLost = true;
        }

        // call the method makeMove, add the state to list of states
        try {
            ISolitaireState nextState = playTurn(currentState, nextMove);
        } catch (SolitarieException e) {
            e.printStackTrace();
        }






        //If move is reveal move ==> set variable that indicates, we need to known which card it is
        //unkownCard = true;

      return null;
    }

     */

    public boolean isUnkownCard() {
        return unkownCard != null;
    }


    public void initClosedGame(ISolitaireState startState, int fixedDepth, long timeLimitMillis) {
        solver = new SolitaireSolver(fixedDepth, timeLimitMillis);
        states = new ArrayList<>();
        states.add(startState);
        gameWon = false;
        gameLost = false;
    }

    public boolean playGame() {
        while (!(gameLost || gameWon)) {
            makeNextMove();
        }
        return gameWon;
    }

    public SpecificMove getNextMove() {
        return nextMove;
    }

    public void addNextClosedState(ClosedSolitaireState newState) {
        //Set knownStockWaste to be copy of previous states knownStockWaste
        ISolitaireState previousState = states.get(states.size() - 1);

        //Make local clone of card list
        List<Card> clone = new ArrayList<>();
        for (Card card : previousState.getKnownStockWaste()) {
            clone.add(new Card(card.getSuit(), card.getValue(), card.isFaceUp()));
        }

        //Set newStates knownStockWaste to the clone and update it
        newState.setKnownStockWaste(clone);


        //Add the new state to list of states
        states.add(newState);

    }

    public ISolitaireState playTurn(ISolitaireState state, SpecificMove move) throws SolitarieException {
        //fromParent is irellevant, since we draw from waste
        //If top of wastepile is an ace - toChild should be null

        // make a copy of the current state and use that
        state = state.clone();

        //Execute move
        MoveExecuter.executeMove(state, move);

        evaluateGameLost(move,state);

        evaluateGameWon(state);

        //Update turnsPlayed
        turnsPlayed++;

        //return new state
        return state;
    }


    private void evaluateGameLost(SpecificMove move,ISolitaireState state) {

        if(move.getMoveType() instanceof StockMove){
            stockMoveCounter=stockMoveCounter+1;
        }else{
            stockMoveCounter=0;
        }
        boolean isStockKnown=state.isStockKnown();
        if(stockMoveCounter>=state.getKnownStockWaste().size() && isStockKnown){
            gameLost=true;
        }
        /*
        //If no NEW cards have been added to foundation...
        if (turnsPlayed > MAX_NUM_OF_MOVES) {
            gameLost = true;
        }
       */
    }

    //Method that checks if the 4 tops cards in the foundation piles are kings, if so, the game is won
    public boolean evaluateGameWon(ISolitaireState state) {
        gameWon = isStateWon(state);
        return gameWon;
    }

    public static boolean isStateWon(ISolitaireState state) {
        return state.getTableau().getMaxTableauLength() == 0 && state.getStockPile().isEmpty() && state.getWastePile().isEmpty();
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
        nextMove = solver.bestPossibleMove(this);
        if (nextMove == null) {
            gameLost = true;
        }
        //Simulations may have set unknownCard ==> Reset it
        unkownCard = null;

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

    public ISolitaireState getLastState() {
        return states.get(states.size() - 1);
    }

    public void setPrinting(boolean printing) {
        this.printing = printing;
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


}
