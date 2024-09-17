module com.example.seabattle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.seabattle to javafx.fxml;
    exports com.example.seabattle;
    exports com.example.seabattle.Controllers;
    opens com.example.seabattle.Controllers to javafx.fxml;
}