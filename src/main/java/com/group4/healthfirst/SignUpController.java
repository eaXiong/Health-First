package com.group4.healthfirst;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private Button button_sign_up;

    @FXML
    private Button button_sign_in;

    @FXML
    private TextField text_field_username;

    @FXML
    private TextField text_field_password;

    @FXML
    private TextField text_field_re_password;

    @FXML
    private TextField text_field_first_name;

    @FXML
    private TextField text_field_middle_name;

    @FXML
    private TextField text_field_last_name;

    @FXML
    private TextField text_field_dob;

    @FXML
    private ChoiceBox<String> choice_box_sex;

    @FXML
    private TextField text_field_phone;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_sign_up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!text_field_username.getText().trim().isEmpty() && !text_field_password.getText().trim().isEmpty() && text_field_password.getText().equals(text_field_re_password.getText())) {
                    DatabaseUtilities.userRegistration(
                            event,
                            text_field_username.getText(),
                            text_field_password.getText(),
                            text_field_first_name.getText(),
                            text_field_middle_name.getText(),
                            text_field_last_name.getText(),
                            text_field_dob.getText(),
                            choice_box_sex.getValue(),
                            text_field_phone.getText()
                    );
                } else if (!text_field_password.getText().equals(text_field_re_password.getText())) {
                    System.out.println("Passwords do not match");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Passwords do not match");
                    alert.show();
                } else {
                    System.out.println("Please fill in all fields");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all fields");
                    alert.show();
                }
            }
        });

        button_sign_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DatabaseUtilities.changeScene(event, "/com/group4/healthfirst/main.fxml", "Health-First Sign-in Page", null);
            }
        });
    }
    // TODO: Implement this class
}
