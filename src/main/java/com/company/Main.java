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
        //solitaire.initGame(false); //Use this to have a winnable game (testing purposes)
        solitaire.initGame(true);

        List<ISolitaireState> states = solitaire.getStates();
        PrintGameState pgs = new PrintGameState();

        pgs.initOpenSolitareState(states.get(0));
        pgs.printCurrentState();

        OpenSolitaireState copy = (OpenSolitaireState) states.get(0).clone();
        OpenSolitaireState copy2 = null;
        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            solitaire.makeNextMove();
            if (i == 6) {
                copy2 = (OpenSolitaireState) states.get(6).clone();
            }
            pgs.initOpenSolitareState(states.get(i+1));
            pgs.printCurrentState();
        }

        System.out.println("KOPI AF STATE 0");
        pgs.initOpenSolitareState(copy);
        pgs.printCurrentState();


        System.out.println("KOPI AF STATE 6");
        pgs.initOpenSolitareState(copy2);
        pgs.printCurrentState();
    }
}
