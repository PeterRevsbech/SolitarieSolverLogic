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

/*    public static void main(String[] args) {
        //For at oprette forbindelse til python laves en client, der kaldes start client.
        //man kan derefter læse input vha. readInput og skrive til python vha writeOutput
        Client client = new Client();
        client.startClient();
        System.out.println(client.readInput());
        client.writeOutput("FRA JAVA 1");
        System.out.println(client.readInput());
        client.writeOutput("FRA JAVA 2");
        System.out.println(client.readInput());
        client.writeOutput("FRA JAVA 3");
    }*/

    public String readInput() {
        String input = "null";
        try {
            stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            input = stdIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("READING INPUT ERROR");
        }
        System.out.println("Python input "+input);
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
            System.out.println("Next move: "+moveMsg);
            sent.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void startClient() {
        try {
            socket = new Socket(HOST, PORT); //Creates a new socket
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }


}