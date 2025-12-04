package com.example.final_project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.final_project.models.Item;

public class ItemController {
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();
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


    public boolean addNewItem( String name, double cost) {
         int generateCodeBar = Item.addItemElements(name, cost);
         Item item= new Item(generateCodeBar, name, cost);
         itemList.add(item);
         return true;


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


    }



//    @Override
//    public String toString() {
//        return "SupplyController{" +
//                "queue=" + queue +
//                '}';
//    }
}
