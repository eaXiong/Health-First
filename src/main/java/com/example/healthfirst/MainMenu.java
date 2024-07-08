package com.example.healthfirst;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MainMenu {

    private VBox view;

    public MainMenu() {
        view = new VBox();
        view.setPadding(new Insets(10));
        view.setSpacing(10);

        Label titleLabel = new Label("Welcome to the HealthFirst Clinic Management System");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button nurseButton = new Button("Nurse View");
        nurseButton.setOnAction(e -> HealthFirst.setView(new NurseLoginView().getView()));

        Button doctorButton = new Button("Doctor View");
        doctorButton.setOnAction(e -> HealthFirst.setView(new DoctorLoginView().getView()));

        Button patientButton = new Button("Patient View");
        patientButton.setOnAction(e -> HealthFirst.setView(new PatientLoginView().getView()));

        view.getChildren().addAll(titleLabel, nurseButton, doctorButton, patientButton);
    }

    public VBox getView() {
        return view;
    }
}
