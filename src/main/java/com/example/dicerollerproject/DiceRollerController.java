package com.example.dicerollerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
//media imports for later
import javafx.scene.media.MediaView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
// base randomizer probably dont need 
import java.util.Random;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRollerController {
    @FXML private Label lblRollText;
    //Main output of the input arguments
    @FXML public static Button btnProfile;
    //Main output of the input arguments
    @FXML private TextField tbxInput;
    //text box for main input
    @FXML private Button btnRollDice;
    //Main button used for rolling the input arguments from tbxInput
    @FXML private Button btnRollSingleDie;
    //btn used to roll a single d6
    
    //welcome label 
    @FXML private Label lblWelcome;
    // WIP things
    @FXML
    private Button btnRollSound;
    private int roll;
    @FXML
    private MediaView mediaView;
    // WIP things

    //^^ Initializes the interface screen elements so they can be referenced (and altered) later


    @FXML
    public void btnRollDiceClick() {
        //event
        try {
            lblRollText.setText(MainDiceRoller(tbxInput.getText()));
            
        } catch (Exception e) {
            lblRollText.setText("Invalid Input. Please try again (but better this time).");
        }
        
    }

    public void showLoggedinUser(String username) {
        lblWelcome.setText("Welcome: " + username); // Display the logged-in username
    }

    public void btnQISingleD4Click(ActionEvent actionEvent) {
        //btn Event to add "1d4" to the tbxInput text field
        tbxInput.setText(QIDice("4", tbxInput.getText()));
        //Inputs/adds a d"4" to the tbx inout
    }

    public void btnQISingleD6Click(ActionEvent actionEvent) {
        //btn Event to add "1d6" to the tbxInput text field
        tbxInput.setText(QIDice("6", tbxInput.getText()));
        //Inputs/adds a d"6" to the tbx inout
    }

    public void btnQISingleD8Click(ActionEvent actionEvent) {
        //btn Event to add "1d8" to the tbxInput text field
        tbxInput.setText(QIDice("8", tbxInput.getText()));
        //Inputs/adds a d"8" to the tbx inout
    }

    public void btnQISingleD10Click(ActionEvent actionEvent) {
        //btn Event to add "1d10" to the tbxInput text field
        tbxInput.setText(QIDice("10", tbxInput.getText()));
        //Inputs/adds a d"10" to the tbx inout
    }

    public void btnQISingleD12Click(ActionEvent actionEvent) {
        //btn Event to add "1d12" to the tbxInput text field
        tbxInput.setText(QIDice("12", tbxInput.getText()));
        //Inputs/adds a d"12" to the tbx inout
    }

    public void btnQISingleD20Click(ActionEvent actionEvent) {
        //btn Event to add "1d20" to the tbxInput text field
        tbxInput.setText(QIDice("20", tbxInput.getText()));
        //Inputs/adds a d"20" to the tbx inout
    }

    private  static String QIDice(String DiceSidesNum, String tbxInputText) {
        String Output = "";
        if (tbxInputText.contains("d" + DiceSidesNum)) {
            String[] InputArguments = tbxInputText.split("\\+");
            //split the tbxInout by the '+' into its individual arguments
            for (String InputArgument : InputArguments) {
                //cycle though each argument
                if (InputArgument.contains("d" + DiceSidesNum)) {
                    //retrieve the first d20 argument in the tbxInput
                    int d20diceCount = Integer.parseInt(InputArgument.replace("d" + DiceSidesNum, "")) + 1;
                    //isolate the dice count amount and add 1
                    Output = tbxInputText.replaceFirst(InputArgument, d20diceCount + "d" + DiceSidesNum);
                    //replace only the first occurrence of the d20 argument.
                    break;
                }
            }
        } else if (tbxInputText.length() == 0) {
            //if text is empty
            Output = tbxInputText + "1d" + DiceSidesNum;
        } else if (tbxInputText.substring(tbxInputText.length() - 1).equals("+")) {
            //text is not empty, and no d20 argument (but there is a "+" on the end for some reason)
            Output = tbxInputText + "1d" + DiceSidesNum;
        } else {
            //text is not empty, and no d20 argument
            Output = tbxInputText + "+1d" + DiceSidesNum;
        }
        return Output;
    }

    private static String MainDiceRoller(String input) {
        String output = "Results: ";
        int total = 0;
        int modifierTotal = 0;
        //setup default Variables

        String[] InputArguments = input.split("\\+");
        //Get the first argument entered (the only one) and splits it along the '+'
        for (int n = 0; n < InputArguments.length; n++) {
            if (InputArguments[n].contains("d")) {
                //if the argument demands we roll dice
                int diceCount = Integer.parseInt(InputArguments[n].split("d")[0]);
                int diceSides = Integer.parseInt(InputArguments[n].split("d")[1]);
                //gather information in separate variables
                output = output + diceSides + "-sided dice; ";
                //append the number of sides of the dice to the output string
                for (int k = 0; k < diceCount; k++) {
                    int rollResult = ThreadLocalRandom.current().nextInt(1, diceSides + 1);
                    output = output + rollResult + ", ";
                    //roll the dice randomly and add that value to the output line
                    total += rollResult;
                    //add the roll to the total
                }

                //Remove trailing ", " and add ". "
                output = output.substring(0, output.length() - 2) + ". ";

            } else {
                //Modifier case
                int modifier = Integer.parseInt(InputArguments[n]);
                modifierTotal += modifier;
                //save the modifier total to add at the end (if there is multiple modifiers
                //they will be combined)

                total += modifier;
            }
        }
        if (modifierTotal != 0) {
            output = output + "Modifier: " + modifierTotal + ". ";
        }
        //only add the modifier in the output, if a modifier was entered
        output = output + "Total: " + total + ".";
        return output;
    }






    
    
    
    // WIP things 


    private void bonusTrumpet() {
        try {
            String filename = "win.mp3";
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
    
    // test rolling a 6 sided die with button
    public void rollSixSided() {
        Random random = new Random();
        roll = random.nextInt(6) + 1; // roll a number between 1 and 6
        sayDice(roll);
        playSound();
    }

    // Method to display the rolled dice value
    private void sayDice(int roll) {
        lblRollText.setText("You rolled a " + roll + "!");
    }
}
