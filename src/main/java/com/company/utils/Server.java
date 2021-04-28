package com.company.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws IOException {
        String fromclient;

        ServerSocket Server = new ServerSocket(5000);

        System.out.println("TCPServer Waiting for client on port 5000");

        while (true) {
            Socket connected = Server.accept();
            System.out.println(" THE CLIENT" + " " + connected.getInetAddress() + ":" + connected.getPort() + " IS CONNECTED ");

            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connected.getInputStream()));

            while (true) {
                fromclient = inFromClient.readLine(); //Læser "startopstilling fra python

                //bygge startopstillingen vha logikken

                //finde det bedste træk

                //returnere en string med det bedste træk

                //lukker socket
                if (fromclient.equals("q") || fromclient.equals("Q")) {
                    connected.close();
                    break;
                } else {
                    String[] cardArray = fromclient.split(" ");
                    System.out.println("RECIEVED: " + Arrays.toString(cardArray));
                    
                }
            }
        }
    }
}
