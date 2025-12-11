package com.example.final_project.factory;

import com.example.final_project.controllers.EmployeeController;
import com.example.final_project.controllers.FinanceController;
import com.example.final_project.controllers.ItemController;
import com.example.final_project.view.EmployeeView;
import com.example.final_project.view.MenuView;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PaneFactory {

    public static HBox createHBox(double spacing, Node... child){
        HBox box = new HBox(spacing);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(child);
        return box;
    }

    public static Button createButton(String text){
        return new Button(text);
    }

    public static Button exitButton(){
         Button exit = new Button("Exit");
         exit.setStyle("-fx-background-color: Red");
         return exit;
    }

    public static Button backButton(Stage stage){
        Button button = new Button("BACK");
        button.setStyle("-fx-background-color: LIGHTSKYBLUE");

        button.setOnAction(actionEvent -> {
            ItemController itemController = new ItemController();
            EmployeeController employeeController = new EmployeeController();
            FinanceController financeController = new FinanceController();
            MenuView menuView = new MenuView( stage,employeeController, itemController,financeController);
            Scene scene = new Scene(menuView, 500, 300);
            stage.setTitle("Menu");
            stage.setScene(scene);
            stage.show();
        });

        return button;
    }

    public static TextField createTextField(String prompt){
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(100);
        return tf;
    }

    public static VBox createVBox(double spacing, Node... child){
        VBox box = new VBox(spacing);
        box.getChildren().addAll(child);
        return box;
    }

    public static TextField createTextFieldUneditable(String prompt){
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(100);
        tf.setEditable(false);
        return tf;
    }

}
