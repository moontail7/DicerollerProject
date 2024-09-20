package com.example.dicerollerproject;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

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

  
}
