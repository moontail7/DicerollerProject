package com.example.dicerollerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InfoController {

 @FXML
  protected Label lblUsername;


 public void showLoggedinUser(String username) {
    if (lblUsername != null) {
        lblUsername.setText("Welcome: " + username); // Display the logged-in username
    }
 }

}
