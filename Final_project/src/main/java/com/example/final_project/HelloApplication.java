package com.example.final_project;

import com.example.final_project.controllers.SupplyController;
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
    }

    public static void main(String[] args) {
        launch();
    }
}