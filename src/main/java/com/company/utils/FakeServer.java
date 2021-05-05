package com.company.utils;

import java.util.Scanner;

public class FakeServer implements IServer{

    Scanner scanner;

    @Override
    public String readInput() {
        return scanner.nextLine();
    }

    @Override
    public void writeOutput(String moveMsg) {
        System.out.println(moveMsg);
    }

    @Override
    public void startServer() {
        scanner = new Scanner(System.in);
    }
}
