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
import org.w3c.dom.Text;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

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

    }
    public void createTable(){
        TableColumn<Item, Integer> itemIdCol = new TableColumn<>("Item Code");
        itemIdCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<Item, String> itemNameCol = new TableColumn<>("Item Name");
        itemNameCol.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, Integer> itemQuantityCol = new TableColumn<>("Item Quantity");
        itemQuantityCol.setCellValueFactory(new PropertyValueFactory<>("itemQuantity"));

        TableColumn<Item, Double> itemCostCol = new TableColumn<>("Item Cost");
        itemCostCol.setCellValueFactory(new PropertyValueFactory<>("itemCost"));

        TableColumn<Item, Timestamp> itemDateCol = new TableColumn<>("Item Date");
        itemDateCol.setCellValueFactory(new PropertyValueFactory<>("itemDate"));

        tableView.getColumns().addAll(itemIdCol,itemNameCol,itemQuantityCol, itemCostCol, itemDateCol);


        }
    private void bindTableData(){
        tableView.setItems(controller.getItem());

    }


    public void addAndDeleteItemsElement(){

        Button deleteBtn = PaneFactory.createButton("Delete");

        this.getChildren().add(deleteBtn);
        deleteBtn.setOnAction(event->{

            String selectedName = tableView.getSelectionModel().getSelectedItem().itemNameProperty().get();
            Item.deleteItem(selectedName);
            controller.removeItem(selectedName);







        });

        TextField nameField = PaneFactory.createTextField("name");
        TextField quantityField = PaneFactory.createTextField("quantity");
        TextField costField = PaneFactory.createTextField("cost");
        Button addBtn =PaneFactory.createButton("Add Item");
        HBox hBox = PaneFactory.createHBox(3,nameField,quantityField,costField,addBtn, deleteBtn
        );


        this.getChildren().add(hBox);

        addBtn.setOnAction(event ->{
            controller.addNewItem(
                    nameField.getText(),Integer.parseInt(quantityField.getText()),
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

        searchBtn.setOnAction(event -> {
            String firstName = searchTextField.getText();


            ObservableList<Item> filtered = controller.searchItem(firstName);

            if (!firstName.isEmpty()) {
                tableView.setItems(filtered);
            }
            else
                tableView.setItems(controller.getItem());
        });

    }
}
