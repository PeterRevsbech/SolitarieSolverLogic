package com.company.model;

import java.util.Arrays;

public class SolitaireState {

    private WastePile wastePile;
    private StockPile stockPile;
    private Tableau tableau;
    private Foundation foundation;


    public static SolitaireState newGame() {
        SolitaireState state = new SolitaireState();
        CardDeque deque = new CardDeque();

        //Initialize Tableau
        state.setTableau(new Tableau());
        for (int i = 0; i < 7; i++) {

            Pile pile = state.getTableau().getPiles()[i];

            for (int j = 0; j < i + 1; j++) {
                pile.addCard(deque.draw());
                if (j == i) {
                    //Turn the uppermost card in each pile face up
                    pile.getCards().get(i).setFaceUp(true);
                }
            }
        }

        //Initialize Foundation
        state.setFoundation(new Foundation());

        //Initialize WastePile
        state.setWastePile(new WastePile());

        //Initialize StockPile
        state.setStockPile(new StockPile());
        while (!deque.getCardsList().isEmpty()) {
            state.getStockPile().addCard(deque.draw());
        }

        return state;

    }

    public WastePile getWastePile() {
        return wastePile;
    }

    public void setWastePile(WastePile wastePile) {
        this.wastePile = wastePile;
    }

    public StockPile getStockPile() {
        return stockPile;
    }

    public void setStockPile(StockPile stockPile) {
        this.stockPile = stockPile;
    }

    public Tableau getTableau() {
        return tableau;
    }

    public void setTableau(Tableau tableau) {
        this.tableau = tableau;
    }

    public Foundation getFoundation() {
        return foundation;
    }

    public void setFoundation(Foundation foundation) {
        this.foundation = foundation;
    }

    public String toString() {
        String result = "";
        result += "\n";
        result += "\nSTOCKPILE: " + stockPile.toString().replace("[", "").replace("]", "") + "\n";
        result += "\nWASTE: " + wastePile.toString() + "\n";
        result += "\nFOUNDATION: " + foundation.toString().replace("[[", "[").replace("]]", "]") + "\n";
        result += "\nTABLEAU: " + tableau.toString().replace("[[", "[").replace("]]", "]") + "\n";


        String[][] tableauMatrix = new String[7][7];

        int counter = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = counter; j < 7; j++) {
                if (!tableau.getPiles()[j].getCard(i).isFaceUp()) {
                    tableauMatrix[i][j] = "*";
                } else {
                    tableauMatrix[i][j] = tableau.getPiles()[j].getCard(i).toString();
                }
            }
            counter++;
        }

        String[][] StockFoundMatrix = new String[2][9];
        StockFoundMatrix[0][0] = "STOCK";
        StockFoundMatrix[1][0] = stockPile.getCard(stockPile.getCards().size() - 1).toString();

        //TODO mangler wastepile logik, bunken skal initialiseres som "tom", så vi undgår OOB error når spillet bygges
        StockFoundMatrix[0][2] = "WASTE";
        StockFoundMatrix[1][2] = "empty"; //wastePile.getCard(wastePile.getCards().size()-1).toString();

        //TODO mangler foundation pile logik, bunken skal initialiseres som "tom", så vi undgår OOB error når spillet bygges
        StockFoundMatrix[0][4] = "FOUNDATION";
        StockFoundMatrix[1][3] = "-";
        StockFoundMatrix[1][4] = "-";
        StockFoundMatrix[1][5] = "-";
        StockFoundMatrix[1][6] = "-";

        for (int row = 0; row < StockFoundMatrix.length; row++) {
            for (int col = 0; col < StockFoundMatrix[row].length; col++) {
                if (StockFoundMatrix[row][col] == null) {
                    StockFoundMatrix[row][col] = "";
                }
                System.out.printf("%5s", StockFoundMatrix[row][col]);
            }
            System.out.println();
        }

        System.out.println("\n-----------TABLEAU-----------");
        for (int row = 0; row < tableauMatrix.length; row++) {
            for (int col = 0; col < tableauMatrix[row].length; col++) {
                if (tableauMatrix[row][col] == null) {
                    tableauMatrix[row][col] = " ";
                }
                System.out.printf("%4s", tableauMatrix[row][col]);
            }
            System.out.println();
        }
        System.out.println("-----------------------------");
        return result;
    }
}
