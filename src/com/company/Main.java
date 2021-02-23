package com.company;

import com.company.model.CardDeque;
import com.company.model.SolitaireState;

public class Main {

    public static void main(String[] args) {
        SolitaireState newgame = SolitaireState.newGame();
        System.out.println(newgame.toString());

    }
}
