
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.utils;

import com.company.models.SpecificMove;
import com.company.models.states.ISolitaireState;
import com.company.models.states.OpenSolitaireState;
import com.company.strategy.TreeSearcher;

public class TreeSearchTester {
    public static void main(String[] args) {
        ISolitaireState newState = OpenSolitaireState.newGame(true, 2);
        TreeSearcher treeSearcher = new TreeSearcher(newState, 4);
        treeSearcher.buildTree(treeSearcher.getRoot(), 4);
        SpecificMove bestMove = treeSearcher.evaluateTree(treeSearcher.getRoot(), 3);
        System.out.println(bestMove);
    }
}
