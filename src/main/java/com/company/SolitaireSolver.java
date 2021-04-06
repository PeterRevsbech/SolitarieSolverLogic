package com.company;

import com.company.model.SpecificMove;
import com.company.model.move.MoveType;
import com.company.model.state.ISolitaireState;
import com.company.strategy.Strategy;
import com.company.strategy.TreeSearcher;

public class SolitaireSolver {

    private Strategy strategy = new Strategy();

    // determine best move
    public SpecificMove bestPossibleMove(Solitaire game) {
        ISolitaireState state = game.getLastState();

        //Try tree search first at see if something better than 900 points is found
        TreeSearcher treeSearcher = new TreeSearcher(state);
        treeSearcher.buildTree(treeSearcher.getRoot(),3);
        SpecificMove treeSearchMove = treeSearcher.evaluateTree(treeSearcher.getRoot(),3);
        if (treeSearcher.getRoot().getBranchPointsMax() > 0){
            return treeSearchMove;
        }

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
