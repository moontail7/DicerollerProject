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







public class ProfileController {

    @FXML
public void initialize() {
    loadCustomRolls(); // Load the rolls when the profile page is opened
}

    @FXML private Button btnLogout;
    @FXML private Label lblUsername;
    @FXML private Button btnEditProfile;
    @FXML private Button btnReturnToMain;

    @FXML private TextField tbxRollName, tbxRollFormula;
    @FXML private ListView<String> listCustomRolls;

    @FXML private Button closeButton;
    

    // Sets up user data on the profile page
    public void populateUserData() {
        String username = UserSession.getInstance().getLoggedInUsername();
        if (username != null) {
            lblUsername.setText("Username: " + username);
        }
    }

    public void showLoggedinUser(String username) {
        if (lblUsername != null) {
            lblUsername.setText("Welcome: " + username); // Display the logged-in username
        }
     }


         // Load custom rolls for the logged-in user and display them in the ListView
         //clears old rolls

         @FXML
         public void loadCustomRolls() {
             String username = UserSession.getInstance().getLoggedInUsername();
             if (username != null) {
                 List<String> customRolls = DatabaseConnection.getCustomRolls(username);
                 listCustomRolls.getItems().clear(); 
                 listCustomRolls.getItems().addAll(customRolls); 
             }
         }
         
         @FXML
         public void saveCustomRollClick(ActionEvent event) {
             String rollName = tbxRollName.getText();
             String rollFormula = tbxRollFormula.getText();
         
             if (rollName.isEmpty() || rollFormula.isEmpty()) {
                 lblUsername.setText("Please enter both a roll name and a formula."); 
                 return;
             }
         
             String username = UserSession.getInstance().getLoggedInUsername();
             DatabaseConnection.saveCustomRoll(rollName, rollFormula, username); 
         
             loadCustomRolls(); 
         
             tbxRollName.clear();
             tbxRollFormula.clear();
         }

             @FXML public void closeHistoryWindow(ActionEvent event) {
        // Close the history window
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

     

    @FXML
    public void logout(ActionEvent event) throws IOException {
        UserSession.getInstance().setLoggedInUsername(null);
        btnLogout.getScene().getWindow().hide();
    }

  


}
