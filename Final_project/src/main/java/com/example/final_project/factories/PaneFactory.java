package com.example.final_project.factories;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PaneFactory {

    public static HBox createHBox(double spacing, Node... child){
        HBox box = new HBox(spacing);
        box.getChildren().addAll(child);
        return box;
    }
    public static Button createButton(String text){
        return new Button(text);
    }

    public static TextField createTextField(String prompt){
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setPrefWidth(100);
        return tf;
    }
}
