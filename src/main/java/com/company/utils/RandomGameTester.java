package com.company.utils;

import com.company.logic.Solitaire;
import com.company.models.states.ISolitaireState;

import java.util.List;
import java.util.Random;

public class RandomGameTester {
    long startTime, endTime, currentGameTime, currentStartTime;
    int gamesWon, numberOfGames, totalTurnsPlayed, turnsThisGame, totalTurnsWonGames, wonGameCounter;
    private static final int DATA_SEED = 5;
    private static final double TIME_LIMIT_MILIS = 10;
    private static final int FIXED_DEPTH = -1;
    private static final int NUMBER_OF_GAMES = 100;
    private static final int NUMBER_OF_UPDATES = 100;
    private static Random numberGenerator;

    public static void main(String[] args) {
        RandomGameTester randomGameTester = new RandomGameTester();
        System.out.println(randomGameTester.playGames(NUMBER_OF_GAMES, NUMBER_OF_UPDATES));
    }


    public String playGames(int numberOfGames, int numberOfUpdates) {
        numberGenerator = new Random(DATA_SEED);
        int gamesPrUpdate = numberOfGames / numberOfUpdates;
        this.gamesWon = 0;
        this.numberOfGames = numberOfGames;
        this.startTime = System.currentTimeMillis();
        Solitaire solitaire;

        for (int i = 1; i <= numberOfGames; i++) {
            currentStartTime = System.currentTimeMillis();
            solitaire = new Solitaire();
            solitaire.initGame(true, false, numberGenerator.nextInt(), (int) TIME_LIMIT_MILIS, FIXED_DEPTH);
            if (solitaire.playGame()) {
                gamesWon++;
            }
            if (solitaire.isGameWon()) {
                totalTurnsWonGames += solitaire.getTurnsPlayed();
                wonGameCounter++;
            }

            totalTurnsPlayed += solitaire.getTurnsPlayed();
            turnsThisGame = solitaire.getTurnsPlayed();
            this.endTime = System.currentTimeMillis();

            currentGameTime = endTime - currentStartTime;

            if (i % gamesPrUpdate == 0) {
                System.out.println(update(i));
            }
        }


        return report();
    }

    private String report() {
        String report = String.format("\nWon %d out of %d games. Winning percentage: %.2f", gamesWon, numberOfGames, (gamesWon * 100.0) / numberOfGames);
        report += String.format("\nAverage time spend pr game: %d s", ((endTime - startTime) / numberOfGames) / 1000);
        report += String.format("\nAverage number of moves pr WON game: %d", totalTurnsWonGames / wonGameCounter);
        report += String.format("\nAverage number of moves pr TOTAL game: %d", totalTurnsPlayed / numberOfGames);
        report += String.format("\nTotal run time: %d s", (endTime - startTime) / 1000);
        return report;
    }

    private String update(int i) {
        String report = String.format("Played %d out of %d games. %.2f pct done.", i, numberOfGames, (i * 100.0) / numberOfGames);
        report += String.format("\nMoves this game: %d, time spend this game: %d s", turnsThisGame, currentGameTime / 1000);
        //report += String.format("\nWon %d out of %d games. Winning percentage is %.2f\n", gamesWon, i, (100.0 * gamesWon) / i);
        report += String.format("\nWins: %d, Losses: %d. Winning percentage: %.2f\n", gamesWon, i - gamesWon, (100.0 * gamesWon) / i);
        return report;
    }


}
