package com.example.dicerollerproject;

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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/dicerollerproject/main.fxml")));
        //declares (and loads) the fxml file

        Window.setTitle("Dice Roller");

        int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int screenHeight = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        Window.setWidth(screenWidth/2);
        Window.setHeight(screenHeight/2);
        //Retrieve screen dimensions and set window to half the size

        Window.setScene(new Scene(root, 6, 4));
        //ngl doesnt know what this does but its VERY important

        Window.show();
        //shows the window to the user
    }

    public static void main(String[] args) {
        launch(args);
    }
}
