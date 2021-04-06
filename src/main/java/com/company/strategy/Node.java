package com.company.strategy;

import com.company.model.SpecificMove;
import com.company.model.state.ISolitaireState;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private ISolitaireState state;
    private SpecificMove move;
    List<Node> children;
    int myPoints;
    int branchPointsMax;

    public Node(ISolitaireState state, SpecificMove move) {
        children = new ArrayList<>();
        this.state = state;
        this.move = move;
    }

    public ISolitaireState getState() {
        return state;
    }

    public void setState(ISolitaireState state) {
        this.state = state;
    }

    public SpecificMove getMove() {
        return move;
    }

    public void setMove(SpecificMove move) {
        this.move = move;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public int getMyPoints() {
        return myPoints;
    }

    public void setMyPoints(int myPoints) {
        this.myPoints = myPoints;
    }

    public int getBranchPointsMax() {
        return branchPointsMax;
    }

    public void setBranchPointsMax(int branchPointsMax) {
        this.branchPointsMax = branchPointsMax;
    }
}
