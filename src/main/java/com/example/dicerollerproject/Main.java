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
import javafx.stage.Stage;

import java.io.IOException;




public class Main extends Application {

    private static Stage primaryStage; // Make the stage static to access it from controllers

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;  //base stage for the application
        primaryStage.setTitle("Dice Roller");

        // Load and show the initial login scene
        showMainScene();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Methods for showing scenes
    // stage > scene
    public static void showLoginScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/dicerollerproject/login.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void showMainScene(String loggedInUsername) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/com/example/dicerollerproject/main.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        // point to the controller and call the method to show the logged-in user
        DiceRollerController controller = loader.getController();
        controller.showLoggedinUser(loggedInUsername);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
}
