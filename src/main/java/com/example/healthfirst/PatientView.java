package com.example.healthfirst;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PatientView {

    private VBox view;
    private String username;
    private TextField phoneField;
    private TextField addressField;
    private TextArea visitResultsArea;
    private TextArea physicalTestFindingsArea;
    private TextArea prescriptionsArea;
    private TextArea messagesArea;
    private TextArea newMessageArea;

    public PatientView(String username) {
        this.username = username;
        view = new VBox();
        view.setPadding(new Insets(10));
        view.setSpacing(10);

        Label titleLabel = new Label("Patient Information");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextArea patientInfoArea = new TextArea();
        patientInfoArea.setEditable(false);

        phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        addressField = new TextField();
        addressField.setPromptText("Address");

        visitResultsArea = new TextArea();
        visitResultsArea.setEditable(false);

        physicalTestFindingsArea = new TextArea();
        physicalTestFindingsArea.setEditable(false);
        physicalTestFindingsArea.setPromptText("Physical Test Findings");

        prescriptionsArea = new TextArea();
        prescriptionsArea.setEditable(false);
        prescriptionsArea.setPromptText("Prescriptions");

        messagesArea = new TextArea();
        messagesArea.setEditable(false);
        messagesArea.setPromptText("Messages");

        newMessageArea = new TextArea();
        newMessageArea.setPromptText("Enter your message here");

        Button loadButton = new Button("Load Patient Info");
        loadButton.setOnAction(e -> loadPatientInfo(patientInfoArea));

        Button updateButton = new Button("Update Contact Info");
        updateButton.setOnAction(e -> updateContactInfo());

        Button sendMessageButton = new Button("Send Message");
        sendMessageButton.setOnAction(e -> sendMessage());

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> HealthFirst.setView(new MainMenu().getView()));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Phone Number:"), 0, 0);
        gridPane.add(phoneField, 1, 0);
        gridPane.add(new Label("Address:"), 0, 1);
        gridPane.add(addressField, 1, 1);
        gridPane.add(updateButton, 1, 2);

        view.getChildren().addAll(titleLabel, loadButton, patientInfoArea, gridPane, new Label("Visit Results:"), visitResultsArea, new Label("Physical Test Findings:"), physicalTestFindingsArea, new Label("Prescriptions:"), prescriptionsArea, new Label("Messages:"), messagesArea, new Label("New Message:"), newMessageArea, sendMessageButton, backButton);
    }

    public VBox getView() {
        return view;
    }

    private void loadPatientInfo(TextArea patientInfoArea) {

        String patientData = FileHandler.readFromFile("patients/" + username + ".txt");
        String messagesData = FileHandler.readMessagesFromFile("patients/" + username + "_messages.txt");
        if (patientData == null) {
            showError("No data available.");
            return;
        }


        StringBuilder generalInfo = new StringBuilder();
        StringBuilder visitResults = new StringBuilder();
        StringBuilder physicalTestFindings = new StringBuilder();
        StringBuilder prescriptions = new StringBuilder();
        String[] lines = patientData.split("\n");
        int visitNumber = 1;
        for (String line : lines) {
            if (line.startsWith("Phone:")) {
                phoneField.setText(line.split(":")[1].trim());
            } else if (line.startsWith("Address:")) {
                addressField.setText(line.split(":")[1].trim());
            } else if (line.startsWith("Weight-Visit")) {
                visitResults.append("Visit ").append(visitNumber).append(":\n")
                        .append(line).append("\n");
                visitNumber++;
            } else if (line.contains("-Visit")) {
                visitResults.append(line).append("\n");
            } else if (line.startsWith("Physical Test Findings:")) {
                physicalTestFindings.append(line.split(":")[1].trim()).append("\n");
            } else if (line.startsWith("New Prescriptions:")) {
                prescriptions.append(line.split(":")[1].trim()).append("\n");
            } else {
                generalInfo.append(line).append("\n");
            }
        }
        patientInfoArea.setText(generalInfo.toString());
        visitResultsArea.setText(visitResults.toString());
        physicalTestFindingsArea.setText(physicalTestFindings.toString());
        prescriptionsArea.setText(prescriptions.toString());
        messagesArea.setText(messagesData);
    }

    private void updateContactInfo() {
        String patientData = FileHandler.readFromFile("patients/" + username + ".txt");
        if (patientData == null) {
            showError("No data available.");
            return;
        }


        StringBuilder updatedData = new StringBuilder();
        String[] lines = patientData.split("\n");
        for (String line : lines) {
            if (line.startsWith("Phone:")) {
                updatedData.append("Phone: ").append(phoneField.getText()).append("\n");
            } else if (line.startsWith("Address:")) {
                updatedData.append("Address: ").append(addressField.getText()).append("\n");
            } else {
                updatedData.append(line).append("\n");
            }
        }

        FileHandler.writeToFile("patients/" + username + ".txt", updatedData.toString());
        showInfo("Contact information updated successfully.");
    }

    private void sendMessage() {
        String message = newMessageArea.getText().trim();
        if (!message.isEmpty()) {
            FileHandler.writeMessageToFile("patients/" + username + "_messages.txt", "Patient", message);
            showInfo("Message sent.");
            newMessageArea.clear();
            messagesArea.appendText("Patient: " + message + "\n");
        } else {
            showError("Message cannot be empty.");
        }
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
