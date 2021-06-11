package com.company.utils;

import com.company.models.SpecificMove;
import com.company.models.states.ISolitaireState;
import com.company.models.states.OpenSolitaireState;
import com.company.strategy.TreeSearcher;

public class TreeSearchTester {
    public static void main(String[] args) {
        ISolitaireState newState = OpenSolitaireState.newGame(true, 2);
        TreeSearcher treeSearcher = new TreeSearcher(newState, 4);
        treeSearcher.buildTree(treeSearcher.getRoot(), 4,null,true);
        SpecificMove bestMove = treeSearcher.evaluateTree(treeSearcher.getRoot(), 3);
        System.out.println(bestMove);
    }
}
