package com.company;

import com.company.logic.Solitaire;
import com.company.models.states.ClosedSolitaireState;
import com.company.models.states.ISolitaireState;
import com.company.utils.PrintGameState;
import com.company.utils.Server;

public class Main {
    public static void main(String[] args) {
        //Init server and game-object
        Server server = new Server();
        Solitaire solitaire = new Solitaire();
        server.startServer();

        //Read start configuration from GUI
        String input = server.readInput();

        //Init game
        //String[] init = {"AH", "KH", "QH", "JH", "10H", "9H", "8H"};
        //ISolitaireState startState = ClosedSolitaireState.newGameFromInput(init);
        ISolitaireState startState = ClosedSolitaireState.newGameFromInput(input.split(" "));
        solitaire.initClosedGame(startState);

        PrintGameState pgs = new PrintGameState();

        pgs.initClosedSolitareState(startState);
        pgs.printCurrentState();

        //While game is not over
/*        while (!solitaire.isGameWon() && !solitaire.isGameLost()){
            //Get moveMsg
            String moveMsg = solitaire.findNextMoveClosedGame().formatGuiMoveMsg(solitaire.isUnkownCard());

            //Write to GUI: unknownCard;move
            server.writeOutput(moveMsg);

            //Read input from camera: String with array of cards, single card or nothing
            input = server.readInput();

            //Update game from input
            solitaire.updateClosedGame(input);
        }*/


    }
}
