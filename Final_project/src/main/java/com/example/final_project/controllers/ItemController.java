package com.example.final_project.controllers;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.final_project.models.Item;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.*;


import static com.example.final_project.models.Item.deleteItem;
import static com.example.final_project.models.Item.searchItemByName;

public class ItemController {
    private final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private static final Logger LOG = Logger.getLogger(ItemController.class.getName());
    private Map<String, Item> hashMapItems = new HashMap<>();
    Double TotalCost;

    static {
        LOG.setLevel(Level.ALL);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.ALL);
        LOG.addHandler(handler);

        LOG.setUseParentHandlers(false);
    }


    public ItemController() {
        List<Item> items = Item.getItem();
        for (int i = items.size() - 1; i >= 0; i--) {
            Item item = items.get(i);
            String key = item.getItemName();
            if (hashMapItems.containsKey(key)) {
                Item existing = hashMapItems.get(key);
                existing.setItemQuantity(existing.getItemQuantity() + item.getItemQuantity());
            } else {
                hashMapItems.put(key, item);
            }
        }
        itemList.setAll(hashMapItems.values());
    }

    public ObservableList<Item> searchItem(String firstName) {
        if (firstName != null && !firstName.isEmpty()) {

            List<Item> filteredList = hashMapItems.values().stream()
                    .filter(item -> item.getItemName().toLowerCase().contains(firstName.toLowerCase()))
                    .toList();
            return FXCollections.observableArrayList(filteredList);
        } else {
            return getItem();
        }
    }

    public ObservableList<Item> getItem() {
        return itemList;
    }

    public boolean removeItem(String name) {
        Item.deleteItem(name);

        itemList.removeIf(i -> i.itemNameProperty().get() == name);
        LOG.info("Deleted item with codeBar: " + name);

        return true;
    }

    public Double getTotalItemsCost(){
        for (Item item: itemList){
            TotalCost += item.itemCostProperty().get();
        }
        System.out.println(TotalCost);
        return TotalCost;
    }



    public boolean addNewItem(String name, int quantity, double cost) {

        Item newItem = Item.addAndGetItem(name, quantity, cost);
        LOG.info("Add the item into sql database");
        itemList.add(newItem);
        hashMapItems.clear();

        for (int i = itemList.size() - 1; i >= 0; i--) {
            Item item = itemList.get(i);
            String key = item.getItemName();

            if (hashMapItems.containsKey(key)) {
                Item existing = hashMapItems.get(key);
                int totalQuantity = existing.getItemQuantity() + item.getItemQuantity();

                    existing.setItemQuantity(totalQuantity);

            } else {
                hashMapItems.put(key, item);
            }
        }

        itemList.setAll(hashMapItems.values());
        return true;
    }
    public void removingDuplicates() {
        itemList.setAll(hashMapItems.values());
    }

}
