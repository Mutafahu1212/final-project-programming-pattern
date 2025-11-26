package com.example.final_project.view;

import com.example.final_project.controllers.SupplyController;
import com.example.final_project.factory.PaneFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
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
        this.createSearchBar();
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
    private void createSearchBar(){
        Label searchlabel = new Label("Supply Name");
        this.getChildren().add(searchlabel);

        TextField searchTextField = new TextField();
        this.getChildren().add(searchTextField);

        Button searchBtn = new Button("Search");
        HBox searchbox = new HBox(10);
        searchbox.getChildren().addAll(searchlabel,searchTextField, searchBtn);
        this.getChildren().add(searchbox);

        searchBtn.setOnAction(event ->{
            String firstName = searchTextField.getText();
            if (firstName == null) firstName = "";

            ObservableList<Supply> searchFirstName = FXCollections.observableArrayList();

            for (Supply s : controller.getSupply()) {
                String empName = String.valueOf(s.supplyNameProperty().get());
                if (empName.equalsIgnoreCase(firstName)) {
                    searchFirstName.add(s);
                    tableView.setItems(searchFirstName);
                    break;
                }
                tableView.setItems(controller.getSupply());
            }
        });
    }
}
