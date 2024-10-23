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

public class HistoryController {

    @FXML private Button closeButton;
    @FXML private Button exportButton;
    @FXML private ListView<String> historyListView;

    @FXML
    public void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> refreshHistory()));
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
        timeline.play(); // Start the timeline

        // Initial fetch of the history data
        refreshHistory();

    }

    public void refreshHistory() {

        List<Roll> rolls = DatabaseConnection.getAllRolls();

   
        historyListView.getItems().clear();
        Collections.reverse(rolls); // Reverse the order of the rolls so the newest is at the top
     
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
        DatabaseConnection.clearAllRolls();

        historyListView.getItems().clear();
        System.out.println("History cleared.");
    }

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