package com.example.dicerollerproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
// import javafx.scene.control.Button;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.ListView;
// import javafx.scene.control.TextField;

//media imports for later
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.*;
// import javafx.scene.media.MediaView;
// import javafx.scene.media.Media;
// import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.awt.*;
import java.util.List;

import javafx.scene.input.MouseEvent;

import javax.swing.text.html.StyleSheet;
import java.io.IOException;
// base randomizer probably dont need 
import java.util.Random;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRollerController {

    public VBox windowBackground;
    public HBox sidePane;
    public Label mainTitle;
    public Label subTitle;
    public Button btnQISingleD4;
    public Button btnQISingleD6;
    public Button btnQISingleD8;
    public Button btnQISingleD10;
    public Button btnQISingleD12;
    public Button btnQISingleD20;
    @FXML
    private Label lblRollText;
    // Main output of the input arguments
    @FXML
    public Button btnProfile;
    // Main output of the input arguments
    @FXML
    private TextField tbxInput;
    // text box for main input
    @FXML
    private Button btnRollDice;
    // Main button used for rolling the input arguments from tbxInput
    @FXML 
    private Button btnClearDicef;
    // Clear the dice input field

    @FXML
    private Button btnLogout;


    @FXML
    private Button btnRollSingleDie;
    // btn used to roll a single d6

    // welcome label
    @FXML
    private Label lblUsername;
    // WIP things
    @FXML
    private Button btnRollSound;
    private int roll;
    @FXML
    private MediaView mediaView;
    // WIP things

    // Fields for custom rolls
    @FXML
    private TextField tbxCustomRollName;
    @FXML
    private TextField tbxCustomRollFormula;
    @FXML
    private Button btnSaveCustomRoll;
    @FXML
    private ListView<String> listCustomRolls;

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// Main Dice Logic //////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////// 
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////



    /**
 * Handles the event when the "Roll Dice" button is clicked.
 * It attempts to roll the dice based on the input string, updates the result label,
 * and plays a rolling sound.
 */

    @FXML
    public void btnRollDiceClick() {

        // event
        try {
            lblRollText.setText(MainDiceRoller(tbxInput.getText()));
            playSound();

        } catch (Exception e) {
            lblRollText.setText("Invalid Input. Please try again (but better this time).");
        }
    }

    public void btnQISingleD4Click(ActionEvent actionEvent) {
        // btn Event to add "1d4" to the tbxInput text field
        tbxInput.setText(QIDice("4", tbxInput.getText()));
        // Inputs/adds a d"4" to the tbx inout
    }

    public void btnQISingleD6Click(ActionEvent actionEvent) {
        // btn Event to add "1d6" to the tbxInput text field
        tbxInput.setText(QIDice("6", tbxInput.getText()));
        // Inputs/adds a d"6" to the tbx inout
    }

    public void btnQISingleD8Click(ActionEvent actionEvent) {
        // btn Event to add "1d8" to the tbxInput text field
        tbxInput.setText(QIDice("8", tbxInput.getText()));
        // Inputs/adds a d"8" to the tbx inout
    }

    public void btnQISingleD10Click(ActionEvent actionEvent) {
        // btn Event to add "1d10" to the tbxInput text field
        tbxInput.setText(QIDice("10", tbxInput.getText()));
        // Inputs/adds a d"10" to the tbx inout
    }

    public void btnQISingleD12Click(ActionEvent actionEvent) {
        // btn Event to add "1d12" to the tbxInput text field
        tbxInput.setText(QIDice("12", tbxInput.getText()));
        // Inputs/adds a d"12" to the tbx inout
    }

    public void btnQISingleD20Click(ActionEvent actionEvent) {
        // btn Event to add "1d20" to the tbxInput text field
        tbxInput.setText(QIDice("20", tbxInput.getText()));
        // Inputs/adds a d"20" to the tbx inout
    }


    /**
 * Clears the dice input field when the "Clear" button is clicked.
 * This method can handle both mouse and action events.
 *
 * @param mouseEvent The mouse event triggered by clicking the result pane.
 */
    
    public void btnClearMicef(MouseEvent mouseEvent) {
        // btn Event to add "1d20" to the tbxInput text field
        tbxInput.setText("");
        // Inputs/adds a d"20" to the tbx inout
    }


    // actual button version

    public void btnClearDicef(ActionEvent event) {
        // btn Event to add "1d20" to the tbxInput text field
        tbxInput.setText("");
        // Inputs/adds a d"20" to the tbx inout
    }

    /**
     * Adds (or incresses) a die argument with a specified dice sides number
     * @param DiceSidesNum The amount of sides on the dice (rolls that dice once)
     * @param tbxInputText The textbox to input/update the dice argument of
     */
    public static String QIDice(String DiceSidesNum, String tbxInputText) {
        String Output = "";
        if (tbxInputText.contains("d" + DiceSidesNum)) {
            String[] InputArguments = tbxInputText.split("\\+");
            // split the tbxInout by the '+' into its individual arguments
            for (String InputArgument : InputArguments) {
                // cycle though each argument
                if (InputArgument.contains("d" + DiceSidesNum)) {
                    // retrieve the first d20 argument in the tbxInput
                    int d20diceCount = Integer.parseInt(InputArgument.replace("d" + DiceSidesNum, "")) + 1;
                    // isolate the dice count amount and add 1
                    Output = tbxInputText.replaceFirst(InputArgument, d20diceCount + "d" + DiceSidesNum);
                    // replace only the first occurrence of the d20 argument.
                    break;
                }
            }
        } else if (tbxInputText.length() == 0) {
            // if text is empty
            Output = tbxInputText + "1d" + DiceSidesNum;
        } else if (tbxInputText.substring(tbxInputText.length() - 1).equals("+")) {
            // text is not empty, and no d20 argument (but there is a "+" on the end for
            // some reason)
            Output = tbxInputText + "1d" + DiceSidesNum;
        } else {
            // text is not empty, and no d20 argument
            Output = tbxInputText + "+1d" + DiceSidesNum;
        }
        return Output;
    }

    /**
     * Rolls (or adds) the dice roller arguments (seperated by a '+') then formulated a string output with each dice rolled, the total modifier and the total of the modifier(s) and die arguments combined
     * @param input the string containing the complete set of dice roller arguments (to roll and/or add)
     */
    public static String MainDiceRoller(String input) {
        String output = "Results: ";
        int total = 0;
        int modifierTotal = 0;
        // setup default Variables

        String[] InputArguments = input.split("\\+");
        // Get the first argument entered (the only one) and splits it along the '+'
        for (int n = 0; n < InputArguments.length; n++) {
            if (InputArguments[n].contains("d")) {
                // if the argument demands we roll dice
                int diceCount = Integer.parseInt(InputArguments[n].split("d")[0]);
                int diceSides = Integer.parseInt(InputArguments[n].split("d")[1]);
                // gather information in separate variables
                output = output + diceSides + "-sided dice; ";
                // append the number of sides of the dice to the output string
                for (int k = 0; k < diceCount; k++) {
                    int rollResult = ThreadLocalRandom.current().nextInt(1, diceSides + 1);
                    output = output + rollResult + ", ";
                    // roll the dice randomly and add that value to the output line
                    total += rollResult;
                    // add the roll to the total
                }

                // Remove trailing ", " and add ". "
                output = output.substring(0, output.length() - 2) + ". ";

            } else {
                // Modifier case
                int modifier = Integer.parseInt(InputArguments[n]);
                modifierTotal += modifier;
                // save the modifier total to add at the end (if there is multiple modifiers
                // they will be combined)

                total += modifier;
            }
        }
        if (modifierTotal != 0) {
            output = output + "Modifier: " + modifierTotal + ". ";
        }
        // only add the modifier in the output, if a modifier was entered
        output = output + "Total: " + total + ".";

        // Insert the total roll into the database
        DatabaseConnection.getInstance();
        System.out.println("Inserting roll total: " + total);
        DatabaseConnection.insertRoll(total);

        return output;
    }

    //// History STUFF
    public Button btnhistory;

    @FXML
    public void btnhistoryClicked(ActionEvent actionEvent) {
        // Open the history window
        openHistoryWindow();
    }

    /**
     * opens the history.fxml in a new window
     */
    private void openHistoryWindow() {
        try {
            // Load the FXML file for the history window
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/dicerollerproject/History.fxml"));
            Parent root = fxmlLoader.load();

            // Create a new stage (window) for the history
            Stage historyStage = new Stage();
            historyStage.setTitle("Roll History");

            // Set the scene and show the history window
            Scene scene = new Scene(root);
            historyStage.setScene(scene);
            historyStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ////// Setting Stuff
    @FXML
    public Button btnSettings;

    @FXML
    public void btnSettingsClick(ActionEvent actionEvent) {
        openSettingWindow();
    }

    /**
     * opens the Settings.fxml in a new window
     */
    private void openSettingWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/com/example/dicerollerproject/Settings.fxml"));

            
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Settings");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////// PROFILE STUFF ///////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /// PROFILE STUFF /////// POSSIBLY MOVE TO A NEW CONTROLLER FILE

    @FXML
    public void showProfileWindow(ActionEvent event) { 
        try {
            Main.showProfileWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void btnLogoutClick(ActionEvent actionEvent) {
        // Logout the user
        UserSession.getInstance().setLoggedInUsername(null);
        // Close the current window
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.close();

        // Open the login window
        try {
            Main.showLoginScene();
        } catch (IOException e) {
            e.printStackTrace();
    }

}

    /**
     * returns a string that welcomes the logged in user
     */
    public void showLoggedinUser(String username) {
        lblUsername.setText("Welcome: \n"  + username); // Display the logged-in username
    }



    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////// SOUND STUFF  /////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    /// SOUND STUFF /////// POSSIBLY MOVE TO A NEW CONTROLLER FILE

    /**
     * Attempts to play a sound when you win (1% chance every time you roll)
     */
    private void bonusTrumpet() {
        try {
            String filename = "win.wav";
            String fileURI = getClass().getResource(filename).toURI().toString();
            if (fileURI != null) {
                Media media = new Media(fileURI);
                MediaPlayer player = new MediaPlayer(media);
                player.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblRollText.setText("Failed to load sound.");
        }
    }

    /**
     * Rolls a number to play a random sound (from 9 similar sounds) of rolling dice
     */
    @FXML
    private void playSound() {
        // method to play a sound
        // rolls a die from 1-9 to select filename
        try {
            Random random = new Random();
            roll = random.nextInt(9) + 1;
            String filename = String.format("%d.wav", roll);

            String fileURI = getClass()

                    .getResource(filename)
                    .toURI()
                    .toString();

            if (fileURI != null) {
                Media media = new Media(fileURI);
                MediaPlayer player = new MediaPlayer(media);
                player.play();
            }
            Random random2 = new Random();
            int roll2 = random2.nextInt(100) + 1;
            if (roll2 == 99) {
                bonusTrumpet();
            }

        } catch (Exception e) {
            e.printStackTrace();
            lblRollText.setText("Failed to load sound.");
        }
    }

    ///// custom rolls///// custom rolls
    @FXML
    public void initialize() {
        loadCustomRollsIntoComboBox();
        cmbCustomRolls.setPromptText("Select or add a custom roll"); 

            cmbCustomRolls.setOnShowing(event -> {
        loadCustomRollsIntoComboBox();
    });
    }

    @FXML
    public void saveCustomRollClick() {
        // Save the custom roll to the database
        // Get the custom roll name and formula from the text fields
        String rollName = tbxCustomRollName.getText();
        String rollFormula = tbxCustomRollFormula.getText();

        if (rollName.isEmpty() || rollFormula.isEmpty()) {
            lblRollText.setText("Custom Roll Name or Formula is empty.");
            return;
        }

        String username = UserSession.getInstance().getLoggedInUsername();
        DatabaseConnection.SaveCustomRoll(rollName, rollFormula, username);
        loadCustomRolls();
        lblRollText.setText("Custom Roll Saved.");
    }

    private void loadCustomRolls() {
        // Clear the existing items in the ListView
        // Get the custom rolls for the logged-in user
        // Populate the ListView with custom rolls
        listCustomRolls.getItems().clear();
        List<String> customRolls = DatabaseConnection.GetCustomRolls(UserSession.getInstance().getLoggedInUsername());

        // Populate the ListView with custom rolls
        listCustomRolls.getItems().addAll(customRolls);
    }

    @FXML
    private ComboBox<String> cmbCustomRolls;

    @FXML
    private Button btnInsertCustomRoll;

    public void loadCustomRollsIntoComboBox() {
        String username = UserSession.getInstance().getLoggedInUsername();
        List<String> customRolls = DatabaseConnection.GetCustomRolls(username);

        cmbCustomRolls.getItems().clear(); 
        for (String rollName : customRolls) {
            String rollFormula = DatabaseConnection.GetRollFormat(rollName, username);
            String displayText = rollName + " (" + rollFormula + ")";
            cmbCustomRolls.getItems().add(displayText); 
        }
    }

    

    // For inserting the selected custom roll into the input field
    @FXML
    public void InsertCustomRoll() {
        String selectedRollName = cmbCustomRolls.getValue();
        if (selectedRollName != null) {
            if (selectedRollName.contains("(")) {
                selectedRollName = selectedRollName.substring(0, selectedRollName.indexOf(" (")).trim();
            }
            
            String username = UserSession.getInstance().getLoggedInUsername();
            String rollFormula = DatabaseConnection.GetRollFormat(selectedRollName, username);
            if (rollFormula != null) {
                tbxInput.setText(rollFormula); // Insert the roll formula into the input field
            }
        } else {
            lblRollText.setText("Please select a custom roll.");
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////

    @FXML
    private Button modeButton;

    // Define an array of colors
    // Secondary items
    private String[] hexColors2 = {"#A65C48", "#884879", "#5A8CB1", "#BEE60F", "#58a562", "#660505", "#33a31d"};
    // Third items
    private String[] hexColors1 = {"#A73C24", "#A7288A", "#3498DB", "#adf10f", "#d1dbc6", "#870606", "#33a31d"};
    private String[] textColors = {"#FFFFFF", "#FFFFFF", "#FF0000", "#0000FF", "#FF0000", "#FFFFFF", "black"};
    // Fourth colour
    private String[] hexColors3 = {"#805E50", "#785871", "#937972", "#CFDB0F", "#95C094", "#412929", "#33a31d"};
    // Background colours
    private String[] hexColorsBackground = {"#73695E", "#28A745", "#CC6633", "#F1C40F", "#ffffff", "#343434", "linear-gradient(to right, #0d1c5d, #891215);"};
    private String[] hexColorsSidePane = {"#A74C36", "#884879", "#5A8CB1", "#F1C40F", "#ffffff", "#870606", "black"};

    // Counters
    private int currentColorIndex = 0;
    private int currentTextIndex = 0;


    @FXML
    public void modeButtonClicked() {
        // change secondary items
        tbxInput.setStyle("-fx-background-color: " + hexColors2[currentColorIndex]);
        mainTitle.setStyle("-fx-background-color: " + hexColors2[currentColorIndex]);
        subTitle.setStyle("-fx-background-color: " + hexColors2[currentColorIndex]);
        lblRollText.setStyle("-fx-background-color: " + hexColors2[currentColorIndex]);

        // Change the button background color third items
        modeButton.setStyle("-fx-background-color: " + hexColors1[currentColorIndex] + "; -fx-text-fill: " + textColors[currentTextIndex]);
        btnhistory.setStyle("-fx-background-color: " + hexColors1[currentColorIndex] + "; -fx-text-fill: " + textColors[currentTextIndex]);
        btnProfile.setStyle("-fx-background-color: " + hexColors1[currentColorIndex] + "; -fx-text-fill: " + textColors[currentTextIndex]);
        btnSettings.setStyle("-fx-background-color: " + hexColors1[currentColorIndex] + "; -fx-text-fill: " + textColors[currentTextIndex]);
        btnRollDice.setStyle("-fx-background-color: " + hexColors1[currentColorIndex] + "; -fx-text-fill: " + textColors[currentTextIndex]);
        btnInsertCustomRoll.setStyle("-fx-background-color: " + hexColors1[currentColorIndex] + "; -fx-text-fill: " + textColors[currentTextIndex]);
        btnQISingleD4.setStyle("-fx-background-color: " + hexColors1[currentColorIndex]);
        btnQISingleD6.setStyle("-fx-background-color: " + hexColors1[currentColorIndex]);
        btnQISingleD8.setStyle("-fx-background-color: " + hexColors1[currentColorIndex]);
        btnQISingleD10.setStyle("-fx-background-color: " + hexColors1[currentColorIndex]);
        btnQISingleD12.setStyle("-fx-background-color: " + hexColors1[currentColorIndex]);
        btnQISingleD20.setStyle("-fx-background-color: " + hexColors1[currentColorIndex]);

        // change Background
        windowBackground.setStyle("-fx-background-color: " + hexColorsBackground[currentColorIndex]);
        sidePane.setStyle("-fx-background-color: " + hexColorsSidePane[currentColorIndex]);

        // Fourth item
        tbxInput.setStyle("-fx-background-color: " + hexColors3[currentColorIndex]);

        // Cycle to the next color
        currentColorIndex = (currentColorIndex + 1) % hexColors1.length;
    }
}
