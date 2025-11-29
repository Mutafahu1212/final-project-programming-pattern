package com.example.final_project;

import com.example.final_project.controllers.EmployeeController;
import com.example.final_project.controllers.SupplyController;
import com.example.final_project.view.MenuView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.final_project.view.SupplyView;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SupplyController supplyController = new SupplyController();
        EmployeeController employeeController = new EmployeeController();
//        SupplyView view = new SupplyView(controller);
        MenuView view = new MenuView(employeeController, supplyController);


        Scene scene = new Scene(view, 500, 300);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
        supplyController.SupplyTotal();
    }

    public static void main(String[] args) {
        launch();
    }
}