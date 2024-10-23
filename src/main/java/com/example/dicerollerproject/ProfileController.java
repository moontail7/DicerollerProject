package com.example.dicerollerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ListView;

/**
 * The ProfileController class manages the user's profile page. 
 * It allows users to view and update their custom dice rolls, log out, and navigate back to the main page.
 */
public class ProfileController {

    @FXML private Button btnLogout;
    @FXML private Label lblUsername;
    @FXML private Button btnEditProfile;
    @FXML private Button btnReturnToMain;
    @FXML private TextField tbxRollName, tbxRollFormula;
    @FXML private ListView<String> listCustomRolls;
    @FXML private Button closeButton;

    /**
     * Initializes the profile controller by loading custom rolls for the logged-in user.
     * This method is automatically called when the profile page is loaded.
     */
    @FXML
    public void initialize() {
        loadCustomRolls(); // Load the rolls when the profile page is opened
    }

    /**
     * Populates the profile page with user data, specifically displaying the username.
     * This method is called when the profile page is loaded to show the logged-in user's name.
     */
    public void populateUserData() {
        String username = UserSession.getInstance().getLoggedInUsername();
        if (username != null) {
            lblUsername.setText("Username: " + username);
        }
    }

    /**
     * Displays the logged-in user's username with a welcome message.
     * 
     * @param username The username of the logged-in user.
     */
    public void showLoggedinUser(String username) {
        if (lblUsername != null) {
            lblUsername.setText("Welcome: " + username); // Display the logged-in username
        }
    }

    /**
     * Loads custom dice rolls for the logged-in user and displays them in the ListView.
     * The list is cleared before adding the new rolls to ensure there are no duplicates.
     */
    @FXML
    public void loadCustomRolls() {
        String username = UserSession.getInstance().getLoggedInUsername();
        if (username != null) {
            List<String> customRolls = DatabaseConnection.GetCustomRolls(username);
            listCustomRolls.getItems().clear(); 
            listCustomRolls.getItems().addAll(customRolls); 
        }
    }

    /**
     * Saves a new custom dice roll when the "Save" button is clicked.
     * It checks that both the roll name and roll formula are not empty before saving.
     * 
     * @param event The ActionEvent triggered by the save button click.
     */
    @FXML
    public void saveCustomRollClick(ActionEvent event) {
        String rollName = tbxRollName.getText();
        String rollFormula = tbxRollFormula.getText();

        if (rollName.isEmpty() || rollFormula.isEmpty()) {
            lblUsername.setText("Please enter both a roll name and a formula."); 
            return;
        }

        String username = UserSession.getInstance().getLoggedInUsername();
        DatabaseConnection.SaveCustomRoll(rollName, rollFormula, username); 

        loadCustomRolls(); // Refresh the list of custom rolls

        tbxRollName.clear();
        tbxRollFormula.clear();
    }

    /**
     * Closes the profile window when the close button is clicked.
     * 
     * @param event The ActionEvent triggered by the close button click.
     */
    @FXML 
    public void closeHistoryWindow(ActionEvent event) {
        // REUSED FOR PROFILE WINDOW
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Logs the user out by clearing their session and hiding the profile window.
     * 
     * @param event The ActionEvent triggered by the logout button click.
     * @throws IOException If an error occurs while logging out or closing the window.
     */
    @FXML
    public void logout(ActionEvent event) throws IOException {
        UserSession.getInstance().setLoggedInUsername(null);
        btnLogout.getScene().getWindow().hide();
    }
}
