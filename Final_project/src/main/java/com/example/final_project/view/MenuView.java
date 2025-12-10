package com.example.final_project.view;

import com.example.final_project.controllers.EmployeeController;
import com.example.final_project.controllers.FinanceController;
import com.example.final_project.controllers.ItemController;
import com.example.final_project.factory.PaneFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MenuView extends VBox {
    private final EmployeeController employeeController;
    private final ItemController itemController;
    private final FinanceController financeController;


    public MenuView(EmployeeController employeeController, ItemController itemController, FinanceController financeController){
        this.employeeController = employeeController;
        this.itemController = itemController;
        this.financeController = financeController;
        this.employeeButton();
        this.itemButton();
        this.fianceButton();
        this.exitButton();
        this.setAlignment(Pos.CENTER);
        this.setSpacing(40);
    }

    public void exitButton(){
        Button exitButton = PaneFactory.exitButton();
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

    public void itemButton(){
        Button supplyButton = PaneFactory.createButton("Item Info");
        this.getChildren().add(supplyButton);

        supplyButton.setOnAction(
                event->{
                    Scene scene = supplyButton.getScene();
                    scene.setRoot(new ItemView(itemController));
                }
        );
    }

    public void fianceButton(){
        Button financeButton = PaneFactory.createButton("Finance");
        this.getChildren().add(financeButton);

        financeButton.setOnAction(event ->{
            Scene scene = financeButton.getScene();
            scene.setRoot(new FinanceView(financeController));
        });
    }
}
