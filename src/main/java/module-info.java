module com.example.dicerollerproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.dicerollerproject to javafx.fxml;
    exports com.example.dicerollerproject;
}