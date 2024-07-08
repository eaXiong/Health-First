module com.example.healthfirst {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.healthfirst to javafx.fxml;
    exports com.example.healthfirst;
}