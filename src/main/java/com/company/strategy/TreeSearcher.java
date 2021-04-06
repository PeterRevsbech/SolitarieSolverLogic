package com.company.strategy;

import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

import java.util.List;

public class TreeSearcher {
    private Node root;


    public TreeSearcher(ISolitaireState rootState){
        root = new Node(rootState,null);
        root.setMyPoints(0);
    }

    public void buildTree(Node root, int depth){
        if (depth==0){
            return;
        }

        //Make all child nodes
        List<SpecificMove> movesFromRoot  = root.getState().getAllPossibleMoves();

        //If no moves were possible - we're at a dead end, and should return
        if (movesFromRoot.isEmpty()){
            //Set points to minimum value
            root.setMyPoints(Integer.MIN_VALUE);
            return;
        }

        //Add the moves
        root.addChildren(movesFromRoot);

        //For each child node, call buildTree(childNode, depth-1)
        for (Node node: root.getChildren()) {
            buildTree(node,depth-1);
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
