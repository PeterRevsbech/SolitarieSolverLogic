package com.company.strategy;

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
        List<Node> nodes;



        //For each child node, call buildTree(childNode, depth-1)


    }



}
