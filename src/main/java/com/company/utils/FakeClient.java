package com.company.utils;

import java.util.Scanner;

public class FakeClient implements IClient {

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
    public void startClient() {
        scanner = new Scanner(System.in);
    }
}
