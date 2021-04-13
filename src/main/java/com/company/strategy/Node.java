package com.company.strategy;

import com.company.Solitaire;
import com.company.model.SpecificMove;
import com.company.model.move.movestypes.TableauToTableau;
import com.company.model.state.ISolitaireState;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private ISolitaireState state;
    private SpecificMove move;
    private List<Node> children;
    private int myPoints;
    private int branchPointsMax;
    private boolean isReveal = false;
    private boolean isWon = false;

    public Node(ISolitaireState state, SpecificMove move) {
        children = new ArrayList<>();
        this.state = state;
        this.move = move;
    }

    public void addChildren(List<SpecificMove> possibleMoves) {
        for (SpecificMove move : possibleMoves) {
            if (TableauToTableau.isUselessKingMove(move,state)){
                //IF move is useless - don't evaluate it
                continue;
            }


            ISolitaireState newState = state.simulateMoveWithClone(state, move);
            Node child = new Node(newState, move);
            child.setMyPoints(move.getPoints(state));
            children.add(child);
            TreeSearcher.incrementCounter();//Adds 1 to the number of nodes searched

            if (Solitaire.isStateWon(newState)){
                child.setWon(true);
                child.increaseMyPoints(PointsTable.WIN_BONUS);
            }
            if (SpecificMove.isReveal(state,newState)) {
                child.setReveal(true);
                child.increaseMyPoints(PointsTable.REVEAL_BONUS);
            }
            if (state.isStockKnown() ){


            }

            //TODO IF STOCK-REVEAL MOVE - add a bonus
        }
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

    public void increaseMyPoints(int points){
        this.myPoints+=points;
    }

    public int getBranchPointsMax() {
        return branchPointsMax;
    }

    public void setBranchPointsMax(int branchPointsMax) {
        this.branchPointsMax = branchPointsMax;
    }

    public boolean isReveal() {
        return isReveal;
    }

    public void setReveal(boolean reveal) {
        isReveal = reveal;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }
}
