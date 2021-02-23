package com.company.model;

public class SolitaireState {

    private WastePile wastePile;
    private StockPile stockPile;
    private Tableau tableau;
    private Foundation foundation;


    public static SolitaireState newGame(){
        SolitaireState state = new SolitaireState();
        CardDeque deque = new CardDeque();

        //Initialize Tableau
        state.setTableau(new Tableau());
        for (int i = 0; i < 7; i++) {

            Pile pile = state.getTableau().getPiles()[i];

            for (int j = 0; j < i+1; j++) {
                pile.addCard(deque.draw());
                if (j==i){
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
        while(!deque.getCardsList().isEmpty()){
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

    public String toString(){
        //TODO Make a nice toString() method, that prints the game state
        String result = "";
        result += tableau.toString();
        result+= "\n\n" + foundation.toString();
        result+= "\n\n" + wastePile.toString();
        result+= "\n\n" + stockPile.toString();
        return result;
    }
}
