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


    public int getPoints(ISolitaireState state) {
        int points = -1;

        if (isReveal(state)) {
            points += 10000;
        }

        if (moveType instanceof StockMove) {
            return points - 10;
        } else if (moveType instanceof WasteToTableau) {
            return points + 10;
        } else if (moveType instanceof WasteToFoundation) {
            return points + 150;
        } else if (moveType instanceof TableauToFoundation) {
            return points + 200;
        } else if (moveType instanceof TableauToTableau) {
            return points - 20;
        } else if (moveType instanceof FoundationToTableau) {
            return points - 250;
        }

        return 0;
    }

    public boolean isReveal(ISolitaireState oldState) {
        ISolitaireState newState = oldState.simulateMoveWithClone(oldState, this);
        if (newState.getNumberOfFaceDownCards() == oldState.getNumberOfFaceDownCards() - 1) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Move type: " + moveType + "\nMove executed: " + fromParent + " -> " + toCard;
    }
}


