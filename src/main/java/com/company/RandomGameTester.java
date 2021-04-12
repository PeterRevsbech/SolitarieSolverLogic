package com.company;

public class RandomGameTester {
    long startTime, endTime;
    int gamesWon, numberOfGames, totalTurnsPlayed;


    public static void main(String[] args) {
        RandomGameTester randomGameTester = new RandomGameTester();
        System.out.println(randomGameTester.playGames(1000, 1000));
    }


    public String playGames(int numberOfGames, int numberOfUpdates) {
        int gamesPrUpdate = numberOfGames / numberOfUpdates;
        this.gamesWon = 0;
        this.numberOfGames = numberOfGames;
        this.startTime = System.currentTimeMillis();
        Solitaire solitaire;

        for (int i = 1; i <= numberOfGames; i++) {
            solitaire = new Solitaire();
            solitaire.initGame(true, false, -1, 100, -1);
            if (solitaire.playGame()) {
                gamesWon++;
            }
            if (i % gamesPrUpdate == 0) {
                System.out.println(update(i));
            }

            totalTurnsPlayed += solitaire.getTurnsPlayed();
        }

        this.endTime = System.currentTimeMillis();

        return report();
    }

    private String report() {
        String report = String.format("Won %d out of %d games. Winning percentage is %.2f", gamesWon, numberOfGames, gamesWon / numberOfGames);
        report += String.format("\nTotal time: %d ms", (endTime - startTime));
        report += String.format("\nAverage time pr game: %d ms", (endTime - startTime) / numberOfGames);
        report += String.format("\nAverage number of turns pr game: %d\n", totalTurnsPlayed / numberOfGames);
        return report;
    }

    private String update(int i) {
        String report = String.format("Played %d out of %d games so far. %.2f pct done.", i, numberOfGames, (i*100.0) / numberOfGames);
        report += String.format("\nWon %d out of %d games. Winning percentage is %.2f\n", gamesWon, i, (100.0*gamesWon) / i);
        return report;
    }


}
