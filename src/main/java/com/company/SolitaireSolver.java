package com.company;

import com.company.model.SpecificMove;
import com.company.model.move.MoveType;
import com.company.model.state.ISolitaireState;
import com.company.strategy.Strategy;

public class SolitaireSolver {

    private Strategy strategy = new Strategy();

    // determine best move
    public SpecificMove bestPossibleMove(Solitaire game) {
        ISolitaireState state = game.getLastState();

        //find move
        SpecificMove move = null;
        for (MoveType moveType : strategy.getPrioritizedMoveTypes()) {
            move = moveType.getMove(state);
            if (move != null) {
                break;
            }
        }

        //Return move
        return move;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
}
