package com.example.seabattle.Controllers;

import com.example.seabattle.Modules.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameOfflineController {
    GameManager gameManager;
    @FXML
    private GridPane myGridPane;
    @FXML
    private GridPane opponentGridPane;
    Random random = new Random();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    public GameOfflineController(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    public void setGridText(GridPane gridPane) {

        for (Ship ship : gameManager.me.ships)
            for (ShipCoordinates shipCoordinates : ship.coordinates) {
                Node node = gridPane.getChildren().get(shipCoordinates.x * 10 + shipCoordinates.y);
                Text text = (Text) node;
                text.setText("X");
                text.setFill(Color.BLUE);
                text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
    }
    @FXML
    void shoot(ActionEvent event) {

        Button button = (Button)event.getSource();
        Integer row = GridPane.getRowIndex(button);
        Integer col = GridPane.getColumnIndex(button);
        if (row == null) {
            row = 0;
        }
        if (col == null) {
            col = 0;
        }
        Shot shot = new Shot();
        shot.x = row;
        shot.y = col;

        System.out.println("\nrow =  = " + shot.x + " col = " + shot.y);

        gameManager.me.Shoot(shot, gameManager.computer.IsHeat(shot));
        gameManager.me.output();

        if (gameManager.me.Shoot(shot, gameManager.computer.IsHeat(shot))) {
            updateOpponentGridPane();
            System.out.println("Вы попали!");
        }
        else {
            updateOpponentGridPane();
            System.out.println("Вы промахнулись!");

            //disableAllButtons(true);

            shootComputer(gameManager.computer.shots.remove(random.nextInt(gameManager.computer.shots.size())));
        }
        if (gameManager.computer.ships.stream().allMatch(ship -> ship.isHit)) System.out.println("Вы выиграли!!!");
        if (gameManager.me.ships.stream().allMatch(ship -> ship.isHit)) System.out.println("Вы проиграли!(((");
    }
    void shootComputer(Shot shot) {
        System.out.println("\nrow =  = " + shot.x + " col = " + shot.y);

        gameManager.computer.Shoot(shot, gameManager.me.IsHeat(shot));
        gameManager.me.output();

        if (gameManager.computer.Shoot(shot, gameManager.me.IsHeat(shot))) {
            System.out.println("Он вас подбили!");
            updateMyGridPane(shot, "X", Color.RED, false, () -> {
                shootComputer(gameManager.computer.shots.remove(random.nextInt(gameManager.computer.shots.size())));
            });
        } else {
            System.out.println("Он промахнулся!");
            updateMyGridPane(shot, "•", Color.BLACK, true, () -> {
            });
        }
    }
    void updateMyGridPane(Shot shot, String symbol, Color color, boolean disableButtons, Runnable onAnimationFinished) {
        Color color1 = Color.BLACK;
        for (Node node : myGridPane.getChildren()) {
            if (node instanceof Text) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);

                if (rowIndex == null) rowIndex = 0;
                if (colIndex == null) colIndex = 0;
                char symbol1 = gameManager.me.battleField.battleField.get(rowIndex).get(colIndex);
                switch (symbol1) {
                    case '1' :
                        symbol1 = 'X';
                        color1 = Color.BLUE;
                        break;
                    case '2' :
                        symbol1 = 'X';
                        color1 = Color.RED;
                        break;
                    case '0' :
                        symbol1 = '•';
                        color1 = Color.BLACK;
                        break;
                    case '-' :
                        symbol1 = '•';
                        color1 = Color.GRAY;
                        break;
                    case '*' :
                        symbol1 = ' ';
                }
                //Node node = myGridPane.getChildren().get(shot.x * 10 + shot.y);
//                Text text = (Text) node;
//                char finalSymbol = symbol1;
//                Color finalColor = color1;
//                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
//                    text.setText(String.valueOf(finalSymbol));
//                    text.setFill(finalColor);
//                    text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
//                }));
//                timeline.setOnFinished(e -> {
//                    if (disableButtons) {
//                        disableAllButtons(false);
//                    }
//                    onAnimationFinished.run();
//                });
//                timeline.play();
                ((Text) node).setText(String.valueOf(symbol1));
                ((Text) node).setFill(color1);
                ((Text) node).setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
        }
    }
    void updateOpponentGridPane() {
        Color color = Color.BLACK;
        for (Node node : opponentGridPane.getChildren()) {
            if (node instanceof Button) {
                Integer rowIndex = GridPane.getRowIndex(node);
                Integer colIndex = GridPane.getColumnIndex(node);

                if (rowIndex == null) rowIndex = 0;
                if (colIndex == null) colIndex = 0;
                char symbol = gameManager.me.opponentField.battleField.get(rowIndex).get(colIndex);
                    switch (symbol) {
                        case '1' :
                            symbol = 'X';
                            color = Color.RED;
                            break;
                        case '0' :
                            symbol = '•';
                            color = Color.BLACK;
                            break;
                        case '-' :
                            symbol = '•';
                            color = Color.GRAY;
                            break;
                        case '?' :
                            symbol = ' ';
                    }
                    ((Button) node).setText(String.valueOf(symbol));
                    ((Button) node).setTextFill(color);
                    ((Button) node).setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
        }
    }
    void disableAllButtons(boolean value) {
        for (Node node : opponentGridPane.getChildren()) {
            if (node instanceof Button button) {
                button.setDisable(value);
            }
        }
    }
    @FXML
    void initialize() {
        setGridText(myGridPane);
    }
}
