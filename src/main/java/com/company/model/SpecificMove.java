package com.company.model;

import com.company.model.exceptions.SolitarieException;
import com.company.model.move.*;
import com.company.model.state.ISolitaireState;

public class SpecificMove {

    private MoveType moveType;
    private Card fromParent;
    private Card toCard;

    public SpecificMove() {
    }

    public SpecificMove(MoveType moveType) {
        this.moveType = moveType;
    }

    public SpecificMove(MoveType moveType, Card fromParent, Card toCard) {
        this.moveType = moveType;
        this.fromParent = fromParent;
        this.toCard = toCard;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public Card getFromParent() {
        return fromParent;
    }

    public void setFromParent(Card fromParent) {
        this.fromParent = fromParent;
    }

    public Card getToCard() {
        return toCard;
    }

    public void setToCard(Card toCard) {
        this.toCard = toCard;
    }


    public int getPoints(ISolitaireState state){
        int points = 0;

        if (isReveal(state)){
            points+=1000;
        }

        if (moveType instanceof StockMove) {
            return points+0;
        } else if (moveType instanceof WasteToTableau) {
            return points-5;
        } else if (moveType instanceof WasteToFoundation) {
            return points+15;
        } else if (moveType instanceof TableauToFoundation) {
            return points+20;
        } else if (moveType instanceof TableauToTableau) {
            return points+0;
        } else if (moveType instanceof FoundationToTableau) {
            return points-25;
        }

        return 0;
    }

    public boolean isReveal(ISolitaireState oldState){
        ISolitaireState newState = oldState.simulateMoveWithClone(oldState,this);
        if (newState.getNumberOfFaceDownCards() == oldState.getNumberOfFaceDownCards()-1){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Move type: " + moveType + "\nMove executed: " + fromParent + " -> " + toCard + "\nNew state: ";
    }
}


