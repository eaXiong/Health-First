package com.example.healthfirst;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PatientLoginView {

    private VBox view;

    public PatientLoginView() {
        view = new VBox();
        view.setPadding(new Insets(10));
        view.setSpacing(10);

        Label titleLabel = new Label("Patient Login/Signup");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");


        TextField usernameField = new TextField();
        usernameField.setPromptText("Full Name");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {

            handleLogin(usernameField.getText(), passwordField.getText());
        });


        TextField signupUsernameField = new TextField();
        signupUsernameField.setPromptText("Your Full Name");

        PasswordField signupPasswordField = new PasswordField();
        signupPasswordField.setPromptText("New Password");

        TextField nameField = new TextField();
        nameField.setPromptText("Preferred Name");

        DatePicker birthdayPicker = new DatePicker();
        birthdayPicker.setPromptText("Birthday");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        TextField addressField = new TextField();
        addressField.setPromptText("Insurance/Pharmacy Info");

        TextArea immunizationHistoryField = new TextArea();
        immunizationHistoryField.setPromptText("Immunization History");

        TextArea previousHealthIssuesField = new TextArea();
        previousHealthIssuesField.setPromptText("Previous Health Issues");

        TextArea medicationsField = new TextArea();
        medicationsField.setPromptText("Medications");

        Button signupButton = new Button("Signup");
        signupButton.setOnAction(e -> {

            handleSignup(
                    signupUsernameField.getText(),
                    signupPasswordField.getText(),
                    nameField.getText(),
                    birthdayPicker.getValue() != null ? birthdayPicker.getValue().toString() : "",
                    phoneField.getText(),
                    addressField.getText(),
                    immunizationHistoryField.getText(),
                    previousHealthIssuesField.getText(),
                    medicationsField.getText()
            );
        });

        GridPane loginGridPane = new GridPane();
        loginGridPane.setHgap(10);
        loginGridPane.setVgap(10);
        loginGridPane.add(new Label("Full Name:"), 0, 0);
        loginGridPane.add(usernameField, 1, 0);
        loginGridPane.add(new Label("Password:"), 0, 1);
        loginGridPane.add(passwordField, 1, 1);
        loginGridPane.add(loginButton, 1, 2);

        GridPane signupGridPane = new GridPane();
        signupGridPane.setHgap(10);
        signupGridPane.setVgap(10);
        signupGridPane.add(new Label("Your Full Name:"), 0, 0);
        signupGridPane.add(signupUsernameField, 1, 0);
        signupGridPane.add(new Label("New Password:"), 0, 1);
        signupGridPane.add(signupPasswordField, 1, 1);
        signupGridPane.add(new Label("Your Preferred Name:"), 0, 2);
        signupGridPane.add(nameField, 1, 2);
        signupGridPane.add(new Label("Birthday:"), 0, 3);
        signupGridPane.add(birthdayPicker, 1, 3);
        signupGridPane.add(new Label("Phone Number:"), 0, 4);
        signupGridPane.add(phoneField, 1, 4);
        signupGridPane.add(new Label("Insurance/Pharmacy Info:"), 0, 5);
        signupGridPane.add(addressField, 1, 5);
        signupGridPane.add(new Label("Immunization History:"), 0, 6);
        signupGridPane.add(immunizationHistoryField, 1, 6);
        signupGridPane.add(new Label("Previous Health Issues:"), 0, 7);
        signupGridPane.add(previousHealthIssuesField, 1, 7);
        signupGridPane.add(new Label("Medications:"), 0, 8);
        signupGridPane.add(medicationsField, 1, 8);
        signupGridPane.add(signupButton, 1, 9);

        view.getChildren().addAll(titleLabel, new Label("Login"), loginGridPane, new Label("Signup"), signupGridPane);
    }

    public VBox getView() {
        return view;
    }

    private void handleLogin(String username, String password) {
        // Check login credentials (simplified for example purposes)
        String userData = FileHandler.readFromFile("patients/" + username + ".txt");
        if (userData != null && userData.contains("Password: " + password)) {
            // Redirect to patient view
            HealthFirst.setView(new PatientView(username).getView());
        } else {
            showError("Invalid username or password.");
        }
    }

    private void handleSignup(String username, String password, String name, String birthday, String phone, String address, String immunizationHistory, String previousHealthIssues, String medications) {

        String userData = FileHandler.readFromFile("patients/" + username + ".txt");
        if (userData != null) {
            showError("Username already exists.");
            return;
        }


        int patientID = (int) (Math.random() * 90000) + 10000;


        String userInfo = String.format(
                "Username: %s\nPassword: %s\nPatientID: %d\nName: %s\nBirthday: %s\nPhone: %s\nAddress: %s\nImmunization History: %s\nPrevious Health Issues: %s\nMedications: %s\n",
                username, password, patientID, name, birthday, phone, address, immunizationHistory, previousHealthIssues, medications
        );
        FileHandler.writeToFile("patients/" + username + ".txt", userInfo);


        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Signup successful. Your patient ID is: " + patientID);
        alert.showAndWait();
        HealthFirst.setView(new PatientView(username).getView());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }
}
