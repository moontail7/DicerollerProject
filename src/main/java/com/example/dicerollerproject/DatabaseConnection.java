package com.example.dicerollerproject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static Connection instance = null;

    //////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    ///////////////////////////// DB ROLLS /////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////
    private DatabaseConnection() {
        String url = "jdbc:sqlite:database.db";
        try {
            instance = DriverManager.getConnection(url);
            createTable();
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

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
    // Method to insert a roll value into the database
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

    public static void clearAllRolls() {
        String sql = "DELETE FROM rolls"; // Deletes all rows from the rolls table
        try (Statement stmt = getInstance().createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All rolls cleared.");
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
    }

    // Method to retrieve roll history
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
