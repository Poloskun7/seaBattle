package com.example.seabattle.Controllers;

import com.example.seabattle.Modules.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class PlacementController {
    GameManager gameManager;
    FXMLLoader fxmlLoaderHome;
    FXMLLoader fxmlLoaderGameOffline;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button buttonNext;
    @FXML
    private Button buttonBack;

    public PlacementController(GameManager gameManager, FXMLLoader fxmlLoaderHome, FXMLLoader fxmlLoaderGameOffline) {
        this.gameManager = gameManager;
        this.fxmlLoaderHome = fxmlLoaderHome;
        this.fxmlLoaderGameOffline = fxmlLoaderGameOffline;
    }

    public void updateGridText() {
        clearGridPane();
        for (Ship ship : gameManager.me.ships)
            for (ShipCoordinates shipCoordinates : ship.coordinates) {
                Node node = gridPane.getChildren().get(shipCoordinates.x * 10 + shipCoordinates.y);
                Text text = (Text) node;
                text.setText("X");
                text.setFill(Color.BLUE);
                text.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            }
        gameManager.me.output();
        System.out.println();
        gameManager.computer.output();
    }
    void clearGridPane() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Node node = gridPane.getChildren().get(i * 10 + j);
                Text text = (Text) node;
                text.setText("");
            }
        }
    }
    @FXML
    void Regenerate() {
        gameManager.generateShips();
        updateGridText();
    }

    @FXML
    void next() {

        fxmlLoaderGameOffline.setControllerFactory(type -> new GameOfflineController(gameManager));

        buttonNext.getScene().getWindow().hide();

        try {
            fxmlLoaderGameOffline.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoaderGameOffline.getRoot(), 820, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    void back() {
//        buttonBack.getScene().getWindow().hide();
//
//        //FXMLLoader loader = new FXMLLoader();
//        //fxmlLoader.setLocation(getClass().getResource("/com/example/seabattle/play-view.fxml"));
//
//        try {
//            fxmlLoaderHome.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        Stage stage = new Stage();
//        Scene scene = new Scene(fxmlLoaderHome.getRoot(), 600, 400);
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
    }

    @FXML
    void initialize() {
        updateGridText();
    }
}
