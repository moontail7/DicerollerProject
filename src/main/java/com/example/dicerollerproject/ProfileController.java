package com.example.dicerollerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.io.IOException;



public class ProfileController {

    @FXML private Button btnLogout;
    @FXML private Label lblUsername;
    @FXML private Button btnEditProfile;
    @FXML private Button btnReturnToMain;

    // Sets up user data on the profile page
    public void populateUserData() {
        String username = UserSession.getInstance().getLoggedInUsername();
        if (username != null) {
            lblUsername.setText("Username: " + username);
        }
    }

    @FXML
    public void returnToMain(ActionEvent event) throws IOException {
        btnReturnToMain.getScene().getWindow().hide();
    }

    @FXML
    public void logout(ActionEvent event) throws IOException {
        UserSession.getInstance().setLoggedInUsername(null);
        btnLogout.getScene().getWindow().hide();
    }

  

    public void showLoggedinUser(String username) {
    if (lblUsername != null) {
        lblUsername.setText("Welcome: " + username); // Display the logged-in username
    }
 }
}
