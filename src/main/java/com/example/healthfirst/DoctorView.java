package com.example.healthfirst;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DoctorView {

    private VBox view;
    private TextField patientUsernameField;
    private TextArea immunizationHistoryArea;
    private TextArea previousHealthIssuesArea;
    private TextArea medicationsArea;
    private TextArea knownAllergiesArea;
    private TextArea physicalTestFindingsArea;
    private TextArea newPrescriptionsArea;
    private TextArea messagesArea;
    private TextArea replyMessageArea;

    public DoctorView() {
        view = new VBox();
        view.setPadding(new Insets(10));

        Label titleLabel = new Label("Doctor - Patient Information and Findings");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Input fields
        patientUsernameField = new TextField();
        patientUsernameField.setPromptText("Patient Username");

        immunizationHistoryArea = new TextArea();
        immunizationHistoryArea.setPromptText("Immunization History");
        immunizationHistoryArea.setEditable(false);

        previousHealthIssuesArea = new TextArea();
        previousHealthIssuesArea.setPromptText("Previous Health Issues");
        previousHealthIssuesArea.setEditable(false);

        medicationsArea = new TextArea();
        medicationsArea.setPromptText("Medications");
        medicationsArea.setEditable(false);

        knownAllergiesArea = new TextArea();
        knownAllergiesArea.setPromptText("Known Allergies");
        knownAllergiesArea.setEditable(false);

        physicalTestFindingsArea = new TextArea();
        physicalTestFindingsArea.setPromptText("Physical Test Findings");

        newPrescriptionsArea = new TextArea();
        newPrescriptionsArea.setPromptText("New Prescriptions");

        messagesArea = new TextArea();
        messagesArea.setEditable(false);
        messagesArea.setPromptText("Messages");

        replyMessageArea = new TextArea();
        replyMessageArea.setPromptText("Reply to patient");

        Button viewPatientButton = new Button("View Patient Info");
        viewPatientButton.setOnAction(e -> viewPatientInfo());

        Button saveButton = new Button("Save Findings and Send Prescriptions to Pharmacy");
        saveButton.setOnAction(e -> {
            // Validate fields
            if (patientUsernameField.getText().isEmpty() || physicalTestFindingsArea.getText().isEmpty() || newPrescriptionsArea.getText().isEmpty()) {
                showError("All fields must be completed.");
            } else {
                // Save findings and prescriptions
                saveFindingsAndPrescriptions(
                        patientUsernameField.getText(),
                        physicalTestFindingsArea.getText(),
                        newPrescriptionsArea.getText()
                );
            }
        });

        Button replyButton = new Button("Reply to Patient");
        replyButton.setOnAction(e -> {
            String patientUsername = patientUsernameField.getText();
            String message = replyMessageArea.getText().trim();
            if (!message.isEmpty()) {
                FileHandler.writeMessageToFile("patients/" + patientUsername + "_messages.txt", "Doctor", message);
                showInfo("Reply sent.");
                replyMessageArea.clear();
                messagesArea.appendText("Doctor: " + message + "\n");
            } else {
                showError("Reply cannot be empty.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> HealthFirst.setView(new MainMenu().getView()));

        GridPane patientInfoGridPane = new GridPane();
        patientInfoGridPane.setHgap(10);
        patientInfoGridPane.setVgap(10);
        patientInfoGridPane.add(new Label("Patient Username:"), 0, 0);
        patientInfoGridPane.add(patientUsernameField, 1, 0);
        patientInfoGridPane.add(viewPatientButton, 1, 1);
        patientInfoGridPane.add(new Label("Immunization History:"), 0, 2);
        patientInfoGridPane.add(immunizationHistoryArea, 1, 2);
        patientInfoGridPane.add(new Label("Previous Health Issues:"), 0, 3);
        patientInfoGridPane.add(previousHealthIssuesArea, 1, 3);
        patientInfoGridPane.add(new Label("Medications:"), 0, 4);
        patientInfoGridPane.add(medicationsArea, 1, 4);
        patientInfoGridPane.add(new Label("Known Allergies:"), 0, 5);
        patientInfoGridPane.add(knownAllergiesArea, 1, 5);
        patientInfoGridPane.add(new Label("Physical Test Findings:"), 0, 6);
        patientInfoGridPane.add(physicalTestFindingsArea, 1, 6);
        patientInfoGridPane.add(new Label("New Prescriptions:"), 0, 7);
        patientInfoGridPane.add(newPrescriptionsArea, 1, 7);
        patientInfoGridPane.add(new Label("Messages:"), 0, 8);
        patientInfoGridPane.add(messagesArea, 1, 8);
        patientInfoGridPane.add(new Label("Reply Message:"), 0, 9);
        patientInfoGridPane.add(replyMessageArea, 1, 9);
        patientInfoGridPane.add(saveButton, 1, 10);
        patientInfoGridPane.add(replyButton, 1, 11);
        patientInfoGridPane.add(backButton, 1, 12);

        view.getChildren().addAll(titleLabel, patientInfoGridPane);
    }

    public VBox getView() {
        return view;
    }

    private void viewPatientInfo() {
        String patientUsername = patientUsernameField.getText();
        String patientData = FileHandler.readFromFile("patients/" + patientUsername + ".txt");
        String messagesData = FileHandler.readMessagesFromFile("patients/" + patientUsername + "_messages.txt");

        if (patientData == null) {
            showError("Patient not found.");
            return;
        }

        String[] lines = patientData.split("\n");
        for (String line : lines) {
            if (line.startsWith("Immunization History:")) {
                immunizationHistoryArea.setText(line.split(":")[1].trim());
            } else if (line.startsWith("Previous Health Issues:")) {
                previousHealthIssuesArea.setText(line.split(":")[1].trim());
            } else if (line.startsWith("Medications:")) {
                medicationsArea.setText(line.split(":")[1].trim());
            } else if (line.startsWith("Known Allergies:")) {
                knownAllergiesArea.setText(line.split(":")[1].trim());
            }
        }
        messagesArea.setText(messagesData);
    }

    private void saveFindingsAndPrescriptions(String patientUsername, String physicalTestFindings, String newPrescriptions) {
        // Save findings and prescriptions to file
        String findingsAndPrescriptions = String.format("Physical Test Findings: %s\nNew Prescriptions: %s\n",
                physicalTestFindings, newPrescriptions);
        FileHandler.writeToFile("patients/" + patientUsername + ".txt", findingsAndPrescriptions);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Findings and prescriptions saved for Patient Username: " + patientUsername);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }
}
