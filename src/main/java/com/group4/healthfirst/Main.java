package com.group4.healthfirst;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    // Main color:  #ebebed   --   Off White Cool
    // Secondary color: #7985a3    --    Dark Blue
    // Accent color I: #535b6f    --    Darker Blue
    // Accent color II: #c1c9da    --    light Blue

    // Main color complement:  #EDEDEB   --   Off White Warm
    // Secondary color complement: #A39779    --    Dark Gold
    // Accent color I complement: #6F6753    --    Darker Gold
    // Accent color II complement: #DAD2C1    --    light Gold

    // Main color inversion: #141412    --    Warm Black
    // Main color complement inversion: #121214    --    Cool Black

    // Primary logo color: #8CA6F7    --    Blue
    // Secondary logo color: ##B9C9FB    --    Lighter Blue

    // Primary logo color complement: #F7DD8C    --    Gold
    // Secondary logo color complement: #FBEBB9    --    Lighter Gold

    // Main text color: #555f75    --    Cool Dark Gray

    @Override
    public void start(Stage primaryStage) throws IOException {
        String sceneFile = "/com/group4/healthfirst/main.fxml";
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(DatabaseUtilities.class.getResource(sceneFile));
        root = loader.load();

        primaryStage.setTitle("Health-First Sign-in Page");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

