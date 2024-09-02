package com.example.dicerollerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.sql.*;
import java.util.ArrayList;

public class LoginController {
    @FXML public TextField tbxInputUsername;
    @FXML public TextField tbxInputPassword;
    //text boxes for username and password inputs respectively
    @FXML public Button btnLogin;
    @FXML public Button btnRegister;
    //button to login and register respectively
    @FXML public Label lblInstructions;
    //initial instructions and feedback in case of errors

    Connection connection = DatabaseConnection.getInstance();
    //connect to the database

    public ResultSet rsLoginUsernames;
    public ArrayList<String> loginUsernames = new ArrayList<>();
    {
        try {
            rsLoginUsernames = connection.createStatement().executeQuery("SELECT loginUsername FROM loginAccountDetails");
            //gets the result set for usernames
            while (rsLoginUsernames.next()) {
                //for each username in the result set, add it to the list
                loginUsernames.add(rsLoginUsernames.getString(1));
            }
        } catch (SQLException e) {
            lblInstructions.setText("Critical backend error: " + e + ".\nPlease Try again.");
        }
    }
    //reference the database to retrieve the usernames, initially in a result set, then move to a list (because they can be appended to) then to an array to be used later
    public ResultSet rsLoginPasswords;
    public ArrayList<String> loginPasswords = new ArrayList<>();
    {
        try {
            rsLoginPasswords = connection.createStatement().executeQuery("SELECT loginPassword FROM loginAccountDetails");
            //gets the result set for passwords
            while (rsLoginPasswords.next()) {
                //for each password in the result set, add it to the list
                loginPasswords.add(rsLoginPasswords.getString(1));
            }
        } catch (SQLException e) {
            lblInstructions.setText("Critical backend error: " + e + ".\nPlease Try again.");
        }
    }
    //reference the database to retrieve the passwords, initially in a result set, then move to a list (because they can be appended to) then to an array to be used later

    public void btnLogin(ActionEvent actionEvent) throws Exception {
        //when the login button is pressed
        String enteredUsername = tbxInputUsername.getText();
        String enteredPassword = tbxInputPassword.getText();
        int usernameIndex = 0;
        boolean foundUsername = false;
        //set starting variables
        if (!enteredUsername.isBlank() && !enteredPassword.isBlank()) {
            //if the entered username AND password is NOT blank (not null for both)
            for (String Username : loginUsernames) {
                //for each username in the database
                if (Username.equals(enteredUsername)) {
                    //if the entered username matches any of them
                    foundUsername = true;
                    break;
                }
                usernameIndex = usernameIndex + 1;
                //manually count the index of the username (to be able to match the password later)
            }
            if (foundUsername) {
                //if username does exist in the database
                if (loginPasswords.get(usernameIndex).equals(enteredPassword)) {
                    //if the password the correlates to the username (using the index) i.e. the password is correct
                    lblInstructions.setText("Login Success. Opening the Dice Roller app (ETA <1s).");
                    btnLogin.getScene().getWindow().hide();
                    Main.openMainWindow();
                } else {
                    //if the password does not correlate with the username
                    lblInstructions.setText("Password Incorrect. Please try again (figuratively speaking)");
                }
            } else {
                //if the username is not found
                lblInstructions.setText("Username not found. You can register this username if you wish.");
            }
        } else {
            lblInstructions.setText("Please enter a non-null username & password.");
        }
    }

    public void btnRegister(ActionEvent actionEvent) {
        //when the register button is pressed
        //below code very similar to login event, to test if the username exists in the database already
        String enteredUsername = tbxInputUsername.getText();
        String enteredPassword = tbxInputPassword.getText();
        boolean foundUsername = false;
        //set starting variables
        if (!enteredUsername.isBlank() && !enteredPassword.isBlank()) {
            //if the entered username AND password is NOT blank (not null for both)
            for (String Username : loginUsernames) {
                //for each username in the database
                if (Username.equals(enteredUsername)) {
                    //if the entered username matches any of them
                    foundUsername = true;
                    break;
                }
            }
            if (!foundUsername) {
                //if the entered username does NOT match any of them
                try {
                    PreparedStatement insertAccount = connection.prepareStatement("INSERT INTO loginAccountDetails (loginUsername, loginPassword) VALUES (?, ?)");
                    //prepare the bulk statement, inserting the username and password to the table (which we set below)
                    insertAccount.setString(1, enteredUsername);
                    insertAccount.setString(2, enteredPassword);
                    //set the username and password input to parameter 1 and 2 respectively in the SQL prepared statement
                    insertAccount.execute();
                    //execute the prepared statement
                } catch (SQLException e) {
                    lblInstructions.setText("Critical backend error: " + e + ".\nPlease Try again.");
                }
            } else {
                //if the entered username exists in the database
                lblInstructions.setText("This username is already registered");
            }
        } else {
            //if either the username or password is blank (null)
            lblInstructions.setText("Please enter a non-null username & password.");
        }
    }
}
