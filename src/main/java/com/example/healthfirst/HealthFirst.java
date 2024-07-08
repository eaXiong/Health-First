package com.example.healthfirst;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HealthFirst extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        HealthFirst.primaryStage = primaryStage;
        primaryStage.setTitle("HealthFirst System");

        // Start with Main Menu
        MainMenu mainMenu = new MainMenu();
        Scene scene = new Scene(mainMenu.getView(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setView(VBox view) {
        Scene scene = new Scene(view, 800, 600);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
