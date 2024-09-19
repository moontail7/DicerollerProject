package com.example.dicerollerproject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserSessionTEST {

    @Test
    public void testUserSessionSingleton() {
        UserSession session1 = UserSession.getInstance();
        UserSession session2 = UserSession.getInstance();
        assertSame(session1, session2, "UserSession should return the same instance (Singleton).");
    }

    @Test
    public void testUserSessionUsername() {
        UserSession session = UserSession.getInstance();
        session.setLoggedInUsername("testUser");
        assertEquals("testUser", session.getLoggedInUsername(), "Logged in username should match the set value.");
    }
}
