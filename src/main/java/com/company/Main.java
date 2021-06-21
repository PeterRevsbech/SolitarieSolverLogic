
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company;

import com.company.logic.Solitaire;
import com.company.models.SpecificMove;
import com.company.models.exceptions.SolitarieException;
import com.company.models.states.ClosedSolitaireState;
import com.company.models.states.ISolitaireState;
import com.company.utils.Client;
import com.company.utils.FakeClient;
import com.company.utils.IClient;


import java.io.IOException;

public class Main {

    private static final String END_GAME = "END_GAME";
    private static final String GAME_WON = "GAME_WON";
    private static final String GAME_LOST = "GAME_LOST";
    private static final String EXIT = "EXIT";
    private static boolean b = false;


    public static void main(String[] args) throws SolitarieException, IOException {
        //Init client and game-object
        //IClient client = new FakeClient();
        IClient client = new Client();
        Solitaire solitaire = new Solitaire();
        client.startClient();

        //Read start configuration from GUI
        String input = client.readInput();
        if (input.contains(EXIT)) {
            client.closeConnection();
            System.exit(0);
        }

        //Init game
        ISolitaireState startState = ClosedSolitaireState.newGameFromInput(input.split(" "));
        solitaire.initClosedGame(startState, -1, 100);

        //While game is not over
        while (!solitaire.isGameWon() && !solitaire.isGameLost()) {

            //When New Game is pressed, we need to read new input to reset states. Ask Mads or Sebastian if in doubt :)
            if (b) {
                //Read start configuration from GUI
                input = client.readInput();

                //Init game
                startState = ClosedSolitaireState.newGameFromInput(input.split(" "));
                solitaire.initClosedGame(startState, -1, 100);
                b = false;
            }

            //Get moveMsg
            solitaire.makeNextMove();
            SpecificMove move = solitaire.getNextMove();
            String wonOrLost = "";



            if (solitaire.isGameWon()) {
                wonOrLost = GAME_WON;
            } else if (solitaire.isGameLost()) {
                wonOrLost = GAME_LOST;
            }

            ISolitaireState previousState;
            if (solitaire.getStates().size() == 1) {
                previousState = null;
            } else {
                previousState = solitaire.getStates().get(solitaire.getStates().size() - 2);
            }

            boolean unkownCard = solitaire.isUnkownCard();
            int turnsPlayed = solitaire.getTurnsPlayed();
            String moveMsg = client.formatGuiMoveMsg(previousState, move, unkownCard, wonOrLost, turnsPlayed);

            //Write to GUI: unknownCard;move
            client.writeOutput(moveMsg);

            //Read next request from client
            input = client.readInput();

            //-----------------------Read next request from client----------------------
            if (input.contains(END_GAME)) {//If client wants to end game
                //Restart everything
                solitaire = new Solitaire();
                //Init game
                //startState = ClosedSolitaireState.newGameFromInput(input.split(" "));
                b = true;
                //solitaire.initClosedGame(startState,-1,100);
                continue;
            }
            if (input.contains(EXIT)) {
                System.exit(0);
            }

            if (solitaire.isUnkownCard()) { //If we asked for unknown card - read it
                String cardString = extractUnknownCard(input);
            } else { //If we didn't ask for new card - assert that empty message was sent
                if (!input.equals(Solitaire.NO_CARD)) {
                    throw new IOException("Unexpected answer from Python-Client. Expected Empty message but recieved:" + input);
                }
            }

            //Update game from input
            solitaire.updateClosedGame(input);
        }
    }

    static String extractUnknownCard(String msg) {
        return msg;
    }
}
