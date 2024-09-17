package com.example.seabattle;

import com.example.seabattle.Controllers.HomeController;
import com.example.seabattle.Controllers.PlacementController;
import com.example.seabattle.Modules.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        GameManager gameManager = new GameManager();

        FXMLLoader fxmlLoaderHome = new FXMLLoader(Application.class.getResource("home-view.fxml"));
        FXMLLoader fxmlLoaderPlacement = new FXMLLoader(Application.class.getResource("placement-view.fxml"));
        FXMLLoader fxmlLoaderGameOffline = new FXMLLoader(Application.class.getResource("gameOffline-view.fxml"));
        FXMLLoader fxmlLoaderGameOnline = new FXMLLoader(Application.class.getResource("gameOnline-view.fxml"));

        fxmlLoaderHome.setControllerFactory(type -> new HomeController(fxmlLoaderPlacement, fxmlLoaderGameOnline));
        fxmlLoaderPlacement.setControllerFactory(type -> new PlacementController(gameManager, fxmlLoaderHome, fxmlLoaderGameOffline));

        Scene scene = new Scene(fxmlLoaderHome.load(), 600, 400);
        stage.setTitle("Sea battle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}