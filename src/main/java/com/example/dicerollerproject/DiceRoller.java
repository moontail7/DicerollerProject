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

        // Parse input like "2d6+3"
        String[] inputArguments = input.split("\\+");

        for (String argument : inputArguments) {
            if (argument.contains("d")) {
                // Dice roll case, e.g., "2d6"
                int diceCount = Integer.parseInt(argument.split("d")[0]);
                int diceSides = Integer.parseInt(argument.split("d")[1]);

                output = output + diceSides + "-sided dice; ";

                for (int k = 0; k < diceCount; k++) {
                    int rollResult = ThreadLocalRandom.current().nextInt(1, diceSides + 1);
                    output = output + rollResult + ", ";
                    total += rollResult;
                }

                // Remove trailing ", " and add ". "
                output = output.substring(0, output.length() - 2) + ". ";

            } else {
                // Modifier case
                int modifier = Integer.parseInt(argument);
                modifierTotal += modifier;
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
