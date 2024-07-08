package com.example.healthfirst;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NurseLoginView {

    private VBox view;

    public NurseLoginView() {
        view = new VBox();
        view.setPadding(new Insets(10));
        view.setSpacing(10);

        Label titleLabel = new Label("Nurse Login");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {

            handleLogin(usernameField.getText(), passwordField.getText());
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> HealthFirst.setView(new MainMenu().getView()));

        GridPane loginGridPane = new GridPane();
        loginGridPane.setHgap(10);
        loginGridPane.setVgap(10);
        loginGridPane.add(new Label("Username:"), 0, 0);
        loginGridPane.add(usernameField, 1, 0);
        loginGridPane.add(new Label("Password:"), 0, 1);
        loginGridPane.add(passwordField, 1, 1);
        loginGridPane.add(loginButton, 1, 2);
        loginGridPane.add(backButton, 1, 3);

        view.getChildren().addAll(titleLabel, loginGridPane);
    }

    public VBox getView() {
        return view;
    }

    private void handleLogin(String username, String password) {

        String nurseData = FileHandler.readFromFile("nurses/" + username + ".txt");
        if (nurseData != null && nurseData.contains("Password: " + password)) {

            HealthFirst.setView(new NurseView().getView());
        } else {
            showError("Invalid username or password.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
