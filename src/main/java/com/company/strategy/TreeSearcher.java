
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.strategy;

import com.company.models.SpecificMove;
import com.company.models.states.ISolitaireState;

import java.util.List;

public class TreeSearcher {
    private Node root;
    private static int counter;
    private static int startDepth;


    public TreeSearcher(ISolitaireState rootState, int startDepth) {
        root = new Node(rootState, null);
        root.setMyPoints(0);
        counter = 0;
        this.startDepth = startDepth;
    }

    public void buildTree(Node root, int depth) {
        if (depth == 0) {
            return;
        } else if (root.isReveal()) {
            //Stop the seach here, since we have found a reveal
            return;
        } else if (root.isWon()) {
            return; //Stop the search, if game is won here
        }

        //Make all child nodes
        List<SpecificMove> movesFromRoot = root.getState().getAllPossibleMoves();

        //If no moves were possible - we're at a dead end, and should return
        if (movesFromRoot.isEmpty()) {
            //Set points to minimum value
            root.setMyPoints(Integer.MIN_VALUE);
            return;
        }

        //Add the moves
        root.addChildren(movesFromRoot);

        //For each child node, call buildTree(childNode, depth-1)
        for (Node node : root.getChildren()) {
            buildTree(node, depth - 1);
        }
    }

    public SpecificMove evaluateTree(Node root, int depth) {
        evaluateBranch(root, depth);
        SpecificMove bestMove = null;
        int maxPoints = Integer.MIN_VALUE;
        for (Node child : root.getChildren()) {
            if (child.getBranchPointsMax() > maxPoints) {
                maxPoints = child.getBranchPointsMax();
                bestMove = child.getMove();
            }
        }

        return bestMove;
    }

    public int evaluateBranch(Node node, int depth) {
        if (depth == 0) {
            node.setBranchPointsMax(node.getMyPoints());
        } else if (!node.isReveal() && node.getChildren().isEmpty()) {
            //Dead end - no possible moves from this state, and it's not because we reached end of search
            node.setBranchPointsMax(Integer.MIN_VALUE);
        } else if (node.isReveal() && node.getChildren().isEmpty()) {
            //If it is reveal - we will stop looking further down this branch
            node.setBranchPointsMax(node.getMyPoints());
        } else {
            //Node has children, and should find its branchMax by looking at max child
            int myPoints = node.getMyPoints();
            node.setBranchPointsMax(Integer.MIN_VALUE);
            for (Node child : node.getChildren()) {
                int childMax = evaluateBranch(child, depth - 1);
                node.setBranchPointsMax(Math.max(node.getBranchPointsMax(), myPoints + childMax));
            }
        }
        return node.getBranchPointsMax();
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public static void incrementCounter() {
        counter++;
    }

    public static int getCounter() {
        return counter;
    }

    public static int getStartDepth() {
        return startDepth;
    }
}
