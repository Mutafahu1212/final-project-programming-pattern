package com.example.final_project.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.final_project.models.Item;

import java.sql.Timestamp;
import java.util.logging.*;

public class ItemController {
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private static final Logger LOG = Logger.getLogger(ItemController.class.getName());
    static{
        LOG.setLevel(Level.ALL);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL); // show all levels in console
        LOG.addHandler(handler);

        LOG.setUseParentHandlers(false);
    }
    //private ObjectProperty<Timestamp> itemDate = new SimpleObjectProperty<>();
    //private TableView<Item> table;
    //Queue<Item> queue = new LinkedList<>();

    //int id;

//    public void SupplyTotal(){
//        for (Supply s : list){
//            System.out.println(s.supplyCostProperty());
//            System.out.println(s.supplyIdProperty());
//            System.out.println(s.supplyNameProperty());
//            queue.add(s.supplyCostProperty().get());
//        }
//        System.out.println(queue);
//    }



    public ItemController(){
        itemList.addAll(Item.getItem());

    }

    public ObservableList<Item> getItem(){
        //LOG.info("Connection successful Between the javaFx  and sql database");
        return itemList;
    }
    public boolean removeItem(int codeBar){
        Item.deleteItem(codeBar);
        //Item newItem = new Item(id, "deete", 0);
        //itemList.add(newItem);
        //return Item.deleteItem(id);

        //int selectedId = tableView.getSelectionModel().getSelectedItem().itemIdProperty().get();
        //Item.deleteItem(selectedId);
        itemList.removeIf(i -> i.itemIdProperty().get() == codeBar);
        LOG.info("Deleted item with codeBar: " + codeBar);

        return true;
    }


    public boolean addNewItem(String name,int quantiy, double cost) {
        Item newItem = Item.addAndGetItem(name,quantiy, cost );
        LOG.info("Add the item into sql database");

            itemList.add(newItem); 
            return true;

    }

//    public boolean addNewItem( String name, double cost) {
//         int generateCodeBar = Item.addItemAndGetCodebar(name, cost);
//         Item item= new Item(generateCodeBar, name, cost, this.itemDate.get());
//         itemList.add(item);
//         return true;
//    }

//        Item codebar = new Item();
//        int code = codebar.itemIdProperty().get();
//        Item s = new Item(name, cost);
//        for (int i = 0; i<itemList.size(); i++){
//
//        }
//
//
//
//        itemList.add(itemList.get(itemList.size()-1));
//        System.out.println("I added the supply");
//        return Item.addItem(name, cost);
//        Item item = new Item();
//        item.addItemWithBarcode(name, cost);
//        itemList.add(item);
//        return true;






//    @Override
//    public String toString() {
//        return "SupplyController{" +
//                "queue=" + queue +
//                '}';
//    }
}
