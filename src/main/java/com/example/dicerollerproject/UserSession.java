package com.example.dicerollerproject;

/**
 * The UserSession class is a Singleton that manages the user's session,
 * specifically tracking the currently logged-in username.
 */
public class UserSession {

    private static UserSession instance; // Singleton instance
    private String loggedInUsername; // Holds the logged-in username

    /**
     * Private constructor to prevent external instantiation.
     * This ensures only one instance of UserSession can exist (Singleton pattern).
     */
    private UserSession() {
    }

    /**
     * Retrieves the singleton instance of UserSession. If it doesn't exist yet,
     * it will be created.
     *
     * @return The singleton instance of UserSession.
     */
    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    /**
     * Gets the username of the currently logged-in user.
     *
     * @return The username of the logged-in user, or null if no user is logged in.
     */
    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    /**
     * Sets the username of the logged-in user. This is called when a user logs in.
     *
     * @param username The username to set as the logged-in user.
     */
    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }


    // /**
    //  * Checks whether a user is currently logged in.
    //  * 
    //  * @return true if a user is logged in, false otherwise.
    //  */
    // public boolean isLoggedIn() {
    //     return loggedInUsername != null;
    // }

    // /**
    //  * Logs the user out by clearing the stored username.
    //  */
    // public void logout() {
    //     this.loggedInUsername = null;
    // }
}
