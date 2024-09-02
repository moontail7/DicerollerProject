package com.example.dicerollerproject;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
//^not being used currently but are used for one time executions, such as creating a table

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage Window) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/dicerollerproject/login.fxml")));
        //declares (and loads) the fxml file

        Window.setTitle("Dice Roller");

        int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        Window.setWidth(screenWidth/2.0);
        Window.setHeight(screenHeight/2.0);
        //Retrieve screen dimensions and set window to half the size

        Window.setScene(new Scene(root, 600, 400));
        //ngl don't know what this does but its VERY important (breaks without it)
        //^^ Creates the window (without showing it)?

        Window.show();
        //shows the window to the user
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void openMainWindow() throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("/com/example/dicerollerproject/main.fxml")));
        Stage Window = new Stage();

        int screenWidth = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        Window.setWidth(screenWidth/2.0);
        Window.setHeight(screenHeight/2.0);
        //Retrieve screen dimensions and set window to half the size

        Window.setScene(new Scene(root, 600, 400));
        //ngl don't know what this does but its VERY important (breaks without it)
        //^^ Creates the window (without showing it)?

        Window.show();
        //shows the window to the user
    }
}
