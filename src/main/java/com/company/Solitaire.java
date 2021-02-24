package com.company;

import com.company.model.SpecificMove;
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

    public ISolitaireState makeMove(ISolitaireState state, SpecificMove move){
        // make a copy of the current state

        //change state according to move

        // evaluates if the game is won or lost

        //return new state
        return null;
    }

    public boolean makeNextMove(){
        // get latest state, call solver to find next move
        ISolitaireState currentState = states.get(states.size()-1);
        SpecificMove nextMove = solver.bestPossibleMove(this);

        // call the method makeMove, add the state to list of states
        ISolitaireState nextState = makeMove(currentState,nextMove);
        states.add(nextState);

        // returns true if the game is over in any way
        return gameWon || gameLost;
    }


}
