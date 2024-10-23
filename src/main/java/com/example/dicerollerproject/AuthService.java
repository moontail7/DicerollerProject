package com.example.dicerollerproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The AuthService class handles user authentication, including validation of credentials,
 * checking for existing usernames, and registering new users in the database.
 */
public class AuthService {

    // Database connection instance from DatabaseConnection class
    private final Connection connection = DatabaseConnection.getInstance();

    /**
     * Validates user credentials by checking if the provided username and password
     * match a record in the database.
     * 
     * @param username The username entered by the user.
     * @param password The password entered by the user.
     * @return true if the credentials are valid (i.e., a matching record exists), false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean isUserValid(String username, String password) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM loginAccountDetails WHERE loginUsername = ? AND loginPassword = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a user is found
        }
    }

    /**
     * Checks if a user with the given username already exists in the database.
     * 
     * @param username The username to check for.
     * @return true if the username exists, false otherwise.
     * @throws SQLException If a database access error occurs.
     */
    public boolean doesUserExist(String username) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM loginAccountDetails WHERE loginUsername = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if a username is found
        }
    }

    /**
     * Registers a new user by inserting the provided username and password into the database.
     * 
     * @param username The username to register.
     * @param password The password associated with the username.
     * @throws SQLException If a database access error occurs or the insert operation fails.
     */
    public void registerUser(String username, String password) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO loginAccountDetails (loginUsername, loginPassword) VALUES (?, ?)")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        }
    }
}

// Below are the older versions of the methods with comments, kept for reference:

// private boolean isUserValid(String username, String password) {
//     //check if the entered username matches any DB entry
//     try (PreparedStatement check = connection.prepareStatement("SELECT * FROM loginAccountDetails WHERE loginUsername = ? AND loginPassword = ?")) {
//         //set the username and password input to parameter 1 and 2 respectively in the SQL prepared statement

//         check.setString(1, username);
//         check.setString(2, password);
//         ResultSet rs = check.executeQuery();
//         //exectue

//         return rs.next();
//         //if the result set has a next value, then the username exists
//     } catch (SQLException e) {
//         lblInstructions.setText("Critical backend error: " + e.getMessage() + ".\nPlease Try again.");
//         e.printStackTrace();
//         return false;
//     }
// }

// private boolean doesUserExist(String username) {
//     //check if the entered username matches any DB entry
//     try (PreparedStatement exist = connection.prepareStatement("SELECT * FROM loginAccountDetails WHERE loginUsername = ?")) {
//         exist.setString(1, username);
//         //execute the prepared statement
//         ResultSet rs = exist.executeQuery();
//         return rs.next();
//         //if the result set has a next value, then the username exists
//     } catch (SQLException e) {
//         lblInstructions.setText("Critical backend error: " + e.getMessage() + ".\nPlease Try again.");
//         e.printStackTrace();
//         return true;  // Return true to prevent registration
//     }
// }

// private void registerUser(String username, String password) {
//     //prepare the bulk statement, inserting the username and password to the table (which we set below)
//     try (PreparedStatement insertAccount = connection.prepareStatement("INSERT INTO loginAccountDetails (loginUsername, loginPassword) VALUES (?, ?)")) {
//         //set the username and password input to parameter 1 and 2 respectively in the SQL prepared statement
//         insertAccount.setString(1, username);
//         insertAccount.setString(2, password);
//         //execute the prepared statement
//         insertAccount.executeUpdate();
//         lblInstructions.setText("Registration successful! You can now log in.");
//     } catch (SQLException e) {
//         lblInstructions.setText("Critical backend error: " + e.getMessage() + ".\nPlease Try again.");
//         e.printStackTrace();
//     }
// }
