package com.example.dicerollerproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The DatabaseConnection class provides methods to interact with an SQLite database
 * for storing and retrieving roll values and custom roll definitions.
 * It follows the Singleton pattern to ensure only one database connection exists.
 */
public class DatabaseConnection {
    private static Connection instance = null;

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ///////////////////////////// DB ROLLS /////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    /**
     * Private constructor for initializing the database connection.
     * If the connection is successful, it also creates the necessary tables.
     */
    private DatabaseConnection() {
        String url = "jdbc:sqlite:database.db";
        try {
            instance = DriverManager.getConnection(url);
            createTable();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    /**
     * Creates the 'rolls' table in the database if it doesn't already exist.
     */
    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS rolls (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "roll_value INTEGER NOT NULL, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

        // TODO add custom rolls table to init
        // TODO add roll history table to init

        try (Statement stmt = instance.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Gets the database connection instance, creating it if necessary.
     * 
     * @return The connection instance to the SQLite database.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection(); // Initialize only if not already done
        }
        return instance;
    }

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ///////////////////////////// HISTORY ROLLS /////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    /**
     * Inserts a roll value into the 'rolls' table.
     * 
     * @param rollValue The value of the roll to be inserted.
     */
    public static void insertRoll(int rollValue) {
        if (instance == null) {
            System.err.println("Database connection is not initialized.");
            return;
        }

        String sql = "INSERT INTO rolls (roll_value) VALUES (?)";

        try (PreparedStatement pstmt = instance.prepareStatement(sql)) {
            pstmt.setInt(1, rollValue);
            pstmt.executeUpdate();
            System.out.println("Inserted roll value: " + rollValue);
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    /**
     * Clears all entries from the 'rolls' table.
     */
    public static void clearAllRolls() {
        String sql = "DELETE FROM rolls"; // Deletes all rows from the rolls table
        try (Statement stmt = getInstance().createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All rolls cleared.");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    /**
     * Retrieves all roll entries from the 'rolls' table.
     * 
     * @return A list of Roll objects representing all stored rolls.
     */
    public static List<HistoryController.Roll> getAllRolls() {
        List<HistoryController.Roll> rolls = new ArrayList<>();
        String sql = "SELECT * FROM rolls";

        try (Statement stmt = instance.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int rollValue = rs.getInt("roll_value");
                String timestamp = rs.getString("timestamp");
                rolls.add(new HistoryController.Roll(id, rollValue, timestamp));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return rolls;
    }

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ///////////////////////////// CUSTOM ROLLS /////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    /**
     * Saves a custom roll to the 'custom_rolls' table.
     * 
     * @param rollName The name of the custom roll.
     * @param rollFormula The formula used to calculate the custom roll.
     * @param username The user associated with the custom roll.
     */
    public static void SaveCustomRoll(String rollName, String rollFormula, String username) {
        String sql = "INSERT INTO custom_rolls (roll_name, roll_formula, username) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = getInstance().prepareStatement(sql)) {
            pstmt.setString(1, rollName);
            pstmt.setString(2, rollFormula);
            pstmt.setString(3, username);
            pstmt.executeUpdate();
            System.out.println("Custom roll saved successfully.");
        } catch (SQLException e) {
            System.err.println("Error saving custom roll: " + e.getMessage());
        }
    }

    /**
     * Retrieves all custom roll names for a given user.
     * 
     * @param username The username whose custom rolls are to be retrieved.
     * @return A list of custom roll names associated with the specified user.
     */
    public static List<String> GetCustomRolls(String username) {
        List<String> customRolls = new ArrayList<>();
        String sql = "SELECT roll_name FROM custom_rolls WHERE username = ?";

        try (PreparedStatement pstmt = getInstance().prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                customRolls.add(rs.getString("roll_name"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching custom rolls: " + e.getMessage());
        }

        return customRolls;
    }

    /**
     * Retrieves the formula associated with a custom roll for a given user.
     * 
     * @param rollName The name of the custom roll.
     * @param username The username associated with the custom roll.
     * @return The formula for the specified custom roll.
     */
    public static String GetRollFormat(String rollName, String username) {
        String rollFormat = null;
        String sql = "SELECT roll_formula FROM custom_rolls WHERE roll_name = ? AND username = ?";

        try (PreparedStatement pstmt = getInstance().prepareStatement(sql)) {
            pstmt.setString(1, rollName);
            pstmt.setString(2, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rollFormat = rs.getString("roll_formula");
            }
        } catch (SQLException e) {
            System.err.println("Error fetching roll format: " + e.getMessage());
        }

        return rollFormat;
    }
}
