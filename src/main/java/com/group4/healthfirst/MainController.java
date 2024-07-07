package com.group4.healthfirst;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button button_sign_in;

    @FXML
    private Button button_sign_up;

    @FXML
    private TextField text_field_username;

    @FXML
    private TextField text_field_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_sign_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DatabaseUtilities.userSignIn(event, text_field_username.getText(), text_field_password.getText());
            }
        });

        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DatabaseUtilities.changeScene(event, "/com/group4/healthfirst/sign-up.fxml", "Health-First Sign-up Page", null);
            }
        });
    }
}