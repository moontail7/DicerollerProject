package com.example.dicerollerproject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.concurrent.ThreadLocalRandom;

public class DiceRoller {

    @FXML
    private Label lblRollText;

    @FXML
    private TextField tbxInput;

    @FXML
    private Button btnReroll;

    @FXML
    public void handleButtonClick() {
        try {
            lblRollText.setText(DiceRollerOutput(tbxInput.getText()));
        } catch (Exception expt) {
            lblRollText.setText("Invalid Input. Please try again (but better this time).");
        }
    }

    private static String DiceRollerOutput(String input) {
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

                // Remove trailing ", " and add ". "
                output = output.substring(0, output.length() - 2) + ". ";

            } else {
                // Modifier case
                int modifier = Integer.parseInt(InputArguments[n]);
                modifierTotal += modifier;
                                //save the modifier total to add at the end (if there is multiple modifiers they will be combined)

                total += modifier;
            }
        }

        if (modifierTotal != 0) {
            output = output + "Modifier: " + modifierTotal + ". ";
        }

        output = output + "Total: " + total + ".";
        return output;
    }
}
