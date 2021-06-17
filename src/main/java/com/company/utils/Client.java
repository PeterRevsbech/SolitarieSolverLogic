
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

package com.company.utils;

import com.company.models.SpecificMove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements IClient {

    private Thread sent;
    private Thread receive;
    private Socket socket;

    private final int PORT = 9999;
    private final String HOST = "localhost";

    private BufferedReader stdIn;
    private PrintWriter stdOut;

    public String readInput() {
        String input = "null";
        try {
            stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("READING INPUT ERROR");
        }
        System.out.println("Python input " + input);
        return input;
    }

    //method used to send a string to the python client
    public void writeOutput(String moveMsg) {
        sent = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    stdOut = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stdOut.println(moveMsg);
                stdOut.flush();
            }
        });
        sent.start();
        try {
            System.out.println("Next move: " + moveMsg);
            sent.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startClient() {
        try {
            socket = new Socket(HOST, PORT); //Creates a new socket
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}