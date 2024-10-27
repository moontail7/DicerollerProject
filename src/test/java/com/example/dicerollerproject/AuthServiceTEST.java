package com.example.dicerollerproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
  

import java.sql.PreparedStatement;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTEST {

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        authService = new AuthService();
    }

    @Test
    public void testIsUserValid_ValidCredentials() {
        try {
            String username = "validUser";
            String password = "validPass";
            
            // Register a user to ensure the test user exists in the database
            if (!authService.doesUserExist(username)) {
                authService.registerUser(username, password);
            }

            boolean result = authService.isUserValid(username, password);
            assertTrue(result, "Valid credentials should return true.");
        } catch (SQLException e) {
            fail("Unexpected SQLException: " + e.getMessage());
        }
    }

    @Test
    public void testIsUserValid_InvalidCredentials() {
        try {
            String username = "invalidUser";
            String password = "wrongPass";

            boolean result = authService.isUserValid(username, password);
            assertFalse(result, "Invalid credentials should return false.");
        } catch (SQLException e) {
            fail("Unexpected SQLException: " + e.getMessage());
        }
    }

    @Test
    public void testDoesUserExist_UserExists() {
        try {
            String username = "existingUser";
            String password = "somePass";
            
            // Register a user to ensure they exist in the database
            if (!authService.doesUserExist(username)) {
                authService.registerUser(username, password);
            }

            boolean result = authService.doesUserExist(username);
            assertTrue(result, "User should exist after registration.");
        } catch (SQLException e) {
            fail("Unexpected SQLException: " + e.getMessage());
        }
    }

    @Test
    public void testDoesUserExist_UserDoesNotExist() {
        try {
            String username = "nonExistentUser";

            boolean result = authService.doesUserExist(username);
            assertFalse(result, "Non-existent user should return false.");
        } catch (SQLException e) {
            fail("Unexpected SQLException: " + e.getMessage());
        }
    }

    @Test
    public void testRegisterUser_Success() {
        try {
            String username = "newUser";
            String password = "newPass";
    
            // Remove the user if they already exist to ensure a clean state
            if (authService.doesUserExist(username)) {
                try (PreparedStatement stmt = DatabaseConnection.getInstance()
                        .prepareStatement("DELETE FROM loginAccountDetails WHERE loginUsername = ?")) {
                    stmt.setString(1, username);
                    stmt.executeUpdate();
                }
            }
    
            // Register the user
            authService.registerUser(username, password);
    
            // Verify the user can now log in with the registered credentials
            boolean isUserValid = authService.isUserValid(username, password);
            assertTrue(isUserValid, "User should be able to log in after registration.");
        } catch (SQLException e) {
            fail("Unexpected SQLException: " + e.getMessage());
        }
    }
}
