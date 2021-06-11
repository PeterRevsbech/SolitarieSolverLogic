package com.company.utils;

import com.company.logic.Solitaire;
import com.company.models.states.ClosedSolitaireState;

public class ClosedGameTester {
    public static void main(String[] args) {
        Solitaire solitaire = new Solitaire();
        solitaire.initClosedGame(ClosedSolitaireState.newTestGame(), -1, 100);

        PrintGameState pgs = new PrintGameState();
        pgs.initClosedSolitareState(solitaire.getStates().get(0));
        pgs.printCurrentState();
        System.out.println(solitaire.getNextMove());
    }
}
