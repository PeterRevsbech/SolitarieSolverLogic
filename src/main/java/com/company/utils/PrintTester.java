package com.company.utils;

import com.company.logic.Solitaire;
import com.company.models.states.ISolitaireState;

import java.util.List;

public class PrintTester {
    public static void main(String[] args) {
        //Create a test game
        Solitaire solitaire = new Solitaire();
        solitaire.initGame(false, true, 1, 1000, 0); //Use this to have a winnable game (testing purposes)
        //solitaire.initGame(true);

        List<ISolitaireState> states = solitaire.getStates();

        PrintGameState pgs = new PrintGameState();
        //pgs.initOpenSolitareState(states.get(0));
        //pgs.initClosedSolitareState(states.get(0));
        //pgs.printCurrentState();

        //Create a test move
        /*
        SpecificMove stockMove = new SpecificMove();
        stockMove.setMoveType(new StockMove());


        //Execute the move
        for (int i = 0; i < 100; i++) {
            states.add(solitaire.makeMove(states.get(i), stockMove));
            //Print new state
            //pgs.initClosedSolitareState(states.get(i));
            pgs.initOpenSolitareState(states.get(i));
            pgs.printCurrentState();
        }

         */
        pgs.initOpenSolitareState(states.get(0));
        pgs.printCurrentState();

/*        solitaire.makeNextMove();
        pgs.initOpenSolitareState(states.get(1));
        pgs.printCurrentState();

        solitaire.makeNextMove();
        pgs.initOpenSolitareState(states.get(2));
        pgs.printCurrentState();

        solitaire.makeNextMove();
        pgs.initOpenSolitareState(states.get(3));
        pgs.printCurrentState();

        solitaire.makeNextMove();
        pgs.initOpenSolitareState(states.get(4));
        pgs.printCurrentState();*/
    }
}
