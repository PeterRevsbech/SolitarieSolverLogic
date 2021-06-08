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


    public static void main(String[] args) throws SolitarieException, IOException {
        //Init client and game-object
        //IClient client = new FakeClient();
        IClient client = new Client();
        Solitaire solitaire = new Solitaire();
        client.startClient();

        //Read start configuration from GUI
        String input = client.readInput();

        //Init game
        ISolitaireState startState = ClosedSolitaireState.newGameFromInput(input.split(" "));
        solitaire.initClosedGame(startState,-1,100);

        //While game is not over
        while (!solitaire.isGameWon() && !solitaire.isGameLost()){

            //Get moveMsg
            solitaire.makeNextMove();
            SpecificMove move = solitaire.getNextMove();
            String moveMsg = move.formatGuiMoveMsg(solitaire.isUnkownCard());

            //Write to GUI: unknownCard;move
            client.writeOutput(moveMsg);

            //Read next request from client
            input = client.readInput();


            //-----------------------Read next request from client----------------------
            if (input.contains(END_GAME)){//If client wants to end game
                System.exit(0);
            }

            if (solitaire.isUnkownCard()){ //If we asked for unknown card - read it
                String cardString = extractUnknownCard(input);
            } else { //If we didn't ask for new card - assert that empty message was sent
                if (!input.equals(Solitaire.NO_CARD)){
                    throw new IOException("Unexpected answer from Python-Client. Expected Empty message but recieved:" + input);
                }
            }

            //Update game from input
            solitaire.updateClosedGame(input);
        }
    }

    static String extractUnknownCard(String msg){
        return msg;
    }

}
