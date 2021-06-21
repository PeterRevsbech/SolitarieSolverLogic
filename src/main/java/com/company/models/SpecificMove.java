
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models;

import com.company.models.moves.*;
import com.company.models.moves.movestypes.*;
import com.company.models.states.ISolitaireState;
import com.company.strategy.PointsTable;

import java.util.ArrayList;
import java.util.List;

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

    public String toString5(){
        String stringToCard = toCard == null ? "Empty field" : toCard.toString();
        //String fromCard = fromParent == null ? "Stockpile" : fromParent.toString();

        return fromParent + " -> " + stringToCard;
    }

    static int stockmoves = 0;
    static int wastetotableaumoves = 0;
    static int wastetofoundationmoves = 0;
    static int tableautofoundationmoves = 0;
    static int tableautotableaumoves = 0;
    static int foundationtotableaumoves = 0;

    public static void printMoveAmount() {
        System.out.println("- stock moves: " + stockmoves);
        System.out.println("- waste to tableau moves: " + wastetotableaumoves);
        System.out.println("- waste to foundation moves: " + wastetofoundationmoves);
        System.out.println("- tableau to foundation moves: " + tableautofoundationmoves);
        System.out.println("- tableau to tableau moves: " + tableautotableaumoves);
        System.out.println("- foundation to tableau moves: " + foundationtotableaumoves);
    }

    public static void resetMoveCounter() {
        stockmoves = 0;
        wastetotableaumoves = 0;
        wastetofoundationmoves = 0;
        tableautofoundationmoves = 0;
        tableautotableaumoves = 0;
        foundationtotableaumoves = 0;
    }

    public String detailedToString(ISolitaireState state) {
        String stringToCard = "";
        stringToCard = toCard == null ? "empty field" : toCard.toString();

        if (moveType instanceof StockMove) {
            stockmoves++;
            if (state != null && state.isStockEmpty()) { //If stock is empty (cannot be in first round)
                return "Turn the waste pile over to renew the stock pile.";
            }
            return toCard == null ? "Reveal the top card from the stock and move it to the empty " +
                    "field in the waste pile." : "Reveal the top card from the stock and move it " +
                    "to " + toCard + " in the waste pile.";

        } else if (moveType instanceof WasteToTableau) {
            wastetotableaumoves++;
            return "Move " + fromParent + " from waste to " + stringToCard + " in the tableau.";
        } else if (moveType instanceof WasteToFoundation) {
            wastetofoundationmoves++;
            return "Move " + fromParent + " from waste to the foundation.";
        } else if (moveType instanceof TableauToFoundation) {
            tableautofoundationmoves++;
            return "Move " + fromParent + " from tableau to the foundation.";
        } else if (moveType instanceof TableauToTableau) {
            tableautotableaumoves++;
            return "Move " + fromParent + " from tableau to " + stringToCard + " in the tableau.";
        } else if (moveType instanceof FoundationToTableau) {
            foundationtotableaumoves++;
            return "Move " + fromParent + " from foundation to " + stringToCard + " in the tableau.";
        }
        return "";
    }
}


