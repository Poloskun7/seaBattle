package com.example.seabattle.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController {

    FXMLLoader fxmlLoaderPlacement;
    FXMLLoader fxmlLoaderGameOnline;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button buttonPlay;
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;
    ToggleGroup toggleGroup = new ToggleGroup();

    public HomeController(FXMLLoader fxmlLoaderPlacement, FXMLLoader fxmlLoaderGameOnline) {
        this.fxmlLoaderPlacement = fxmlLoaderPlacement;
        this.fxmlLoaderGameOnline = fxmlLoaderGameOnline;
    }

    @FXML
    void play(ActionEvent event) {
        buttonPlay.getScene().getWindow().hide();

        if (toggleGroup.getSelectedToggle() == radioButton1) {
            openNewWindow(fxmlLoaderPlacement);
        } else if (toggleGroup.getSelectedToggle() == radioButton2) {
            openNewWindow(fxmlLoaderGameOnline);
        }
    }
        private void openNewWindow(FXMLLoader fxmlLoader) {
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.getRoot(), 600, 400);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

    @FXML
    void initialize() {
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
    }
}
