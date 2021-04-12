package com.company;

import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;
import com.company.model.state.OpenSolitaireState;
import com.company.strategy.TreeSearcher;

public class TreeSearchTester {
    public static void main(String[] args) {
        ISolitaireState newState = OpenSolitaireState.newGame(true,2);
        TreeSearcher treeSearcher = new TreeSearcher(newState,4);
        treeSearcher.buildTree(treeSearcher.getRoot(),4);
        SpecificMove bestMove = treeSearcher.evaluateTree(treeSearcher.getRoot(),3);
        System.out.println(bestMove);
    }
}
