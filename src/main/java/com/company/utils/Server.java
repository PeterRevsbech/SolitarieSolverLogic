package com.company.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Server {

    static Thread sent;
    static Thread receive;
    static Socket socket;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 9999); //Creates a new socket
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        sent = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(socket.getInputStream())); //Gets the list of visible cards from python
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true); //printwriter is used to send messages back to python
                    String in = stdIn.readLine(); //saves the input in a string
                    //System.out.println(in);

                    //Byg et solitaire state ud fra de synlige kort

                    //beregn bedst mulige træk

                    //send trækket til python
                    out.print("Message to python from java" + "\r\n"); //sends a string to python containing the "best" move
                    out.flush();


                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        sent.start();
        try {
            sent.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //Hjælpe klasse til at returnere moves for at få tingene til at virke
    public String calcMoves() {
        int upperBound = 5;
        String[] str = {"AH -> KS","AH -> KS", "QH -> JS"};
        return str[new Random().nextInt(upperBound)];
    }
}