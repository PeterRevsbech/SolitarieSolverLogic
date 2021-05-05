package com.company;

import com.company.logic.Solitaire;
import com.company.models.SpecificMove;
import com.company.models.exceptions.SolitarieException;
import com.company.models.states.ClosedSolitaireState;
import com.company.models.states.ISolitaireState;
import com.company.utils.FakeServer;
import com.company.utils.IServer;
import com.company.utils.PrintGameState;
import com.company.utils.Server;

public class Main {
    public static void main(String[] args) throws SolitarieException {
        //Init server and game-object
        IServer server = new FakeServer();
        Solitaire solitaire = new Solitaire();
        server.startServer();

        //Read start configuration from GUI
        String input = server.readInput();

        //Init game
        //String[] init = {"AH", "KH", "QH", "JH", "10H", "9H", "8H"};
        //ISolitaireState startState = ClosedSolitaireState.newGameFromInput(init);
        ISolitaireState startState = ClosedSolitaireState.newGameFromInput(input.split(" "));
        solitaire.initClosedGame(startState,-1,100);

        //While game is not over
        while (!solitaire.isGameWon() && !solitaire.isGameLost()){

            //Get moveMsg
            solitaire.makeNextMove();
            SpecificMove move = solitaire.getNextMove();
            String moveMsg = move.formatGuiMoveMsg(solitaire.isUnkownCard());

            //Write to GUI: unknownCard;move
            server.writeOutput(moveMsg);

            //Read input from camera: String with array of cards, single card or nothing
            input = server.readInput();

            //Update game from input
            solitaire.updateClosedGame(input);
        }


    }
}
