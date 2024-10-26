package com.example.dicerollerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;

/**
 * The LoginController class handles the login and registration functionality for the user.
 * It interacts with the AuthService to validate login credentials or register new users,
 * and provides feedback through a label.
 */
public class LoginController {
    // Text boxes for username and password inputs respectively
    @FXML private TextField tbxInputUsername;
    @FXML private TextField tbxInputPassword;
    // Buttons for login and registration
    @FXML private Button btnLogin;
    @FXML private Button btnRegister;
    // Label to display instructions and feedback for errors
    @FXML private Label lblInstructions;

    // Database connection
    private final Connection connection = DatabaseConnection.getInstance();

    // AuthService instance for handling authentication-related logic
    private final AuthService authService = new AuthService();

    /**
     * Initializes the controller. Sets up event listeners to trigger the login
     * when the Enter key is pressed or a keyboard shortcut (Ctrl + L) is used.
     */
    @FXML
    public void initialize() {
        // Trigger login when Enter is pressed in the username or password field
        btnLogin.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    // Check focused before performing an action
                    if (event.getCode() == KeyCode.ENTER) {
                        if (newScene.getFocusOwner() == tbxInputUsername || newScene.getFocusOwner() == tbxInputPassword) {
                            btnLogin(new ActionEvent()); 
                        }
                    }
                    if (event.getCode() == KeyCode.L && event.isControlDown()) {
                        btnLogin(new ActionEvent()); 
                    }
                });
            }
        });
    }

    /**
     * Handles the login button click. Validates the input and checks the credentials
     * through the AuthService. If valid, opens the main application window.
     * 
     * @param actionEvent The action event triggered by clicking the login button.
     */
    @FXML
    public void btnLogin(ActionEvent actionEvent) {
        // Login Button behaviour
        String enteredUsername = tbxInputUsername.getText();
        String enteredPassword = tbxInputPassword.getText();

        // Run validation on input
        if (validateInput(enteredUsername, enteredPassword)) {
            try {
                if (authService.isUserValid(enteredUsername, enteredPassword)) {
                    lblInstructions.setText("Login Success. Opening the Dice Roller app...");
                    // Pass the username to the UserSession class
                    UserSession.getInstance().setLoggedInUsername(enteredUsername);
                    // Close the login window
                    btnLogin.getScene().getWindow().hide();
                    openMainWindow();
                } else {
                    lblInstructions.setText("Invalid username or password. Please try again.");
                }
            } catch (SQLException e) {
                lblInstructions.setText("Error during login: " + e.getMessage() + ".\nPlease Try again.");
            }
        }
    }

    /**
     * Handles the registration button click. Checks if the username exists,
     * and if not, registers the new user via the AuthService.
     * 
     * @param actionEvent The action event triggered by clicking the register button.
     */
    @FXML
    public void btnRegister(ActionEvent actionEvent) {
        String enteredUsername = tbxInputUsername.getText();
        String enteredPassword = tbxInputPassword.getText();

        if (validateInput(enteredUsername, enteredPassword)) {
            try {
                if (!authService.doesUserExist(enteredUsername)) {
                    authService.registerUser(enteredUsername, enteredPassword);
                    lblInstructions.setText("Registration successful! You can now log in.");
                } else {
                    lblInstructions.setText("This username is already registered.");
                }
            } catch (SQLException e) {
                lblInstructions.setText("Error during registration: " + e.getMessage());
            }
        }
    }

    /**
     * Validates the username and password input to ensure they are not blank.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if both inputs are valid, false otherwise.
     */
    private boolean validateInput(String username, String password) {
        // Reusable validation method
        if (username.isBlank() || password.isBlank()) {
            lblInstructions.setText("Please enter a non-null username & password.");
            return false;
        }
        return true;
    }

    /**
     * Opens the main window of the application after a successful login.
     */
    private void openMainWindow() {
        try {
            Main.showMainScene();
        } catch (IOException e) {
            lblInstructions.setText("Error opening main window: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
