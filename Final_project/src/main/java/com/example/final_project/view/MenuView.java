package com.example.final_project.view;

import com.example.final_project.controllers.EmployeeController;
import com.example.final_project.controllers.SupplyController;
import com.example.final_project.factory.PaneFactory;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView extends VBox {
    private final EmployeeController employeeController;
    private final SupplyController supplyController;

    public MenuView(EmployeeController employeeController, SupplyController supplyController){
        this.employeeController = employeeController;
        this.supplyController = supplyController;
        this.employeeButton();
        this.supplyButton();
        this.fianceButton();
        this.exitButton();
    }

    public void exitButton(){
        Button exitButton = PaneFactory.createButton("Exit");
        this.getChildren().add(exitButton);
        exitButton.setOnAction(event -> {Platform.exit();});
    }

    public void employeeButton(){
        Button employeeButton = PaneFactory.createButton("Employee Info");
        this.getChildren().add(employeeButton);

        employeeButton.setOnAction(
                event->{
                    Scene scene = employeeButton.getScene();
                    scene.setRoot(new EmployeeView(employeeController));
                }
        );
    }

    public void supplyButton(){
        Button supplyButton = PaneFactory.createButton("Supply Info");
        this.getChildren().add(supplyButton);

        supplyButton.setOnAction(
                event->{
                    Scene scene = supplyButton.getScene();
                    scene.setRoot(new SupplyView(supplyController));
                }
        );
    }

    public void fianceButton(){
        Button financeButton = PaneFactory.createButton("Finance");
        this.getChildren().add(financeButton);
    }
}
