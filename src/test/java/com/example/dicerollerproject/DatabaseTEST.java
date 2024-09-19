package com.example.dicerollerproject;

import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTEST {
    @Test
    void testConnectionNotNull() {
        Connection connection = DatabaseConnection.getInstance();
        assertNotNull(connection, "The database connection should not be null");
    }
}