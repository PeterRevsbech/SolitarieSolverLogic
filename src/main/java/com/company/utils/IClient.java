package com.company.utils;

import com.company.models.SpecificMove;
import com.company.models.states.ISolitaireState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public interface IClient {

    public String readInput();

    //method used to send a string to the  client
    public void writeOutput(String moveMsg);

    public void startClient();

    public default String formatGuiMoveMsg(ISolitaireState state, SpecificMove move, boolean unkownCard, String wonOrLost, int turnsPlayed){
        return move.detailedToString(state) +";"+ unkownCard + ";" + wonOrLost+";"+ turnsPlayed;
    }

}
