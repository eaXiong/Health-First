package com.example.healthfirst;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DoctorLoginView {

    private VBox view;
    private TextField usernameField;
    private PasswordField passwordField;

    public DoctorLoginView() {
        view = new VBox();
        view.setPadding(new Insets(10));
        view.setSpacing(10);

        Label titleLabel = new Label("Doctor Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin());

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> HealthFirst.setView(new MainMenu().getView()));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 1, 2);
        gridPane.add(backButton, 1, 3);

        view.getChildren().addAll(titleLabel, gridPane);
    }

    public VBox getView() {
        return view;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (validateCredentials(username, password)) {
            HealthFirst.setView(new DoctorView().getView());
        } else {
            showError("Invalid username or password.");
        }
    }

    private boolean validateCredentials(String username, String password) {
        String filePath = "doctors/" + username + ".txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String storedPassword = reader.readLine();
            return password.equals(storedPassword);
        } catch (IOException e) {
            return false;
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
