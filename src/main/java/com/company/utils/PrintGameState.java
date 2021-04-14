package com.company.utils;

import com.company.models.piles.Foundation;
import com.company.models.piles.StockPile;
import com.company.models.piles.Tableau;
import com.company.models.piles.WastePile;
import com.company.models.states.ISolitaireState;

public class PrintGameState {

    private WastePile wastePile;
    private StockPile stockPile;
    private Tableau tableau;
    private Foundation foundation;
    private boolean isClosed;

    ISolitaireState solitaireState;

    public PrintGameState() {
    }

    public void initOpenSolitareState(ISolitaireState openSolitaireState) {
        this.solitaireState = openSolitaireState;
        this.wastePile = solitaireState.getWastePile();
        this.stockPile = solitaireState.getStockPile();
        this.tableau = solitaireState.getTableau();
        this.foundation = solitaireState.getFoundation();
    }

    public void initClosedSolitareState(ISolitaireState closedSolitaireState) {
        this.solitaireState = closedSolitaireState;
        this.wastePile = solitaireState.getWastePile();
        this.stockPile = solitaireState.getStockPile();
        this.tableau = solitaireState.getTableau();
        this.foundation = solitaireState.getFoundation();
        isClosed = true;
    }

    public void printCurrentState() {
        if (isClosed) {
            printStockWasteFoundMatrix();
            printTableau();
        } else {
            printStockWasteFoundMatrix();
            printTableau();
            printCardArrays();
        }
    }

    private void printCardArrays() {
        //When testing a string with no hidden information might come in handy
        String result = "";
        result += "STOCK: " + stockPile.toString().replace("[", "").replace("]", "") + "\n";
        result += "WASTE: " + wastePile.toString().replace("[", "").replace("]", "") + "\n";
        //result += "\nFOUNDATION: " + foundation.toString().replace("[[", "[").replace("]]", "]") + "\n";
        //result += "\nTABLEAU: " + tableau.toString().replace("[[", "[").replace("]]", "]") + "\n";
        result += "\n\n\n";

        System.out.println(result);
    }

    private void printTableau() {
        //Generates the tableau matrix, hidden cards are made into *'s
        String[][] tableauMatrix = buildTableauMatrix();

        //Prints the tableu
        System.out.println("\n-------------------TABLEAU-------------------");
        for (int row = 0; row < tableauMatrix.length; row++) {
            for (int col = 0; col < tableauMatrix[row].length; col++) {
                if (tableauMatrix[row][col] == null) {
                    tableauMatrix[row][col] = "";
                }
                System.out.printf("%6s", tableauMatrix[row][col]);
            }
            System.out.println();
        }
        System.out.println("---------------------------------------------");
    }

    private String[][] buildTableauMatrix() {
        int tabLen = tableau.getMaxTableauLength();
        String[][] tableauMatrix = new String[tabLen][7]; //TODO har tilføjet +1 til tabLen, for at undgå mærkelig OOB error
        for (int i = 0; i < tabLen; i++) {
            for (int j = 0; j < 7; j++) {
                int pileLen = tableau.getPiles()[j].getCards().size();
                if (i >= pileLen) { //If there is no card at this place
                    tableauMatrix[i][j] = " ";
                } else if (!tableau.getPiles()[j].getCard(i).isFaceUp()) { //if the card is NOT face up
                    tableauMatrix[i][j] = "*";
                } else if (tableau.getPiles()[j].getCard(i).isFaceUp() && tableau.getPiles()[j].getCard(i).getSuit() != null) { //if the card IS faceup and HAS a suit
                    tableauMatrix[i][j] = tableau.getPiles()[j].getCard(i).toString();
                }
            }
        }
        return tableauMatrix;
    }

    private void printStockWasteFoundMatrix() {
        System.out.println("---------------------------------------------");
        String[][] StockFoundMatrix = buildStockWasteFoundMatrix();
        //Loop for printing the stockpile and foundation piles
        for (int row = 0; row < StockFoundMatrix.length; row++) {
            for (int col = 0; col < StockFoundMatrix[row].length; col++) {
                if (StockFoundMatrix[row][col] == null) {
                    StockFoundMatrix[row][col] = "";
                }
                System.out.printf("%5s", StockFoundMatrix[row][col]);
            }
            System.out.println();
        }
    }

    private String[][] buildStockWasteFoundMatrix() {
        //Stockpile and foundation matrix
        String[][] StockFoundMatrix = new String[3][9];

        //Stockpile heading and the top stockpilecard
        StockFoundMatrix[0][0] = "STOCK";
        StockFoundMatrix[1][0] = stockPile.getCards().size() + " cd";

        StockFoundMatrix[0][1] = "|";
        StockFoundMatrix[1][1] = "|";

        //Wastepile heading and the top waste pile card(needs implementation)
        StockFoundMatrix[0][3] = "WASTE";
        if (wastePile.isEmpty()) {
            StockFoundMatrix[1][3] = "empty";
        } else {
            StockFoundMatrix[1][3] = wastePile.getCard(wastePile.getCards().size() - 1).toString(); //;
        }

        StockFoundMatrix[0][4] = "|";
        StockFoundMatrix[1][4] = "|";

        //Foundation heading and the 4 foundation piles
        //TODO mangler foundation pile logik, bunken skal initialiseres som "tom", så vi undgår OOB error når spillet bygges
        StockFoundMatrix[0][6] = "  FOUNDATION";

        if (foundation.getPiles()[0].isEmpty()) {
            StockFoundMatrix[1][5] = "-";
        } else {
            StockFoundMatrix[1][5] = foundation.getPiles()[0].getTopCard().toString();
        }
        if (foundation.getPiles()[1].isEmpty()) {
            StockFoundMatrix[1][6] = "-";
        } else {
            StockFoundMatrix[1][6] = foundation.getPiles()[1].getTopCard().toString();
        }
        if (foundation.getPiles()[2].isEmpty()) {
            StockFoundMatrix[1][7] = "-";
        } else {
            StockFoundMatrix[1][7] = foundation.getPiles()[2].getTopCard().toString();
        }
        if (foundation.getPiles()[3].isEmpty()) {
            StockFoundMatrix[1][8] = "-";
        } else {
            StockFoundMatrix[1][8] = foundation.getPiles()[3].getTopCard().toString();
        }
        StockFoundMatrix[2][0] = "---------------------------------------------";
        return StockFoundMatrix;
    }
}
