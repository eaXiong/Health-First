package com.group4.healthfirst;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PatientViewController implements Initializable{

    @FXML
    private Button button_logout;

    @FXML
    private Label label_welcome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DatabaseUtilities.changeScene(event, "/com/group4/healthfirst/main.fxml", "Health-First Sign-in Page", null);
            }
        });
    }

    public void setPatientInformation(String patientName) {
        label_welcome.setText("Welcome, " + patientName );
    }
}
