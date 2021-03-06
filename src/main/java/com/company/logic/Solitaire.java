
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.logic;

import com.company.models.Card;
import com.company.models.SpecificMove;
import com.company.models.exceptions.SolitarieException;
import com.company.models.moves.*;
import com.company.models.moves.movestypes.StockMove;
import com.company.models.states.ClosedSolitaireState;
import com.company.models.states.ISolitaireState;
import com.company.models.states.OpenSolitaireState;
import com.company.utils.PrintGameState;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private int stockMoveCounter = 0;
    private final static int MAX_NUM_OF_MOVES = 250;
    SpecificMove nextMove;
    private int gameCounter;

    public void setGameCounter(int gameCounter) {
        this.gameCounter = gameCounter;
    }


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

    public String randString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }

        return buffer.toString();
    }

    String s = "";

    public void writeToFile() throws FileNotFoundException, UnsupportedEncodingException {
        //File myObj = new File("/Users/madsstorgaard-nielsen/Desktop/test");
        String randString = randString();

        PrintWriter writer = new PrintWriter("/Users/madsstorgaard-nielsen/Desktop/test/gameNum" + gameCounter, "UTF-8");

        writer.println(s);

        writer.close();
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

        evaluateGameLost(move, state);

        evaluateGameWon(state);

        //Update turnsPlayed
        turnsPlayed++;

        //return new state
        return state;
    }

    boolean isStockKnown;

    private void evaluateGameLost(SpecificMove move, ISolitaireState state) {

        if (move.getMoveType() instanceof StockMove) {
            stockMoveCounter++;
        } else {
            stockMoveCounter = 0;
        }
        //System.out.println(stockMoveCounter);

        if (state.isStockEmpty()) {
            isStockKnown = true;
        }

        if ((stockMoveCounter > state.getKnownStockWaste().size()+15) && isStockKnown) {
            gameLost = true;
/*            try {
                writeToFile();
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }*/
            s = "";
        } else if (turnsPlayed > MAX_NUM_OF_MOVES) {
            gameLost = true;
        }
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

    PrintGameState pgs = new PrintGameState();


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

        s += "\n" + "Tur: " + turnsPlayed + " " + nextMove.detailedToString(currentState);
        //System.out.println(nextMove.detailedToString(currentState));
        //pgs.initClosedSolitareState(currentState);
        //pgs.printCurrentState();
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
