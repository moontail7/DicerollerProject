package com.example.dicerollerproject;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class UserSessionTEST {

    @Test
    public void testIfSameSessionUsedEveryTime() {
        UserSession session1 = UserSession.getInstance();
        UserSession session2 = UserSession.getInstance();
        assertSame(session1, session2, "It should always use the same session object.");
    }

    @Test
    public void testIfUsernameSetCorrectly() {
        UserSession session = UserSession.getInstance();
        session.setLoggedInUsername("testUser");
        assertEquals("testUser", session.getLoggedInUsername(), "Username should match the one that was set.");
    }
}