package org.example.domain;

public class Prisoner {
    private int morale = 100;

    public void receiveShouting() {
        this.morale -= 50;
    }

    public int getMorale() {
        return morale;
    }
}