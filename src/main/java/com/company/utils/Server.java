package com.company.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server {

    static Thread sent;
    static Thread receive;
    static Socket socket;

    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 9999);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        sent = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    BufferedReader stdIn =
                            new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    while (true) {
                        String in = stdIn.readLine();
                        System.out.println(in);
                        out.print("Message to python from java" + "\r\n");
                        out.flush();
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
}