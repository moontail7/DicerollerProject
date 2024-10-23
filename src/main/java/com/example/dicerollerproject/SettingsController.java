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

/**
 * The SettingsController class manages the settings page, where users can update their
 * username and password. It interacts with the database to load and update user data.
 */
public class SettingsController {

    @FXML
    private TextField nameField, passField;

    @FXML
    private Button cancelButton, saveButton;

    @FXML
    private Label lblFeedback;

    // Database connection instance
    private final Connection connection = DatabaseConnection.getInstance();

    /**
     * Initializes the settings page by loading the logged-in user's data.
     * This method is called automatically when the settings page is opened.
     */
    @FXML
    public void initialize() {
        loadUserData();
    }

    /**
     * Handles the cancel button click event, which reverts any changes made on the form
     * and reloads the original user data.
     * 
     * @param event The ActionEvent triggered by the cancel button.
     */
    public void handleCancel(ActionEvent event) {
        lblFeedback.setText("Changes cancelled.");
        loadUserData();
    }

    /**
     * Loads the user's current username and password from the database and displays
     * them in the form. If there is no user logged in, a message is displayed.
     */
    private void loadUserData() {
        String username = UserSession.getInstance().getLoggedInUsername();  
        if (username != null) {
            String query = "SELECT loginUsername, loginPassword FROM loginAccountDetails WHERE loginUsername = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);  
                ResultSet rs = stmt.executeQuery();  
    
                if (rs.next()) {
                    // Disabled field display
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
    
    /**
     * Saves the updated username and password to the database. 
     * It checks for valid input before saving and also updates any custom rolls
     * associated with the user to reflect the new username.
     * 
     * @param event The ActionEvent triggered by the save button.
     */
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
                    // Update custom rolls tied to the original username
                    updateCustomRollsUsername(originalUsername, newUsername);
    
                    lblFeedback.setText("User data updated successfully!");
                    // Set the new username in session
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

    /**
     * Updates the username for all custom rolls associated with the user.
     * This ensures that custom rolls are retained when the username is changed.
     * 
     * @param oldUsername The original username before the update.
     * @param newUsername The new username to be updated in custom rolls.
     */
    private void updateCustomRollsUsername(String oldUsername, String newUsername) {
        String updateQuery = "UPDATE custom_rolls SET username = ? WHERE username = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(updateQuery)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
    
            int updatedRows = stmt.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Custom rolls updated to new username successfully.");
            } else {
                System.out.println("No custom rolls needed updating.");
            }
        } catch (SQLException e) {
            lblFeedback.setText("Error updating custom rolls: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Validates the username and password input fields to ensure they are not blank.
     * 
     * @param name The entered username.
     * @param pass The entered password.
     * @return true if both inputs are valid, false otherwise.
     */
    private boolean validateInput(String name, String pass) {
        if (name.isBlank() || pass.isBlank()) {
            lblFeedback.setText("Please fill in all fields.");
            return false;
        }
        return true;
    }
}
