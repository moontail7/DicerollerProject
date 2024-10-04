package com.example.dicerollerproject;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTEST {

    @Test
    public void testIfDBConnectionNotNull() {
        Connection connection = DatabaseConnection.getInstance();
        assertNotNull(connection, "DB connection shouldn't be null, check if it connects.");
    }

    @Test
    public void testIfSameDBConnectionUsedEveryTime() {
        Connection firstInstance = DatabaseConnection.getInstance();
        Connection secondInstance = DatabaseConnection.getInstance();
        assertSame(firstInstance, secondInstance, "should use the same DB connection.");
    }
}
