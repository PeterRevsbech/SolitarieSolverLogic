package com.company.model;

import java.util.Arrays;

public class Foundation {

    private Pile[] piles = new Pile[4];

    public Foundation(){
        for (int i = 0; i < 4; i++) {
            piles[i] = new Pile();
        }
    }

    public Pile[] getPiles() {
        return piles;
    }

    public void setPiles(Pile[] piles) {
        this.piles = piles;
    }

    @Override
    public String toString() {
        return "Foundation{" +
                "piles=" + Arrays.toString(piles) +
                '}';
    }
}
