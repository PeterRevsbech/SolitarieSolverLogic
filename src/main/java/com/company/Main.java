package com.company;

import com.company.model.exceptions.SolitarieException;
import com.company.model.state.ISolitaireState;
import com.company.strategy.TreeSearcher;
import com.company.utils.PrintGameState;

import java.util.List;

public class Main {
    public static int i = 0;

    public static void main(String[] args) throws SolitarieException {
        ///for (int j = 0; j < 1000; j++) {
        //Create a test game
        Solitaire solitaire = new Solitaire();
        //solitaire.initGame(false); //Use this to have a winnable game (testing purposes)
        solitaire.initGame(true, true, 2);


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
            System.out.println(String.format("Evaluated %d nodes in tree search", TreeSearcher.getCounter()));

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
