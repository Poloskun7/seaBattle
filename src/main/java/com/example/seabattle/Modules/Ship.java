package com.example.seabattle.Modules;

import java.util.ArrayList;

public class Ship {
    public int length = 0;
    public boolean isHit = false;
    public ArrayList<ShipCoordinates> coordinates = new ArrayList<>();
    public Ship(int length) {
        this.length = length;
    }

    public void OutPut() {
        for (ShipCoordinates el : coordinates) {
            System.out.println("first = " + el.x + "   second = " + el.y +
                    "   length = " + length + "   arraySize = " + coordinates.size());
        }
    }
}
