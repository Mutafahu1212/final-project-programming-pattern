package com.example.final_project;

import com.example.final_project.controllers.EmployeeController;
import com.example.final_project.controllers.FinanceController;
import com.example.final_project.controllers.ItemController;
import com.example.final_project.view.MenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ItemController itemController = new ItemController();
        EmployeeController employeeController = new EmployeeController();
        FinanceController financeController = new FinanceController();

        MenuView view = new MenuView(stage, employeeController, itemController, financeController);

        Scene scene = new Scene(view, 500, 300);
        stage.setTitle("Menu");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}