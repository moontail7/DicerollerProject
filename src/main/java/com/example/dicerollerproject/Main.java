package com.example.dicerollerproject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import javafx.scene.Parent;
import java.awt.*;
import java.util.Objects;
//^not being used currently but are used for one time executions, such as creating a table

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.dicerollerproject.DiceRollerController;
import com.example.dicerollerproject.ProfileController;

public class Main extends Application {
    private static Stage primaryStage; // Make the stage static to access it from controllers

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;  //base stage for the application
        primaryStage.setTitle("Dice Roller");

        // Load and show the initial login scene
        showLoginScene();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Methods for showing scenes
    // stage > scene
    public static void showLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/dicerollerproject/login.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void showMainScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/dicerollerproject/main.fxml"));
        Scene scene = new Scene(loader.load());
    
        // show the logged-in user via controller
        DiceRollerController controller = loader.getController();
        controller.showLoggedinUser(UserSession.getInstance().getLoggedInUsername());
    
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    public static void showProfileWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/dicerollerproject/profile.fxml"));
        Scene scene = new Scene(loader.load());
    
        ProfileController controller = loader.getController();
        controller.showLoggedinUser(UserSession.getInstance().getLoggedInUsername());
        controller.populateUserData();
    
        Stage profileStage = new Stage();
        profileStage.setTitle("User Profile");
        profileStage.setScene(scene);
        profileStage.initModality(Modality.APPLICATION_MODAL);
        profileStage.show();
    }

    public static void showSettingsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/dicerollerproject/settings.fxml"));
        Scene scene = new Scene(loader.load());
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        settingsStage.setScene(scene);
        settingsStage.show();
    }
}