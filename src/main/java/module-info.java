module com.javafootball {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.javafootball to javafx.fxml;
    exports com.javafootball;
}
