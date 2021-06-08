package com.company.models;

import com.company.models.moves.*;
import com.company.models.moves.movestypes.*;
import com.company.models.states.ISolitaireState;
import com.company.strategy.PointsTable;

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
        int points = PointsTable.TURN_COST;

        if (moveType instanceof StockMove) {
            return points + PointsTable.STOCKMOVE;
        } else if (moveType instanceof WasteToTableau) {
            return points + PointsTable.WASTE_TO_TABLEAU;
        } else if (moveType instanceof WasteToFoundation) {
            return points + PointsTable.WASTE_TO_FOUNDATION;
        } else if (moveType instanceof TableauToFoundation) {
            return points + PointsTable.TABLEAU_TO_FOUNDATION;
        } else if (moveType instanceof TableauToTableau) {
            return points + PointsTable.TABLEAU_TO_TABLEAU;
        } else if (moveType instanceof FoundationToTableau) {
            return points + PointsTable.FODUNDATION_TO_TABLEAU;
        }

        return 0;
    }

    public static boolean isTableauReveal(ISolitaireState oldState, ISolitaireState newState) {
        if (newState.getNumberOfFaceDownCards() == oldState.getNumberOfFaceDownCards() - 1) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Move type: " + moveType + "\nMove executed: " + fromParent + " -> " + toCard;
    }



    public String detailedToString() {
        //TODO create this
        String stringToCard = "";
        if (toCard==null) {
            stringToCard="empty field";
        } else{
            stringToCard=toCard.toString();
        }

        if (moveType instanceof StockMove) {
            return "Turn the top card from the stock.";
        } else if (moveType instanceof WasteToTableau ) {
            return "Move "+fromParent+" from waste, to "+stringToCard+" in the tableau.";
        } else if (moveType instanceof WasteToFoundation ) {
            return "Move "+fromParent+" from waste, to the foundation.";
        } else if (moveType instanceof TableauToFoundation) {
            return "Move "+fromParent+" from tableau, to the foundation.";
        } else if (moveType instanceof TableauToTableau) {
            return "Move "+fromParent+" from tableau, to "+stringToCard+" in the tableau.";
        } else if (moveType instanceof FoundationToTableau) {
            return "Move "+fromParent+" from foundation, to "+stringToCard+" in the tableau.";
        }
        //TODO Flip stockpile



        return "";
    }

    public String formatGuiMoveMsg(boolean unkownCard){
        //TODO remove unknownCards for debugging purp
        return detailedToString() +";"+ unkownCard;
    }
}


