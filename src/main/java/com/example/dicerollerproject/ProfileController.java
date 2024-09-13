package com.example.dicerollerproject;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;

public class ProfileController {

    @FXML Label lblWelcome;

    @FXML private Button btnLogout;


    
    @FXML
    private Label lblUsername;

    @FXML
    private Label lblEmail;
    

    @FXML
    private Button btnEditProfile;

    @FXML
    private Button btnReturnToMain;




public void showLoggedinUser(String username) {
    lblWelcome.setText("Welcome: " + username); // Display the logged-in username
}

public void init() {
    String username = UserSession.getInstance().getLoggedInUsername();
    String email = "placeholder_email@email.lol";

    lblUsername.setText("Username: " + username);
    lblEmail.setText("Email: " + email);

}


}


