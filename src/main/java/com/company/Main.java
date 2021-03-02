package com.company;

import com.company.model.SpecificMove;
import com.company.model.exceptions.SolitarieException;
import com.company.model.move.StockMove;
import com.company.model.state.ISolitaireState;
import com.company.model.state.OpenSolitaireState;

import java.util.List;

public class Main {

    public static void main(String[] args) throws SolitarieException {
        //Create a test game
        Solitaire solitaire = new Solitaire();
        solitaire.initGame();
        List<ISolitaireState> states = solitaire.getStates();
        System.out.println(states.get(0));

        //Create a test move
        SpecificMove stockMove = new SpecificMove();
        stockMove.setMoveType(new StockMove());

        //Execute the move
        for (int i = 0; i < 100; i++) {
            states.add(solitaire.makeMove(states.get(i), stockMove));
            //Print new state
            System.out.println(states.get(i));
        }




    }
}
