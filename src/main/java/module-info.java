module com.example.dicerollerproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.jdi;
    requires java.sql;

    opens com.example.dicerollerproject to javafx.fxml;
    exports com.example.dicerollerproject;
}
