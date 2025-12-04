package com.example.final_project.view;

import com.example.final_project.controllers.ItemController;
import com.example.final_project.factory.PaneFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.example.final_project.models.Item;

public class ItemView extends VBox {
private final TableView<Item> tableView;
private final ItemController controller;





    public ItemView(ItemController controller) {
        this.tableView = new TableView<>();
        this.controller = controller;
        this.createSearchBar();
        this.createTable();
        this.bindTableData();
        this.getChildren().add(tableView);
        this.addAndDeleteItemsElement();
        //this.delete();



    }



    public void createTable(){
        TableColumn<Item, Integer> itemIdCol = new TableColumn<>("Item Id");
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Item, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, Double> itemCostCol = new TableColumn<>("Item Cost");
        itemCostCol.setCellValueFactory(new PropertyValueFactory<>("itemCost"));

        tableView.getColumns().addAll(itemIdCol,itemNameCol,itemCostCol);


        }
    private void bindTableData(){
        tableView.setItems(controller.getItem());

    }

//    public void delete(){
//
//        Button deleteBtn = PaneFactory.createButton("Delete");
//
//        this.getChildren().add(deleteBtn);
//        deleteBtn.setOnAction(event->{
//
//            int selectedId = tableView.getSelectionModel().getSelectedItem().itemIdProperty().get();
//            Item.deleteItem(selectedId);
//            controller.removeItem(selectedId);
//
//
//
//
//            //boolean selectedId = tableView.getSelectionModel().getSelectedItem();
//
//
//        });
//    }
    public void addAndDeleteItemsElement(){

        Button deleteBtn = PaneFactory.createButton("Delete");

        this.getChildren().add(deleteBtn);
        deleteBtn.setOnAction(event->{

            int selectedId = tableView.getSelectionModel().getSelectedItem().itemIdProperty().get();
            Item.deleteItem(selectedId);
            controller.removeItem(selectedId);




            //boolean selectedId = tableView.getSelectionModel().getSelectedItem();


        });
        //TextField idField = PaneFactory.createTextField("id ");
        TextField nameField = PaneFactory.createTextField("name");
        TextField costField = PaneFactory.createTextField("cost");
        Button addBtn =PaneFactory.createButton("Add Item");
        HBox hBox = PaneFactory.createHBox(3,nameField,costField,addBtn, deleteBtn
        );


        this.getChildren().add(hBox);

        addBtn.setOnAction(event ->{
            controller.addNewItem(
                    nameField.getText(),
                    Double.parseDouble(costField.getText()));
        });

    }

    private void createSearchBar(){
        Label searchlabel = new Label("Item Name");
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

            ObservableList<Item> searchFirstName = FXCollections.observableArrayList();

            for (Item s : controller.getItem()) {
                String empName = String.valueOf(s.itemNameProperty().get());
                if (empName.equalsIgnoreCase(firstName)) {
                    searchFirstName.add(s);
                    tableView.setItems(searchFirstName);
                    break;
                }
                tableView.setItems(controller.getItem());
            }
        });
    }
}
