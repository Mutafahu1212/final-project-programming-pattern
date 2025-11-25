package com.example.final_project;

import com.example.final_project.controllers.EmployeeController;
import com.example.final_project.controllers.SupplyController;
import com.example.final_project.view.EmployeeView;
import com.example.final_project.view.FinanceView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.final_project.view.SupplyView;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SupplyController controller = new SupplyController();
        SupplyView view = new SupplyView(controller);


        Scene scene = new Scene(view, 500, 300);
        stage.setTitle("Supply Table");
        stage.setScene(scene);
        stage.show();
        controller.SupplyTotal();

        FinanceView  financeView = new FinanceView();
        Scene financeScene = new Scene(financeView,500,300);
        Stage financeStage = new Stage();
        financeStage.setTitle("Finance");
        financeStage.setScene(financeScene);
        financeStage.show();

        /*EmployeeController employeeController = new EmployeeController();
        EmployeeView employeeView = new EmployeeView(employeeController);
        Scene employeeScene = new Scene(employeeView, 500, 500);
        Stage employeeStage = new Stage();
        employeeStage.setScene(employeeScene);
        employeeStage.show();*/


    }

    public static void main(String[] args) {
        launch();
    }
}