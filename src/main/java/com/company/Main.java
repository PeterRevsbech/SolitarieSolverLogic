package com.company;

import com.company.model.state.OpenSolitaireState;

public class Main {

    public static void main(String[] args) {
        OpenSolitaireState newgame = OpenSolitaireState.newGame();
        System.out.println(newgame.toString());

    }
}
