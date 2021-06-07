package com.company.utils;

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
}
