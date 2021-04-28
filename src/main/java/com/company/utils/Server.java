package com.company.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Server {

    static Thread sent;
    static Thread receive;
    static Socket socket;

    public static void main(String args[]){
        try {
            socket = new Socket("localhost",9999);
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        sent = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    BufferedReader stdIn =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    while(true){
                        System.out.println("Trying to read...");
                        String in = stdIn.readLine();
                        System.out.println(in);
                        out.print("Try"+"\r\n");
                        out.flush();
                        System.out.println("Message sent");
                    }

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
/* // try nr. 1
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

 */
}
