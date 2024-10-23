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

public class LoginController {
    //text boxes for username and password inputs respectively
    @FXML private TextField tbxInputUsername;
    @FXML private TextField tbxInputPassword;
    //button to login and register respectively
    @FXML private Button btnLogin;
    @FXML private Button btnRegister;
    //initial instructions and feedback in case of errors
    @FXML private Label lblInstructions;
    //hold da user
    // @FXML private Label lblWelcome;

    //create priv connection to DB
    private final Connection connection = DatabaseConnection.getInstance();

    // connection to auth service functions
    private final AuthService authService = new AuthService();

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

    @FXML
    public void btnLogin(ActionEvent actionEvent) {

        //Login Button behaviour
        // init input strings
        String enteredUsername = tbxInputUsername.getText();
        String enteredPassword = tbxInputPassword.getText();
        // run validation methods on input
        if (validateInput(enteredUsername, enteredPassword)) {
            try {
                if (authService.isUserValid(enteredUsername, enteredPassword)) {
                    lblInstructions.setText("Login Success. Opening the Dice Roller app...");
                    //pass the username to the UserSession class
                    UserSession.getInstance().setLoggedInUsername(enteredUsername);
                     //close the login window
                    btnLogin.getScene().getWindow().hide();
                    openMainWindow();
                } else {
                    lblInstructions.setText("Invalid username or password. Please try again.");
                }
            } catch (SQLException e) {
                lblInstructions.setText("Error during login: " + e.getMessage()  + ".\nPlease Try again.");
            }
        }
    }

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

    private boolean validateInput(String username, String password) {
        // reusable validation method 
        // can add more checks than just blank checks here TODO:: add more checks
        if (username.isBlank() || password.isBlank()) {
            lblInstructions.setText("Please enter a non-null username & password.");
            return false;
        }
        return true;
    }
                


    //method to show main window
    private void openMainWindow() {
        try {
            Main.showMainScene(UserSession.getInstance().getLoggedInUsername()); 
        } catch (IOException e) {
            lblInstructions.setText("Error opening main window: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
}
