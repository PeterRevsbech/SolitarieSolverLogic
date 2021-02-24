package com.company.model;

import java.util.Arrays;

public class Tableau {

    private Pile[] piles = new Pile[7];

    public Tableau(){
        for (int i = 0; i <7 ; i++) {
            piles[i] = new Pile();
            piles[i].setFanned(true);
        }
    }

    public Pile[] getPiles() {
        return piles;
    }

    public Pile getPile(int index) {
        return piles[index];
    }

    public void setPiles(Pile[] piles) {
        this.piles = piles;
    }

    @Override
    public String toString() {
        return Arrays.toString(piles);
    }
}
