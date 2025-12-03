module com.example.final_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.example.final_project to javafx.fxml;
    opens com.example.final_project.models to javafx.fxml, javafx.base;
    exports com.example.final_project;
}