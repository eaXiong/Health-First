package com.group4.healthfirst;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // Main color:  #8ca6f7
    // Secondary color: #b9c9fb
    // Teritary color: #F7DD8C

    // Main background color: #9196a2
    // Secondary background color: #6f737c
    // Teritary background color: #4d5056

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root =  FXMLLoader.load(getClass().getResource("main.fxml"));
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

