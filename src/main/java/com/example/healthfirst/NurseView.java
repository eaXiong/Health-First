package com.example.healthfirst;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class NurseView {

    private VBox view;
    private TextField patientIDField;
    private TextArea immunizationHistoryArea;
    private TextArea previousHealthIssuesArea;
    private TextArea medicationsArea;
    private TextField weightField;
    private TextField heightField;
    private TextField bodyTemperatureField;
    private TextField bloodPressureField;
    private TextField allergiesField;
    private TextField healthConcernsField;
    private TextArea messagesArea;
    private TextArea replyMessageArea;
    private Button loadButton;
    private Button saveButton;
    private int visitCount = 0;

    public NurseView() {
        view = new VBox();
        view.setPadding(new Insets(10));
        view.setSpacing(10);

        Label titleLabel = new Label("Nurse - Patient Information");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        patientIDField = new TextField();
        patientIDField.setPromptText("Patient Username");

        immunizationHistoryArea = new TextArea();
        immunizationHistoryArea.setPromptText("Immunization History");
        immunizationHistoryArea.setEditable(false);

        previousHealthIssuesArea = new TextArea();
        previousHealthIssuesArea.setPromptText("Previous Health Issues");
        previousHealthIssuesArea.setEditable(false);

        medicationsArea = new TextArea();
        medicationsArea.setPromptText("Medications");
        medicationsArea.setEditable(false);

        weightField = new TextField();
        weightField.setPromptText("Weight");

        heightField = new TextField();
        heightField.setPromptText("Height");

        bodyTemperatureField = new TextField();
        bodyTemperatureField.setPromptText("Body Temperature");

        bloodPressureField = new TextField();
        bloodPressureField.setPromptText("Blood Pressure");

        allergiesField = new TextField();
        allergiesField.setPromptText("Known Allergies");

        healthConcernsField = new TextField();
        healthConcernsField.setPromptText("Health Concerns");

        messagesArea = new TextArea();
        messagesArea.setEditable(false);
        messagesArea.setPromptText("Messages");

        replyMessageArea = new TextArea();
        replyMessageArea.setPromptText("Reply to patient");

        loadButton = new Button("Load Patient Info");
        loadButton.setOnAction(e -> loadPatientInfo());

        saveButton = new Button("Save Health Info");
        saveButton.setOnAction(e -> saveHealthInfo());

        Button replyButton = new Button("Reply to Patient");
        replyButton.setOnAction(e -> {
            String patientID = patientIDField.getText();
            String message = replyMessageArea.getText().trim();
            if (!message.isEmpty()) {
                FileHandler.writeMessageToFile("patients/" + patientID + "_messages.txt", "Nurse", message);
                showInfo("Reply sent.");
                replyMessageArea.clear();
                messagesArea.appendText("Nurse: " + message + "\n");
            } else {
                showError("Reply cannot be empty.");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> HealthFirst.setView(new MainMenu().getView()));

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Patient Username:"), 0, 0);
        gridPane.add(patientIDField, 1, 0);
        gridPane.add(loadButton, 1, 1);
        gridPane.add(new Label("Immunization History:"), 0, 2);
        gridPane.add(immunizationHistoryArea, 1, 2);
        gridPane.add(new Label("Previous Health Issues:"), 0, 3);
        gridPane.add(previousHealthIssuesArea, 1, 3);
        gridPane.add(new Label("Medications:"), 0, 4);
        gridPane.add(medicationsArea, 1, 4);
        gridPane.add(new Label("Weight:"), 0, 5);
        gridPane.add(weightField, 1, 5);
        gridPane.add(new Label("Height:"), 0, 6);
        gridPane.add(heightField, 1, 6);
        gridPane.add(new Label("Body Temperature:"), 0, 7);
        gridPane.add(bodyTemperatureField, 1, 7);
        gridPane.add(new Label("Blood Pressure:"), 0, 8);
        gridPane.add(bloodPressureField, 1, 8);
        gridPane.add(new Label("Known Allergies:"), 0, 9);
        gridPane.add(allergiesField, 1, 9);
        gridPane.add(new Label("Health Concerns:"), 0, 10);
        gridPane.add(healthConcernsField, 1, 10);
        gridPane.add(saveButton, 1, 11);
        gridPane.add(new Label("Messages:"), 0, 12);
        gridPane.add(messagesArea, 1, 12);
        gridPane.add(new Label("Reply Message:"), 0, 13);
        gridPane.add(replyMessageArea, 1, 13);
        gridPane.add(replyButton, 1, 14);
        gridPane.add(backButton, 1, 15);

        view.getChildren().addAll(titleLabel, gridPane);
    }

    public VBox getView() {
        return view;
    }

    private void loadPatientInfo() {
        String patientID = patientIDField.getText();
        String patientData = FileHandler.readFromFile("patients/" + patientID + ".txt");
        String messagesData = FileHandler.readMessagesFromFile("patients/" + patientID + "_messages.txt");

        if (patientData == null) {
            showError("Patient ID not found.");
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
            } else if (line.startsWith("Visit Count:")) {
                visitCount = Integer.parseInt(line.split(":")[1].trim());
            }
        }
        messagesArea.setText(messagesData);
    }

    private void saveHealthInfo() {
        String patientID = patientIDField.getText();
        String patientData = FileHandler.readFromFile("patients/" + patientID + ".txt");

        if (patientData == null) {
            showError("Patient ID not found.");
            return;
        }

        visitCount++;
        String visitData = String.format(
                "Weight-Visit%d: %s\nHeight-Visit%d: %s\nBody Temperature-Visit%d: %s\nBlood Pressure-Visit%d: %s\nKnown Allergies-Visit%d: %s\nHealth Concerns-Visit%d: %s\n",
                visitCount, weightField.getText(), visitCount, heightField.getText(), visitCount, bodyTemperatureField.getText(),
                visitCount, bloodPressureField.getText(), visitCount, allergiesField.getText(), visitCount, healthConcernsField.getText()
        );

        String updatedData = patientData + visitData + "Visit Count: " + visitCount + "\n";

        FileHandler.writeToFile("patients/" + patientID + ".txt", updatedData);
        showInfo("Health information saved successfully.");
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
