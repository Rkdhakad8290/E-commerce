module com.example.ecommerceweb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ecommerceweb to javafx.fxml;
    exports com.example.ecommerceweb;
}