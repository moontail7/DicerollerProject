package com.example.dicerollerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.List;

public class HistoryController {

    public static class Roll {
        private int id;
        private int rollValue;
        private String timestamp;

        public Roll(int id, int rollValue, String timestamp) {
            this.id = id;
            this.rollValue = rollValue;
            this.timestamp = timestamp;
        }

        public int getId() {
            return id;
        }

        public int getRollValue() {
            return rollValue;
        }

        public String getTimestamp() {
            return timestamp;
        }
    }

    @FXML
    private Button closeButton;

    @FXML
    private ListView<String> historyListView; // Link this to your FXML ListView

    @FXML
    public void initialize() {
        // Fetch rolls from the database
        List<Roll> rolls = DatabaseConnection.getAllRolls();

        // Populate the ListView with roll data
        for (Roll roll : rolls) {
            String displayText = "Roll ID: " + roll.getId() + ", Value: " + roll.getRollValue() + ", Timestamp: " + roll.getTimestamp();
            historyListView.getItems().add(displayText);
        }
    }

    @FXML
    public void closeHistoryWindow(ActionEvent event) {
        // Close the history window
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}