package com.company;

import com.company.model.SpecificMove;
import com.company.model.exceptions.SolitarieException;
import com.company.model.move.FoundationToTableau;
import com.company.model.move.StockMove;
import com.company.model.state.ClosedSolitaireState;
import com.company.model.state.ISolitaireState;
import com.company.model.state.OpenSolitaireState;
import com.company.strategy.Strategy;
import com.company.utils.PrintGameState;

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




        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            solitaire.makeNextMove();
            pgs.initOpenSolitareState(states.get(i+1));
            pgs.printCurrentState();
        }








    }

}
