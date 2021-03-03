package com.company;

import com.company.model.SpecificMove;
import com.company.model.exceptions.SolitarieException;
import com.company.model.move.FoundationToTableau;
import com.company.model.move.StockMove;
import com.company.model.state.ClosedSolitaireState;
import com.company.model.state.ISolitaireState;
import com.company.model.state.OpenSolitaireState;
import com.company.utils.PrintGameState;

import java.util.List;

public class Main {

    public static void main(String[] args) throws SolitarieException {
        //Create a test game
        Solitaire solitaire = new Solitaire();
        solitaire.initGame();
        List<ISolitaireState> states = solitaire.getStates();

        PrintGameState pgs = new PrintGameState();
        //pgs.initOpenSolitareState(states.get(0));
        //pgs.initClosedSolitareState(states.get(0));
        //pgs.printCurrentState();

        //Create a test move
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
    }
}
