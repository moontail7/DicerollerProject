package com.example.dicerollerproject;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTEST{

    @Test
    public void testDatabaseConnectionNotNull() {
        Connection connection = DatabaseConnection.getInstance();
        assertNotNull(connection, "Database connection should not be null.");
    }

    @Test
    public void testDatabaseConnectionSingleton() {
        Connection firstInstance = DatabaseConnection.getInstance();
        Connection secondInstance = DatabaseConnection.getInstance();
        assertSame(firstInstance, secondInstance, "DatabaseConnection should return the same instance (Singleton).");
    }
}
