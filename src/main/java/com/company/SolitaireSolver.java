package com.company;

import com.company.model.SpecificMove;
import com.company.model.move.MoveType;
import com.company.model.state.ISolitaireState;
import com.company.strategy.Strategy;
import com.company.strategy.TreeSearcher;

public class SolitaireSolver {

    private final int fixedDepth;
    private final long timeLimitMillis;

    private Strategy strategy = new Strategy();

    public SolitaireSolver(int fixedDepth, long timeLimitMillis) {
        this.fixedDepth = fixedDepth;
        this.timeLimitMillis = timeLimitMillis;
    }

    // determine best move
    public SpecificMove bestPossibleMove(Solitaire game) {
        ISolitaireState state = game.getLastState();

        SpecificMove move = null;

        if (game.isAllCardsFaceUp(state)) {
            //find move
            for (MoveType moveType : strategy.getPrioritizedMoveTypes()) {
                move = moveType.getMove(state);
                if (move != null) {
                    return move;
                }
            }
        }

        long t0 = System.currentTimeMillis();
        int depth = 2; //Start with a depth of 2 and increase as long as there is time

        while (System.currentTimeMillis() - t0 < timeLimitMillis){ //While the time limit has not yet been exceeded - start a new round
            if (fixedDepth!=-1){ //If fixed depth is set - use it
                depth=fixedDepth;
            }
            TreeSearcher treeSearcher = new TreeSearcher(state,depth);
            treeSearcher.buildTree(treeSearcher.getRoot(), depth);
            move = treeSearcher.evaluateTree(treeSearcher.getRoot(), depth);
            depth++;
            if (fixedDepth!=-1){ //If fixed depth is set - do no more iterations
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
