package com.example.seabattle.Modules;

import java.util.ArrayList;
import java.util.Arrays;

public class GameManager {
    public Player me = new Player();
    public Player computer = new Player();
    public void generateShips() {
        me.ships = me.LocationOfTheShips(new ArrayList<>(Arrays.asList(new Ship(4), new Ship(3), new Ship(3),
                new Ship(2), new Ship(2), new Ship(2), new Ship(1), new Ship(1), new Ship(1), new Ship(1))));
        me.battleField.fill(me.ships);
        me.output();
    }
}