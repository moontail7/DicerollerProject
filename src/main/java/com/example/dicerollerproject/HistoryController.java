package com.example.dicerollerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SortEvent;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.util.Collections;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * The HistoryController class handles displaying and managing the roll history.
 * It interacts with the ListView to show past rolls, allows exporting roll history
 * to a CSV file, and provides functionality to clear all history.
 */
public class HistoryController {

    @FXML private Button closeButton;
    @FXML private Button exportButton;
    @FXML private ListView<String> historyListView;

    /**
     * Initializes the controller and sets up the refresh timeline.
     * Refreshes the roll history every 5 seconds indefinitely.
     */
    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> refreshHistory()));
        timeline.setCycleCount(Timeline.INDEFINITE); 
        timeline.play(); // Start the timeline

        // Initial fetch of the history data
        refreshHistory();
    }

    /**
     * Helper function to refresh the history ListView.
     * This method fetches the roll history from the database, reverses the order
     * (so the newest roll appears first), and updates the ListView with the roll details.
     */
    public void refreshHistory() {
        List<Roll> rolls = DatabaseConnection.getAllRolls();
        historyListView.getItems().clear();
        Collections.reverse(rolls); // Reverse the order of the rolls so the newest is at the top
     
        for (Roll roll : rolls) {
            String displayText = "Roll ID: " + roll.getId() + ", Value: " + roll.getRollValue() + ", Timestamp: " + roll.getTimestamp();
            historyListView.getItems().add(displayText);
        }
    }

    /**
     * Handles the action of closing the history window.
     * This method is triggered by the "Close" button.
     * 
     * @param event The action event triggered when the button is clicked.
     */
    @FXML public void closeHistoryWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Inner class representing a Roll.
     * A Roll consists of an ID, value, and timestamp.
     */
    public static class Roll {
        private int id;
        private int rollValue;
        private String timestamp;

        /**
         * Constructor for creating a Roll object.
         * 
         * @param id The roll's ID.
         * @param rollValue The value of the roll.
         * @param timestamp The timestamp of when the roll occurred.
         */
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

    @FXML private Button clearAllButton;

    /**
     * Handles the action of clearing all roll history data from the database.
     * This method is triggered by the "Clear All" button.
     * 
     * @param event The action event triggered when the button is clicked.
     */
    @FXML
    public void clearAllData(ActionEvent event) {
        DatabaseConnection.clearAllRolls();
        historyListView.getItems().clear();
        System.out.println("History cleared.");
    }

    /**
     * Handles the action of exporting the roll history to a CSV file.
     * This method is triggered by the "Export" button.
     * 
     * @param event The action event triggered when the button is clicked.
     */
    @FXML
    public void ExportData(ActionEvent event) {
        List<Roll> rolls = DatabaseConnection.getAllRolls();
        String csvFile = "exported_rolls.csv";

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append("Roll ID, Roll Value, Timestamp\n");
            for (Roll roll : rolls) {
                writer.append(roll.getId() + "," + roll.getRollValue() + "," + roll.getTimestamp() + "\n");
            }
            System.out.println("Data successfully exported to " + csvFile);
        } catch (IOException e) {
            System.out.println("Error exporting data: " + e.getMessage());
        }
    }
}
