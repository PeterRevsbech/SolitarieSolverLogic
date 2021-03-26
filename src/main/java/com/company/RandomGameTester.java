package com.company;

public class RandomGameTester {
    long startTime, endTime;
    int gamesWon,numberOfGames, totalTurnsPlayed;


    public static void main(String[] args) {
        RandomGameTester randomGameTester = new RandomGameTester();
        System.out.println(randomGameTester.playGames(500));
    }


    public String playGames(int numberOfGames){
        this.gamesWon=0;
        this.numberOfGames=numberOfGames;
        this.startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfGames; i++) {
            Solitaire solitaire = new Solitaire();
            solitaire.initGame(true,false);
            if (solitaire.playGame()){
                gamesWon++;
            }
            totalTurnsPlayed +=solitaire.getTurnsPlayed();
        }

        this.endTime = System.currentTimeMillis();

        return report();


    }

    private String report(){
        String report = String.format("Won %d out of %d games. Winning percentage is %d",gamesWon,numberOfGames,gamesWon/numberOfGames);
        report += String.format("\nTotal time: %d ms",(endTime-startTime));
        report += String.format("\nAverage time pr game: %d ms",(endTime-startTime)/numberOfGames);
        report += String.format("\nAverage number of turns pr game: %d",totalTurnsPlayed/numberOfGames);
        return report;
    }




}
