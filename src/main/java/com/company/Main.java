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
import com.company.strategy.TreeSearcher;
import com.company.utils.PrintGameState;

import java.io.*;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SolitarieException {
        ///for (int j = 0; j < 1000; j++) {
        //Create a test game
        Solitaire solitaire = new Solitaire();
        //solitaire.initGame(false); //Use this to have a winnable game (testing purposes)
        solitaire.initGame(true,true,24);


        List<ISolitaireState> states = solitaire.getStates();
        PrintGameState pgs = new PrintGameState();
        pgs.initOpenSolitareState(states.get(0));
        pgs.printCurrentState();


        for (int i = 0; i < 300; i++) {
            System.out.println("Move: " + i);
            solitaire.makeNextMove();
            System.out.println(solitaire.getNextMove().toString());
            System.out.println(String.format("Evaluated %d nodes in tree search", TreeSearcher.getCounter()));

            pgs.initOpenSolitareState(states.get(i + 1));
            pgs.printCurrentState();

        }
        //}

    }


}
