package com.example.dicerollerproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import java.io.File;

import javafx.event.ActionEvent;
import java.sql.*;

public class DiceRollerControllerTEST {

    @Test
    public void testMainDiceRoller_outputFormat() {
        String input = "2d6";
        
        String result = DiceRollerController.MainDiceRoller(input); 
        assertTrue(result.contains("Results:"), "The result should contain 'Results'.");
        assertTrue(result.contains("sided dice"), "The result should mention the dice.");
        assertTrue(result.contains("Total:"), "The result should contain 'Total'.");


        System.out.println("Test Result is: " + result);



        
    }


    @Test
    public void testMainDiceRoller_validInput() {
        String input = "1d6+2";
        String result = DiceRollerController.MainDiceRoller(input);
        assertTrue(result.contains("Results:"), "The result should contain 'Results'.");
        assertTrue(result.contains("Total:"), "The result should contain the total.");
    }

    // @Test
    // public void testMainDiceRoller_badInput() {
    //     String input = "abc";
    //     Exception exception = assertThrows(Exception.class, () -> {
    //         DiceRollerController.MainDiceRoller(input);
    //     });
    //     assertTrue(exception.getMessage().contains("Invalid Input"), "Should throw invalid input exception.");
    // }

    @Test
    public void testQIDice() {
        String input = "";
        String result = DiceRollerController.QIDice("6", input);
        assertEquals("1d6", result, "Should append 1d6 when input is empty.");

        input = "1d6";
        result = DiceRollerController.QIDice("6", input);
        assertEquals("2d6", result, "Should increment dice count when '1d6' is already present.");
    }


    @Test
    public void testSaveCustomRoll() {
        String rollName = "TestRoll";
        String rollFormula = "1d6+3";
        String username = "testUser";

        DatabaseConnection.SaveCustomRoll(rollName, rollFormula, username);
        
        // Retrieve custom rolls and ensure the saved one is in the list
        List<String> customRolls = DatabaseConnection.GetCustomRolls(username);
        assertTrue(customRolls.contains(rollName), "Custom roll should be saved.");
    }

    @Test
    public void testGetCustomRollFormat() {
        String rollName = "TestRoll";
        String username = "testUser";

        String rollFormula = DatabaseConnection.GetRollFormat(rollName, username);
        assertEquals("1d6+3", rollFormula, "Roll formula should match the one saved.");
    }

    public class HistoryControllerTest {

    @Test
    public void testExportRollHistory() {
        HistoryController controller = new HistoryController();
        controller.ExportData(null); // Trigger export function
        
        File exportedFile = new File(System.getProperty("java.io.tmpdir"), "exported_rolls.csv");
        assertTrue(exportedFile.exists(), "Exported file should exist.");
        assertTrue(exportedFile.length() > 0, "Exported file should not be empty.");
        
        // Cleanup after test
        exportedFile.delete();
    }
}
  
}