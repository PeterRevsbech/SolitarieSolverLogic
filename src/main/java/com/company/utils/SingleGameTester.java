
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.utils;

import com.company.logic.Solitaire;
import com.company.models.exceptions.SolitarieException;
import com.company.models.states.ISolitaireState;
import com.company.strategy.TreeSearcher;
import com.company.utils.PrintGameState;

import java.util.List;

public class SingleGameTester {
    public static int i = 0;

    public static void main(String[] args) throws SolitarieException {
        ///for (int j = 0; j < 1000; j++) {
        //Create a test game
        Solitaire solitaire = new Solitaire();
        //solitaire.initGame(false); //Use this to have a winnable game (testing purposes)
        solitaire.initGame(true, true, 2, 100, -1);

        List<ISolitaireState> states = solitaire.getStates();
        PrintGameState pgs = new PrintGameState();
        pgs.initOpenSolitareState(states.get(0));
        pgs.printCurrentState();

        for (i = 0; i < 300; i++) {

            if (solitaire.evaluateGameWon(states.get(i))) {
                winnerMsg();
                System.exit(0);
            }

            System.out.println("Move: " + i);
            solitaire.makeNextMove();
            System.out.println(solitaire.getNextMove().toString());
            System.out.println(String.format("Evaluated %d nodes in a depth of %d", TreeSearcher.getCounter(), TreeSearcher.getStartDepth()));

            pgs.initOpenSolitareState(states.get(i + 1));
            pgs.printCurrentState();


        }
    }


    private static void winnerMsg() {
        System.out.println("  /$$$$$$   /$$$$$$  /$$      /$$ /$$$$$$$$       /$$      /$$  /$$$$$$  /$$   /$$\n" +
                " /$$__  $$ /$$__  $$| $$$    /$$$| $$_____/      | $$  /$ | $$ /$$__  $$| $$$ | $$\n" +
                "| $$  \\__/| $$  \\ $$| $$$$  /$$$$| $$            | $$ /$$$| $$| $$  \\ $$| $$$$| $$\n" +
                "| $$ /$$$$| $$$$$$$$| $$ $$/$$ $$| $$$$$         | $$/$$ $$ $$| $$  | $$| $$ $$ $$\n" +
                "| $$|_  $$| $$__  $$| $$  $$$| $$| $$__/         | $$$$_  $$$$| $$  | $$| $$  $$$$\n" +
                "| $$  \\ $$| $$  | $$| $$\\  $ | $$| $$            | $$$/ \\  $$$| $$  | $$| $$\\  $$$\n" +
                "|  $$$$$$/| $$  | $$| $$ \\/  | $$| $$$$$$$$      | $$/   \\  $$|  $$$$$$/| $$ \\  $$\n" +
                " \\______/ |__/  |__/|__/     |__/|________/      |__/     \\__/ \\______/ |__/  \\__/\n" +
                "                                                                                  \n" +
                "                                                                                  \n" +
                "                ");
    }
}
