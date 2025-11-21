package com.example.final_project.view;

import com.example.final_project.controllers.SupplyController;
import com.example.final_project.factory.PaneFactory;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.example.final_project.models.Supply;

public class SupplyView extends VBox {
private final TableView<Supply> tableView;
private final SupplyController controller;



    public SupplyView(SupplyController controller) {
        this.tableView = new TableView<>();
        this.controller = controller;
        this.createTable();
        this.bindTableData();
        this.getChildren().add(tableView);
        this.addSupplyElement();
        this.delete();

    }


    public void createTable(){
        TableColumn<Supply, Integer> supplyIdCol = new TableColumn<>("Supply Id");
        supplyIdCol.setCellValueFactory(new PropertyValueFactory<>("supplyId"));

        TableColumn<Supply, String> supplyNameCol = new TableColumn<>("Supply Name");
        supplyNameCol.setCellValueFactory(new PropertyValueFactory<>("supplyName"));

        TableColumn<Supply, Double> supplyCostCol = new TableColumn<>("Supply Cost");
        supplyCostCol.setCellValueFactory(new PropertyValueFactory<>("supplyCost"));

        tableView.getColumns().addAll(supplyIdCol,supplyNameCol,supplyCostCol);


        }
    private void bindTableData(){
        tableView.setItems(controller.getSupply());

    }

    public void delete(){
        Button deleteBtn = PaneFactory.createButton("Delete");



        this.getChildren().add(deleteBtn);


        deleteBtn.setOnAction(event->{
            int selectedId = tableView.getSelectionModel().getSelectedItem().supplyIdProperty().get();
            Supply.deleteSupply(selectedId);
        });
    }
    public void addSupplyElement(){
        TextField idField = PaneFactory.createTextField("id ");
        TextField nameField = PaneFactory.createTextField("name");
        TextField costField = PaneFactory.createTextField("cost");
        Button addBtn =PaneFactory.createButton("Add Supply");
        HBox hBox = PaneFactory.createHBox(3,idField ,nameField,costField,addBtn
        );


        this.getChildren().add(hBox);

        addBtn.setOnAction(event ->{

            controller.addNewSupply(Integer.parseInt(idField.getText()),
                    nameField.getText(),
                    Double.parseDouble(costField.getText()));
        });


    }
}
