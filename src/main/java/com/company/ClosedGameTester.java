package com.company;

import com.company.model.state.ClosedSolitaireState;
import com.company.utils.PrintGameState;

public class ClosedGameTester {
    public static void main(String[] args) {
        Solitaire solitaire = new Solitaire();
        solitaire.initClosedGame(ClosedSolitaireState.newTestGame());

        PrintGameState pgs = new PrintGameState();
        pgs.initClosedSolitareState(solitaire.getStates().get(0));
        pgs.printCurrentState();
        System.out.println(solitaire.getNextMove());

    }

}
