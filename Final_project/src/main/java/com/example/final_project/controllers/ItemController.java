package com.example.final_project.controllers;

import com.example.final_project.view.ItemView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.final_project.models.Item;
import javafx.scene.control.TableView;

import java.util.LinkedList;
import java.util.Queue;

public class ItemController {
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private TableView<Item> table;
    Queue<Double> queue = new LinkedList<>();
    int id;

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
        System.out.println("i receave the supply");
        return itemList;
    }
    public boolean removeItem(int codeBar){
        //Item newItem = new Item(id, "deete", 0);
        //itemList.add(newItem);
        //return Item.deleteItem(id);

        //int selectedId = tableView.getSelectionModel().getSelectedItem().itemIdProperty().get();
        //Item.deleteItem(selectedId);
        for (Item i : itemList) {
            if (i.itemIdProperty().get() == codeBar) {
                itemList.remove(i);
            }

        }
        return true;
    }


    public boolean addNewItem(int id, String name, double cost) {

        Item s = new Item(id, name, cost);

        itemList.add(s);
        System.out.println("I added the supply");
        return Item.addItem(id, name, cost);
    }



//    @Override
//    public String toString() {
//        return "SupplyController{" +
//                "queue=" + queue +
//                '}';
//    }
}
