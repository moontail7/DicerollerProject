package com.example.dicerollerproject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseConnection {
    private static Connection instance = null;

    private DatabaseConnection() {
        String url = "jdbc:sqlite:database.db";
        try {
            instance = DriverManager.getConnection(url);
            createTable();
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS rolls (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "roll_value INTEGER NOT NULL, " +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)";

        try (Statement stmt = instance.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection();
        }
        return instance;
    }


    public static void closeConnection() {
        try {
            if (instance != null && !instance.isClosed()) {
                instance.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // Method to insert a roll value into the database
    public static void insertRoll(int rollValue) {
        if (instance == null || isConnectionClosed()) {
            System.err.println("Connection is closed or null");
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


    private static boolean isConnectionClosed() {
        try {
            return instance == null || instance.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    // Method to retrieve roll history
    public static List<HistoryController.Roll> getAllRolls() {
        List<HistoryController.Roll> rolls = new ArrayList<>();
        String sql = "SELECT * FROM rolls";

        try (Connection conn = getInstance();
             Statement stmt = conn.createStatement();
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

}
