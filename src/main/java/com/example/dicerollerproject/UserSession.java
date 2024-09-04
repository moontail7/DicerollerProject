package com.example.dicerollerproject;

public class UserSession {
    private static UserSession instance; 
    private String loggedInUsername; 

    // init constructor
    private UserSession() {
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
    }

    // public boolean isLoggedIn() {
    //     return loggedInUsername != null; 
    // }

    // public void logout() {
    //     this.loggedInUsername = null; 
    // }
}

