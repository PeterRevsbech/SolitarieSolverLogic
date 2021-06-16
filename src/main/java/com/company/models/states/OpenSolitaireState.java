
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.models.states;

import com.company.models.*;
import com.company.models.piles.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class OpenSolitaireState implements ISolitaireState, Cloneable, Serializable {

    private WastePile wastePile;
    private StockPile stockPile;
    private Tableau tableau;
    private Foundation foundation;
    private List<Card> knownStockWaste = new ArrayList<>();


    public static OpenSolitaireState newGame(boolean isShuffled, int dataSeed) {
        OpenSolitaireState state = new OpenSolitaireState();
        CardDeque deque = new CardDeque(isShuffled, dataSeed);

        if (!isShuffled) {
            return newCheatGame(state);
        } else {
            state.setTableau(new Tableau());
            //Initialize Tableau
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

            //Initialize knownStockWaste
            state.knownStockWaste = new ArrayList<>();
        }
        return state;
    }

    public static OpenSolitaireState newCheatGame(OpenSolitaireState state) {

        state.setTableau(new Tableau());

        Pile pile7 = state.getTableau().getPiles()[6];
        Pile pile6 = state.getTableau().getPiles()[5];
        Pile pile5 = state.getTableau().getPiles()[4];
        Pile pile4 = state.getTableau().getPiles()[3];
        Pile pile3 = state.getTableau().getPiles()[2];
        Pile pile2 = state.getTableau().getPiles()[1];
        Pile pile1 = state.getTableau().getPiles()[0];

        pile7.addCard(new Card(Card.Suit.Clubs, 8, false));
        pile7.addCard(new Card(Card.Suit.Clubs, 9, false));
        pile7.addCard(new Card(Card.Suit.Clubs, 10, false));
        pile7.addCard(new Card(Card.Suit.Clubs, 11, false));
        pile7.addCard(new Card(Card.Suit.Clubs, 12, false));
        pile7.addCard(new Card(Card.Suit.Clubs, 13, false));
        pile7.addCard(new Card(Card.Suit.Clubs, 1, true));

        pile6.addCard(new Card(Card.Suit.Spades, 9, false));
        pile6.addCard(new Card(Card.Suit.Spades, 10, false));
        pile6.addCard(new Card(Card.Suit.Spades, 11, false));
        pile6.addCard(new Card(Card.Suit.Spades, 12, false));
        pile6.addCard(new Card(Card.Suit.Spades, 13, false));
        pile6.addCard(new Card(Card.Suit.Spades, 1, true));

        pile5.addCard(new Card(Card.Suit.Hearts, 10, false));
        pile5.addCard(new Card(Card.Suit.Hearts, 11, false));
        pile5.addCard(new Card(Card.Suit.Hearts, 12, false));
        pile5.addCard(new Card(Card.Suit.Hearts, 13, false));
        pile5.addCard(new Card(Card.Suit.Hearts, 1, true));

        pile4.addCard(new Card(Card.Suit.Diamonds, 11, false));
        pile4.addCard(new Card(Card.Suit.Diamonds, 12, false));
        pile4.addCard(new Card(Card.Suit.Diamonds, 13, false));
        pile4.addCard(new Card(Card.Suit.Diamonds, 1, true));

        pile3.addCard(new Card(Card.Suit.Diamonds, 10, false));
        pile3.addCard(new Card(Card.Suit.Diamonds, 9, false));
        pile3.addCard(new Card(Card.Suit.Diamonds, 8, true));

        pile2.addCard(new Card(Card.Suit.Diamonds, 7, false));
        pile2.addCard(new Card(Card.Suit.Diamonds, 6, true));

        pile1.addCard(new Card(Card.Suit.Diamonds, 5, true));

        //Initialize Foundation
        state.setFoundation(new Foundation());

        //Initialize WastePile
        state.setWastePile(new WastePile());

        //Initialize StockPile
        state.setStockPile(new StockPile());
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 4));
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 3));
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 2));

        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 9));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 8));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 7));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 6));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 5));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 4));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 3));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 2));

        state.getStockPile().addCard(new Card(Card.Suit.Spades, 8));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 7));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 6));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 5));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 4));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 3));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 2));

        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 7));
        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 6));
        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 5));
        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 4));
        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 3));
        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 2));

        return state;
    }

    public static OpenSolitaireState newCheatWinableGame1(OpenSolitaireState state) {

        state.setTableau(new Tableau());

        Pile pile7 = state.getTableau().getPiles()[6];
        Pile pile6 = state.getTableau().getPiles()[5];
        Pile pile5 = state.getTableau().getPiles()[4];
        Pile pile4 = state.getTableau().getPiles()[3];
        Pile pile3 = state.getTableau().getPiles()[2];
        Pile pile2 = state.getTableau().getPiles()[1];
        Pile pile1 = state.getTableau().getPiles()[0];

        pile7.addCard(new Card(Card.Suit.Spades, 6, false));
        pile7.addCard(new Card(Card.Suit.Spades, 8, false));
        pile7.addCard(new Card(Card.Suit.Clubs, 9, false));
        pile7.addCard(new Card(Card.Suit.Diamonds, 11, false));
        pile7.addCard(new Card(Card.Suit.Diamonds, 6, false));
        pile7.addCard(new Card(Card.Suit.Diamonds, 4, false));
        pile7.addCard(new Card(Card.Suit.Spades, 1, true));

        pile6.addCard(new Card(Card.Suit.Clubs, 4, false));
        pile6.addCard(new Card(Card.Suit.Clubs, 12, false));
        pile6.addCard(new Card(Card.Suit.Hearts, 1, false));
        pile6.addCard(new Card(Card.Suit.Clubs, 1, false));
        pile6.addCard(new Card(Card.Suit.Clubs, 13, false));
        pile6.addCard(new Card(Card.Suit.Clubs, 3, true));

        pile5.addCard(new Card(Card.Suit.Diamonds, 8, false));
        pile5.addCard(new Card(Card.Suit.Hearts, 9, false));
        pile5.addCard(new Card(Card.Suit.Spades, 3, false));
        pile5.addCard(new Card(Card.Suit.Spades, 10, false));
        pile5.addCard(new Card(Card.Suit.Clubs, 8, true));

        pile4.addCard(new Card(Card.Suit.Hearts, 8, false));
        pile4.addCard(new Card(Card.Suit.Hearts, 4, false));
        pile4.addCard(new Card(Card.Suit.Spades, 7, false));
        pile4.addCard(new Card(Card.Suit.Clubs, 10, true));

        pile3.addCard(new Card(Card.Suit.Diamonds, 2, false));
        pile3.addCard(new Card(Card.Suit.Hearts, 2, false));
        pile3.addCard(new Card(Card.Suit.Spades, 11, true));

        pile2.addCard(new Card(Card.Suit.Spades, 9, false));
        pile2.addCard(new Card(Card.Suit.Clubs, 6, true));

        pile1.addCard(new Card(Card.Suit.Diamonds, 13, true));

        //Initialize Foundation
        state.setFoundation(new Foundation());

        //Initialize WastePile
        state.setWastePile(new WastePile());

        //Initialize StockPile
        state.setStockPile(new StockPile());
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 1));
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 3));
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 5));
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 7));
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 9));
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 10));
        state.getStockPile().addCard(new Card(Card.Suit.Diamonds, 12));

        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 3));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 5));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 6));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 7));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 10));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 11));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 12));
        state.getStockPile().addCard(new Card(Card.Suit.Hearts, 13));

        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 2));
        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 5));
        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 7));
        state.getStockPile().addCard(new Card(Card.Suit.Clubs, 11));

        state.getStockPile().addCard(new Card(Card.Suit.Spades, 2));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 4));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 5));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 12));
        state.getStockPile().addCard(new Card(Card.Suit.Spades, 13));

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
    public void setRevealedStockWaste(int knownCards) {

    }

    @Override
    public int getRevealedStockWaste() {
        return 0;
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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
