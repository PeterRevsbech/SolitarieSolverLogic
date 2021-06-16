
// Gruppe 12 //
//Christian Kyed - s184210
//Ida Schrader - s195483
//Mads Storgaard-Nielsen - s180076
//Marie Seindal - s185363
//Peter Revsbech - s183760
//Sebastian Bjerre - s163526

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
