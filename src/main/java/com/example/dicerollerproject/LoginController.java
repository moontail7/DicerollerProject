package com.example.dicerollerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    

    @FXML
    public void btnLogin(ActionEvent actionEvent) {
        //Login Button behaviour
        // init input strings
        String enteredUsername = tbxInputUsername.getText();
        String enteredPassword = tbxInputPassword.getText();
        // run validation methods on input
        if (validateInput(enteredUsername, enteredPassword)) {
            if (isUserValid(enteredUsername, enteredPassword)) {
                lblInstructions.setText("Login Success. Opening the Dice Roller app (ETA <1s).");
                //pass the username to the UserSession class
                UserSession.getInstance().setLoggedInUsername(enteredUsername);
                // lblWelcome.setText("Welcome: " + enteredUsername);

                //close the login window
                btnLogin.getScene().getWindow().hide();

                //open the main window
                openMainWindow();
            } else {
                lblInstructions.setText("Invalid username or password. Please try again.");
            }
        }
    }

    @FXML
    public void btnRegister(ActionEvent actionEvent) {
        //when the register button is pressed
        // init input strings

        String enteredUsername = tbxInputUsername.getText();
        String enteredPassword = tbxInputPassword.getText();
        // run validation methods on input
        if (validateInput(enteredUsername, enteredPassword)) {
            if (!doesUserExist(enteredUsername)) {
                registerUser(enteredUsername, enteredPassword);
            } else {
                lblInstructions.setText("This username is already registered.");
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
                

    private boolean isUserValid(String username, String password) {
        //check if the entered username matches any DB entry
        try (PreparedStatement check = connection.prepareStatement("SELECT * FROM loginAccountDetails WHERE loginUsername = ? AND loginPassword = ?")) {
            //set the username and password input to parameter 1 and 2 respectively in the SQL prepared statement

            check.setString(1, username);
            check.setString(2, password);
            ResultSet rs = check.executeQuery();
            //exectue

            return rs.next();
            //if the result set has a next value, then the username exists
        } catch (SQLException e) {
            lblInstructions.setText("Critical backend error: " + e.getMessage() + ".\nPlease Try again.");
            e.printStackTrace();
            return false;
        }
    }

    private boolean doesUserExist(String username) {
        //check if the entered username matches any DB entry
        try (PreparedStatement exist = connection.prepareStatement("SELECT * FROM loginAccountDetails WHERE loginUsername = ?")) {
            exist.setString(1, username);
            //execute the prepared statement
            ResultSet rs = exist.executeQuery();
            return rs.next();
            //if the result set has a next value, then the username exists
        } catch (SQLException e) {
            lblInstructions.setText("Critical backend error: " + e.getMessage() + ".\nPlease Try again.");
            e.printStackTrace();
            return true;  // Return true to prevent registration
        }
    }

    private void registerUser(String username, String password) {
        //prepare the bulk statement, inserting the username and password to the table (which we set below)
        try (PreparedStatement insertAccount = connection.prepareStatement("INSERT INTO loginAccountDetails (loginUsername, loginPassword) VALUES (?, ?)")) {
            //set the username and password input to parameter 1 and 2 respectively in the SQL prepared statement
            insertAccount.setString(1, username);
            insertAccount.setString(2, password);
            //execute the prepared statement
            insertAccount.executeUpdate();
            lblInstructions.setText("Registration successful! You can now log in.");
        } catch (SQLException e) {
            lblInstructions.setText("Critical backend error: " + e.getMessage() + ".\nPlease Try again.");
            e.printStackTrace();
        }
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
