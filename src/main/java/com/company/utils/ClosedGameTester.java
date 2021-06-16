
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

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
