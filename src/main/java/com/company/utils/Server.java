package com.company.utils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {

    public static void main(String[] args) throws IOException {
        String fromclient;

        ServerSocket server_socket = new ServerSocket(5000);

        System.out.println("TCPServer Waiting for client on port 5000");

        while (true) {
            Socket client_socket = server_socket.accept();
            PrintWriter output = new PrintWriter(client_socket.getOutputStream(),true);
            BufferedReader input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));

            System.out.println(" THE CLIENT" + " " + client_socket.getInetAddress() + ":" + client_socket.getPort() + " IS CONNECTED ");

            while (true) {
                fromclient = input.readLine(); //Læser "startopstilling fra python

                //bygge startopstillingen vha logikken

                //finde det bedste træk

                //returnere en string med det bedste træk

                //lukker socket
                if (fromclient.equals("q") || fromclient.equals("Q")) {
                    client_socket.close();
                    break;
                } else {
                    String[] cardArray = fromclient.split(" ");
                    System.out.println("RECIEVED: " + Arrays.toString(cardArray));
                }
            }
        }
    }
}
