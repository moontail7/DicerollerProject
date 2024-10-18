package com.example.dicerollerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;



public class SettingsController {

    @FXML
    private TextField nameField, passField;

    @FXML
    private Button cancelButton, saveButton;

    @FXML
    private Label lblFeedback;

    private final Connection connection = DatabaseConnection.getInstance();

    @FXML
    public void initialize() {
        loadUserData();
    }

    public void handleCancel(ActionEvent event) {
        lblFeedback.setText("Changes cancelled.");
        loadUserData();
    }

    private void loadUserData() {
        String username = UserSession.getInstance().getLoggedInUsername();  
        if (username != null) {
            String query = "SELECT loginUsername, loginPassword FROM loginAccountDetails WHERE loginUsername = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);  
                ResultSet rs = stmt.executeQuery();  
    
                if (rs.next()) {
                  // to display content - disabled
                    // nameField.setText(rs.getString("loginUsername"));
                    // passField.setText(rs.getString("loginPassword"));
                }
            } catch (SQLException e) {
                lblFeedback.setText("Error loading user data: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            lblFeedback.setText("No user logged in.");
        }
    }
    
    public void saveUserData(ActionEvent event) {
        String newUsername = nameField.getText();  // New username from the form
        String newPassword = passField.getText();  // New password from the form
        String originalUsername = UserSession.getInstance().getLoggedInUsername();  // Original username to use in WHERE 
    
        // Validate input from login
        if (validateInput(newUsername, newPassword)) {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE loginAccountDetails SET loginUsername = ?, loginPassword = ? WHERE loginUsername = ?")) {
                stmt.setString(1, newUsername);
                stmt.setString(2, newPassword);
                stmt.setString(3, originalUsername); 
    
                int updatedRows = stmt.executeUpdate();
                if (updatedRows > 0) {
                    lblFeedback.setText("User data updated successfully!");
                    // give new username
                    UserSession.getInstance().setLoggedInUsername(newUsername);
                } else {
                    lblFeedback.setText("No changes were made.");
                }
    
            } catch (SQLException e) {
                lblFeedback.setText("Error updating user data: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    


    private boolean validateInput(String name, String pass) {
        if (name.isBlank() || pass.isBlank()) {
            lblFeedback.setText("Please fill in all fields.");
            return false;
        }
        return true;
    }
}
