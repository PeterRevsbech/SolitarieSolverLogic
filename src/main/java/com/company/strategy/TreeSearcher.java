package com.company.strategy;

import com.company.models.Card;
import com.company.models.SpecificMove;
import com.company.models.moves.movestypes.StockMove;
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

    public void buildTree(Node root, int depth, Card wasteStopCard, boolean firstStockMove) {
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
            //Check if node is stockmove and if special stock-simulation is relevant
            if (node.getMove().getMoveType() instanceof StockMove){
                //If so: Check if WasteStopCard is on top ==> Don't build tree
                //TODO Could check top of stock insted of top of waste here to save one simulated move
                if (!firstStockMove && node.getState().getWasteTop().equals(wasteStopCard)){
                    continue;
                }

                if (!firstStockMove){
                    //it was a stock move, but not the first
                    firstStockMove = false;
                    depth++; //To balance out the coming decrease of depth
                }

            } else {
                //If it is not a stock move ==> update wasteStopCard
                wasteStopCard = node.getState().getWasteTop();
                //If it is null AND we know what is in stockwaste
                if (wasteStopCard == null && node.getState().isStockKnown()){
                    int lastStockWasteIndex = node.getState().getKnownStockWaste().size()-1;
                    wasteStopCard = node.getState().getKnownStockWaste().get(lastStockWasteIndex);
                }
                firstStockMove = true;
            }

            buildTree(node, depth-1,wasteStopCard,firstStockMove);
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
