package com.example.dicerollerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SortEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.Collections;
import java.util.List;

public class HistoryController {

    @FXML private Button closeButton;
    @FXML private ListView<String> historyListView;

    @FXML
    public void initialize() {
        // Fetch rolls from the database
        List<Roll> rolls = DatabaseConnection.getAllRolls();

        // Reverse the list so the most recent roll is at the top
        Collections.reverse(rolls);

        // Populate the ListView with roll data
        for (Roll roll : rolls) {
            String displayText = "Roll ID: " + roll.getId() + ", Value: " + roll.getRollValue() + ", Timestamp: " + roll.getTimestamp();
            historyListView.getItems().add(displayText);
        }
    }

    @FXML public void closeHistoryWindow(ActionEvent event) {
        // Close the history window
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

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
    private Button clearAllButton;

    @FXML
    public void clearAllData(ActionEvent event) {
        // Call the method to clear all rolls from the database
        DatabaseConnection.clearAllRolls();

        // Clear the ListView after clearing the database
        historyListView.getItems().clear();

        System.out.println("History cleared.");
    }
}