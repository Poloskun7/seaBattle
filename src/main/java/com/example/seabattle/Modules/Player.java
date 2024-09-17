package com.example.seabattle.Modules;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Player {
    public BattleField battleField = new BattleField('*');
    public BattleField opponentField = new BattleField('?');
    public ArrayList<Ship> ships = new ArrayList<>();
    public ArrayList<Shot> shots = new ArrayList<>();
    public Player() {

        ships = LocationOfTheShips(new ArrayList<>(Arrays.asList(new Ship(4), new Ship(3), new Ship(3),
                new Ship(2), new Ship(2), new Ship(2), new Ship(1), new Ship(1), new Ship(1), new Ship(1))));

        battleField.fill(ships);

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/com/example/seabattle/shots.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] numbers = line.split(" ");
                Shot shot = new Shot();
                shot.x = Integer.parseInt(numbers[0]);
                shot.y = Integer.parseInt(numbers[1]);
                shots.add(shot);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
    public ArrayList<Ship> LocationOfTheShips(ArrayList<Ship> ships) {

        ArrayList<Ship> newShips = new ArrayList<>();

            for (Ship ship : ships) {
                ship.coordinates = GenerateRandomShip(newShips, ship);
                newShips.add(ship);

                System.out.println("len = " + ship.length);
                for (ShipCoordinates shipCoordinates : ship.coordinates) {
                    System.out.println("x = " + shipCoordinates.x + " y = " + shipCoordinates.y);
                }

                System.out.println();
            }
            return newShips;
        }

        public ArrayList<ShipCoordinates> GenerateRandomShip(ArrayList<Ship> ships, Ship ship) {

            ArrayList<ShipCoordinates> coordinates = new ArrayList<>();

            ShipCoordinates shipCoordinates = GenerateRandomCoordinates();

            Random random = new Random();

            int direction = random.nextInt(4);

            while (coordinates.size() < ship.length) {
                if (CheakShipCoordinates(ships, shipCoordinates)) {
                ShipCoordinates newShipCoordinates = new ShipCoordinates();
                newShipCoordinates.x = shipCoordinates.x;
                newShipCoordinates.y = shipCoordinates.y;
                coordinates.add(newShipCoordinates);

                    switch (direction) {
                        case 0:
                            shipCoordinates.x++;
                            break;
                        case 1:
                            shipCoordinates.x--;
                            break;
                        case 2:
                            shipCoordinates.y++;
                            break;
                        case 3:
                            shipCoordinates.y--;
                            break;
                    }
                }
                else {
                    coordinates.clear();
                    shipCoordinates = GenerateRandomCoordinates();
                }
            }

            return coordinates;
        }
        public ShipCoordinates GenerateRandomCoordinates() {
            Random random = new Random();
            int max = 10;
            int x = random.nextInt(max);
            int y = random.nextInt(max);
            ShipCoordinates shipCoordinates = new ShipCoordinates();
            shipCoordinates.x = x;
            shipCoordinates.y = y;
            return  shipCoordinates;
        }
        public boolean CheakShipCoordinates(ArrayList<Ship> ships, ShipCoordinates shipCoordinates) {
            return (shipCoordinates.x > 0 &&
                    shipCoordinates.x < 10 &&
                    shipCoordinates.y > 0 &&
                    shipCoordinates.y < 10) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x == shipCoordinates.x && coordinates.y == shipCoordinates.y))) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x + 1 == shipCoordinates.x && coordinates.y == shipCoordinates.y))) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x - 1 == shipCoordinates.x && coordinates.y == shipCoordinates.y))) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x == shipCoordinates.x && coordinates.y + 1 == shipCoordinates.y))) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x == shipCoordinates.x && coordinates.y - 1 == shipCoordinates.y))) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x + 1 == shipCoordinates.x && coordinates.y + 1 == shipCoordinates.y))) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x - 1 == shipCoordinates.x && coordinates.y - 1 == shipCoordinates.y))) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x + 1 == shipCoordinates.x && coordinates.y - 1 == shipCoordinates.y))) &&
                    (ships.stream().noneMatch(ship -> ship.coordinates.stream().anyMatch(coordinates ->
                            coordinates.x - 1 == shipCoordinates.x && coordinates.y + 1 == shipCoordinates.y)));
        }
    public boolean Shoot(Shot shot, CheckShip hit) {
        if (hit.getValue()) {

            opponentField.replaceValue(shot.x, shot.y, '1');

            if (shots.removeIf(shot1 -> shot1.x == shot.x + 1 && shot1.y == shot.y + 1)) {
                shots.removeIf(shot1 -> shot1.x == shot.x + 1 && shot1.y == shot.y + 1);
            }
            if (shots.removeIf(shot1 -> shot1.x == shot.x - 1 && shot1.y == shot.y - 1)) {
                shots.removeIf(shot1 -> shot1.x == shot.x - 1 && shot1.y == shot.y - 1);
            }
            if (shots.removeIf(shot1 -> shot1.x == shot.x + 1 && shot1.y == shot.y - 1)) {
                shots.removeIf(shot1 -> shot1.x == shot.x + 1 && shot1.y == shot.y - 1);
            }
            if (shots.removeIf(shot1 -> shot1.x == shot.x - 1 && shot1.y == shot.y + 1)) {
                shots.removeIf(shot1 -> shot1.x == shot.x - 1 && shot1.y == shot.y + 1);
            }

            opponentField.replaceValuesAtCorners(shot.x, shot.y, '-');

            if (!hit.checkedShots.isEmpty()) {
                for (Shot shot1 : hit.checkedShots) {

                    if (shots.removeIf(shot2 -> shot2.x == shot1.x + 1 && shot2.y == shot1.y)) {
                        shots.removeIf(shot2 -> shot2.x == shot1.x + 1 && shot2.y == shot1.y);
                    }
                    if (shots.removeIf(shot2 -> shot2.x == shot1.x - 1 && shot2.y == shot1.y)) {
                        shots.removeIf(shot2 -> shot2.x == shot1.x - 1 && shot2.y == shot1.y);
                    }
                    if (shots.removeIf(shot2 -> shot2.x == shot1.x && shot2.y == shot1.y + 1)) {
                        shots.removeIf(shot2 -> shot2.x == shot1.x && shot2.y == shot1.y + 1);
                    }
                    if (shots.removeIf(shot2 -> shot2.x == shot1.x && shot2.y == shot1.y - 1)) {
                        shots.removeIf(shot2 -> shot2.x == shot1.x && shot2.y == shot1.y - 1);
                    }
                    opponentField.replaceValuesAround(shot1.x, shot1.y, '-');
                }
            }
            return true;
        }
        else {
            opponentField.replaceValue(shot.x, shot.y, '0');
            return false;
        }
    }
    public CheckShip IsHeat(Shot shot) {
        CheckShip checkShip = new CheckShip();

        for (Ship ship : ships)
            for (ShipCoordinates shipCoordinates : ship.coordinates)
                if (shipCoordinates.x == shot.x && shipCoordinates.y == shot.y) {
                    shipCoordinates.isHit = true;
                    checkShip.setValue(true);
                    battleField.replaceValue(shot.x, shot.y, '2');
                    battleField.replaceValuesAtCorners(shot.x, shot.y, '-');
                    if (ship.coordinates.stream().allMatch(shipCoordinates1 -> shipCoordinates1.isHit)) {
                        ship.isHit = true;
                        for (ShipCoordinates shipCoordinates1 : ship.coordinates) {
                            battleField.replaceValuesAround(shipCoordinates1.x, shipCoordinates1.y, '-');
                            checkShip.checkedShots.add(new Shot(shipCoordinates1.x, shipCoordinates1.y));
                        }
//                        checkShip.setValue(true);
                    }
                    return checkShip;
                }
        battleField.replaceValue(shot.x, shot.y, '0');
        checkShip.setValue(false);
        return checkShip;
    }
    public void output() {
        for (int i = 0; i < battleField.battleField.size(); i++) {
            System.out.println();
            for (int j = 0; j < battleField.battleField.getFirst().size(); j++) {
                int finalI = i;
                int finalJ = j;
                if (ships.stream().anyMatch(ship -> ship.coordinates.stream().anyMatch(ShipCoordinates ->
                        ShipCoordinates.x == finalI && ShipCoordinates.y == finalJ && ShipCoordinates.isHit)))
                    System.out.print("\u001B[31m" + battleField.battleField.get(i).get(j) + " " + "\u001B[0m");
                else if (battleField.battleField.get(i).get(j) == '0') System.out.print("\u001B[34m" + battleField.battleField.get(i).get(j) + " " + "\u001B[0m");
                else System.out.print(battleField.battleField.get(i).get(j) + " ");
            }
            System.out.print("\t");
            for (int k = 0; k < opponentField.battleField.size(); k++)
                System.out.print(opponentField.battleField.get(i).get(k) + " ");
        }
    }
}

