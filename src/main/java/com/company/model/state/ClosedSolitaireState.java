package com.company.model.state;

import com.company.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClosedSolitaireState implements ISolitaireState {

    private WastePile wastePile;
    private StockPile stockPile;
    private Tableau tableau;
    private Foundation foundation;
    private List<Card> knownStockWaste = new ArrayList<>();

    public static ClosedSolitaireState newTestGame() {//Will probably not be used for more than debugging - The actual state will be given by OpenCV
        ClosedSolitaireState state = new ClosedSolitaireState();
        CardDeque deque = new CardDeque(true, -1);

        state.setTableau(new Tableau());
        //Initialize Tableau
        for (int i = 0; i < 7; i++) {
            Pile pile = state.getTableau().getPiles()[i];
            for (int j = 0; j < i + 1; j++) {
                if (j == i) {
                    //Turn the uppermost card in each pile face up
                    pile.addCard(deque.draw());
                    pile.getCards().get(i).setFaceUp(true);
                } else {
                    pile.addCard(new Card(null, 0, false));
                }
            }
        }

        //Initialize Foundation
        state.setFoundation(new Foundation());

        //Initialize WastePile
        state.setWastePile(new WastePile());

        //Initialize StockPile
        state.setStockPile(new StockPile());
        state.getStockPile().addCard(new Card(null, 0, false));

        //Initialize knownStockWaste
        state.knownStockWaste = new ArrayList<>();

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
        if (wastePile.getCards().size() > 0) {
            return wastePile.getCards().get(wastePile.getCards().size() - 1);
        } else return null;
    }

    public Foundation getFoundation() {
        return foundation;
    }

    public void setFoundation(Foundation foundation) {
        this.foundation = foundation;
    }

    @Override
    public List<Card> getKnownStockWaste() {
        return knownStockWaste;
    }

    @Override
    public void setKnownStockWaste(List<Card> stockWaste) {
        this.knownStockWaste = stockWaste;
    }



    @Override
    public ISolitaireState clone() {
        ISolitaireState obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(this);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = (ISolitaireState) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }
}
