package com.example.seabattle.Modules;

import java.util.ArrayList;

public class BattleField {
    final int BattleFieldSize = 10;
    public ArrayList<ArrayList<Character>> battleField = new ArrayList<>();
    public BattleField(Character el) {
        for (int i = 0; i < BattleFieldSize; i++) {
            ArrayList<Character> row = new ArrayList<>();
            for (int j = 0; j < BattleFieldSize; j++) {
                row.add(el);
            }
            battleField.add(row);
        }
    }
    public void fill(ArrayList<Ship> ships) {
        clearBattleField();

        for (Ship ship : ships)
            for (ShipCoordinates shipCoordinates : ship.coordinates)
                battleField.get(shipCoordinates.x).set(shipCoordinates.y, '1');
    }
    public void clearBattleField() {
        for (int i = 0; i < battleField.size(); i++)
            for (int j = 0; j < battleField.getFirst().size(); j++)
                battleField.get(i).set(j, '*');
    }
    public void replaceValue(int x, int y, Character newValue) {
        battleField.get(x).set(y, newValue);
    }
    public void replaceValuesAtCorners(int x, int y, Character newValue) {
        if (x > 0 && y > 0) {
            battleField.get(x - 1).set(y - 1, newValue);
        }
        if (x > 0 && y < battleField.get(0).size() - 1) {
            battleField.get(x - 1).set(y + 1, newValue);
        }
        if (x < battleField.size() - 1 && y > 0) {
            battleField.get(x + 1).set(y - 1, newValue);
        }
        if (x < battleField.size() - 1 && y < battleField.get(0).size() - 1) {
            battleField.get(x + 1).set(y + 1, newValue);
        }
    }
    public void replaceValuesAround(int x, int y, char newValue) {
        if (x > 0 && battleField.get(x - 1).get(y) != '1' && battleField.get(x - 1).get(y) != '2') {
            battleField.get(x - 1).set(y, newValue);
        }
        if (x < battleField.size() - 1 && battleField.get(x + 1).get(y) != '1' && battleField.get(x + 1).get(y) != '2') {
            battleField.get(x + 1).set(y, newValue);
        }
        if (y > 0 && battleField.get(x).get(y - 1) != '1' && battleField.get(x).get(y - 1) != '2') {
            battleField.get(x).set(y - 1, newValue);
        }
        if (y < battleField.get(0).size() - 1 && battleField.get(x).get(y + 1) != '1' && battleField.get(x).get(y + 1) != '2') {
            battleField.get(x).set(y + 1, newValue);
        }
    }
}
