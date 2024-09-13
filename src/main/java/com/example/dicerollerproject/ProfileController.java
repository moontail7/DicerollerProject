package com.example.dicerollerproject;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;

public class ProfileController extends InfoController {

    // @FXML Label lblWelcome;

    @FXML private Button btnLogout;

    @FXML
    private Label lblUsername;



    @FXML
    private Button btnEditProfile;

    @FXML
    private Button btnReturnToMain;




/// PUTS THE DATA INTO THE PROFILE PAGE 
    public void populateUserData() {
    String username = UserSession.getInstance().getLoggedInUsername();
    

    lblUsername.setText("Username: " + username);
    

}



/////// NOT IMPLINMENTED YET //////
public void returnToMain(ActionEvent event) throws IOException {
    btnReturnToMain.getScene().getWindow().hide();


}
public void logout(ActionEvent event) throws IOException {
    UserSession.getInstance().setLoggedInUsername(null);
    btnLogout.getScene().getWindow().hide();

}

}

