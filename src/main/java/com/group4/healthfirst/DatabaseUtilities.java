package com.group4.healthfirst;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtilities {


    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DatabaseUtilities.class.getResource(fxmlFile));
                root = loader.load();

                if (fxmlFile.equals("/com/group4/healthfirst/patient-view.fxml")) {
                    PatientViewController patientViewController = loader.getController();
                    patientViewController.setPatientInformation(username);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(DatabaseUtilities.class.getResource(fxmlFile));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void userRegistration(ActionEvent event, String username, String password, String fname, String mname, String lname, String dob, String sex, String phone) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUser = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://health-first-db.c7cc4e24kmj3.us-east-2.rds.amazonaws.com/health-first", "admin", "group4password");
            psCheckUser = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUser.setString(1, username);
            rs = psCheckUser.executeQuery();

            if (rs.isBeforeFirst()) {
                System.out.println("Username already exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Username is unavailable.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, password, first_name, middle_name, last_name, date_of_birth, sex, phone_number, user_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, fname);
                psInsert.setString(4, mname);
                psInsert.setString(5, lname);
                psInsert.setString(6, dob);
                psInsert.setString(7, sex);
                psInsert.setString(8, phone);
                psInsert.setString(9, "patient");
                psInsert.executeUpdate();

                DatabaseUtilities.changeScene(event, "/com/group4/healthfirst/patient-view.fxml", "Health-First Patient View", username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (psInsert != null) {
                    psInsert.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (psCheckUser != null) {
                    psCheckUser.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void userSignIn(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://health-first-db.c7cc4e24kmj3.us-east-2.rds.amazonaws.com/health-first", "admin", "group4password");
            ps = connection.prepareStatement("SELECT password, user_type FROM users WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("User does not exist.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Sign in failed!");
                alert.show();
            } else {
                while (rs.next()) {
                    if (rs.getString("password").equals(password)) {
                        if (rs.getString("user_type").equals("patient")) {
                            System.out.println("Patient signed in.");
                            DatabaseUtilities.changeScene(event, "/com/group4/healthfirst/patient-view.fxml", "Health-First Patient View", username);
                        } else if (rs.getString("user_type").equals("nurse")) {
                            System.out.println("Nurse signed in.");
                            DatabaseUtilities.changeScene(event, "/com/group4/healthfirst/nurse-view.fxml", "Health-First Nurse View", username);

                        } else if (rs.getString("user_type").equals("doctor")) {
                            System.out.println("Doctor signed in.");
                            DatabaseUtilities.changeScene(event, "/com/group4/healthfirst/doctor-view.fxml", "Health-First Doctor View", username);
                        } else {
                            System.out.println("Dummy signed in.");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Sign in failed!");
                            alert.show();
                        }
                    } else {
                        System.out.println("Incorrect password.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Sign in failed!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

