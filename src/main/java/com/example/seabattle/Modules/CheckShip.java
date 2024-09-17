package com.example.seabattle.Modules;

import java.util.ArrayList;

public class CheckShip {
    private boolean value = false;
    public ArrayList<Shot> checkedShots = new ArrayList<>();
    public boolean getValue() {
        return value;
    }
    public void setValue(boolean value) {
        this.value = value;
    }
}
