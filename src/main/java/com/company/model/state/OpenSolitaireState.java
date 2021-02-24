package com.company.model.state;

import com.company.model.*;

public class OpenSolitaireState implements ISolitaireState{

    private WastePile wastePile;
    private StockPile stockPile;
    private Tableau tableau;
    private Foundation foundation;


    public static OpenSolitaireState newGame(){
        OpenSolitaireState state = new OpenSolitaireState();
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



    @Override
    public boolean isStockEmpty() {
        return stockPile.isEmpty();
    }

    public void setTableau(Tableau tableau) {
        this.tableau = tableau;
    }

    @Override
    public Card getWasteTop() {
        if(wastePile.getCards().size() > 0 ){
            return wastePile.getCards().get(wastePile.getCards().size()-1);
        }else return null;
    }

    public Foundation getFoundation() {
        return foundation;
    }

    public void setFoundation(Foundation foundation) {
        this.foundation = foundation;
    }

    public String toString() {

        //When testing a string with no hidden information might come in handy
        String result = "";
        result += "\n";
        result += "\nSTOCKPILE: " + stockPile.toString().replace("[", "").replace("]", "") + "\n";
        result += "\nWASTE: " + wastePile.toString() + "\n";
        result += "\nFOUNDATION: " + foundation.toString().replace("[[", "[").replace("]]", "]") + "\n";
        result += "\nTABLEAU: " + tableau.toString().replace("[[", "[").replace("]]", "]") + "\n";

        PrintStockWasteFoundPiles();
        PrintTableauPiles();
        return result;
    }

    private void PrintTableauPiles() {
        //Generates the tableau matrix, hidden cards are made into *'s
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

    private void PrintStockWasteFoundPiles() {
        System.out.println("---------------------------------------------");
        //Stockpile and foundation matrix
        String[][] StockFoundMatrix = new String[3][9];

        //Stockpile heading and the top stockpilecard
        StockFoundMatrix[0][0] = "STOCK";
        StockFoundMatrix[1][0] = stockPile.getCard(stockPile.getCards().size() - 1).toString();

        StockFoundMatrix[0][1] = "|";
        StockFoundMatrix[1][1] = "|";

        //Wastepile heading and the top waste pile card(needs implementation)
        //TODO mangler wastepile logik, bunken skal initialiseres som "tom", så vi undgår OOB error når spillet bygges
        StockFoundMatrix[0][3] = "WASTE";
        StockFoundMatrix[1][3] = "empty"; //wastePile.getCard(wastePile.getCards().size()-1).toString();

        StockFoundMatrix[0][4] = "|";
        StockFoundMatrix[1][4] = "|";

        //Foundation heading and the 4 foundation piles
        //TODO mangler foundation pile logik, bunken skal initialiseres som "tom", så vi undgår OOB error når spillet bygges
        StockFoundMatrix[0][6] = "  FOUNDATION";
        StockFoundMatrix[1][5] = "-";
        StockFoundMatrix[1][6] = "-";
        StockFoundMatrix[1][7] = "-";
        StockFoundMatrix[1][8] = "-";

        StockFoundMatrix[2][0] = "---------------------------------------------";
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
}
