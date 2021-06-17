
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.utils;

import com.company.models.SpecificMove;
import com.company.models.states.ISolitaireState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public interface IClient {

    String readInput();

    //method used to send a string to the  client
    void writeOutput(String moveMsg);

    void startClient();

    default String formatGuiMoveMsg(ISolitaireState state, SpecificMove move, boolean unkownCard, String wonOrLost, int turnsPlayed) {
        return move.detailedToString(state) + ";" + unkownCard + ";" + wonOrLost + ";" + turnsPlayed;
    }
}
