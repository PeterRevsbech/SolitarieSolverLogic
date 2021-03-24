package com.company;

import com.company.model.Card;
import com.company.model.SpecificMove;
import com.company.model.exceptions.SolitarieException;
import com.company.model.move.FoundationToTableau;
import com.company.model.move.StockMove;
import com.company.model.state.ClosedSolitaireState;
import com.company.model.state.ISolitaireState;
import com.company.model.state.OpenSolitaireState;
import com.company.strategy.Strategy;
import com.company.utils.PrintGameState;

import java.io.*;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SolitarieException {
        //Create a test game
        Solitaire solitaire = new Solitaire();
        solitaire.initGame(false); //Use this to have a winnable game (testing purposes)
        //solitaire.initGame(true);

        List<ISolitaireState> states = solitaire.getStates();
        ISolitaireState state = (ISolitaireState) copy(states.get(0));
        PrintGameState pgs = new PrintGameState();

        pgs.initOpenSolitareState(states.get(0));
        pgs.printCurrentState();
        ISolitaireState copy=null;

        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            solitaire.makeNextMove();
            if(i==5){
                copy = (ISolitaireState) copy(states.get(5));
            }
            pgs.initOpenSolitareState(states.get(i+1));
//            pgs.printCurrentState();

        }
        System.out.println(copy.getTableau());
        System.out.println(states.get(99).getTableau());


//        System.out.println("State: " + copy().getTableau().toString() + "\nState2: " + state2.getTableau().toString());
//        Card card = new Card(Card.Suit.Diamonds,13,true);
//        ArrayList<Card> list = new ArrayList<>();
//
//        list.add(card);
//        state2.getTableau().getPile(0).setCards(list);
//        System.out.println("State: " + state.getTableau().toString() + "\nState2: " + state2.getTableau().toString()); // dette er shallow copy, vi skal bruge deep copy


    }
    public static ISolitaireState copy(ISolitaireState orig) {
        ISolitaireState obj = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                    new ByteArrayInputStream(bos.toByteArray()));
            obj = (ISolitaireState) in.readObject();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }

}
