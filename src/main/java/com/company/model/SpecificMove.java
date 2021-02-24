package com.company.model;

import com.company.model.move.MoveType;

public class SpecificMove {

    private MoveType moveType;
    private Card fromParent;
    private Card toChild;

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

    public Card getToChild() {
        return toChild;
    }

    public void setToChild(Card toChild) {
        this.toChild = toChild;
    }
}


