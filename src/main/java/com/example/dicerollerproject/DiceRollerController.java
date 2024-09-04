package com.example.dicerollerproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    //^^ Initializes the interface screen elements so they can be referenced (and altered) later
    @FXML private Label lblWelcome;

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
}
