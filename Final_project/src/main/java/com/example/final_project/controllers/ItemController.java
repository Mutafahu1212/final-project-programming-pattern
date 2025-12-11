package com.example.final_project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.final_project.models.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.*;

public class ItemController {

    private final ObservableList<Item> itemList = FXCollections.observableArrayList();
    private static final Logger LOG = Logger.getLogger(ItemController.class.getName());
    private final Map<String, Item> hashMapItems = new HashMap<>();


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

                int newQuantity = existing.getItemQuantity() + item.getItemQuantity();
                double newCost = existing.getItemCost();

                existing.setItemQuantity(newQuantity);
                existing.setItemCost(newCost);


                Item.updateItem(key, newQuantity, newCost);

            } else {
                hashMapItems.put(key, item);
            }
        }

        itemList.setAll(hashMapItems.values());
    }


    public ObservableList<Item> searchItem(String text) {
        if (text != null && !text.isEmpty()) {
            List<Item> filtered = hashMapItems.values().stream()
                    .filter(i -> i.getItemName().toLowerCase().contains(text.toLowerCase()))
                    .toList();
            return FXCollections.observableArrayList(filtered);
        }
        return itemList;
    }

    public ObservableList<Item> getItem() {
        return itemList;
    }

    public boolean removeItem(String name) {
        Item.deleteItem(name);

        itemList.removeIf(i -> i.getItemName().equals(name));
        hashMapItems.remove(name);

        LOG.info("Deleted item: " + name);
        return true;
    }


    public Double getTotalItemsCost() {
        double totalCost = 0;
        for (Item item : itemList) {
            System.out.println("item costs"+item.getItemCost());
            System.out.println("item quantiy"+ item.getItemQuantity());
            totalCost += item.getItemCost() * item.getItemQuantity();
        }
        System.out.println("the item cost is total"+totalCost);

        return totalCost;
    }



    public boolean addNewItem(String name, int quantity, double cost) {

        Item newItem = Item.addAndGetItem(name, quantity, cost);
        if (newItem == null){
            LOG.warning("Failed to add item in data base");
            return false;
        }


        LOG.info("Inserted new item into SQL.");


        if (hashMapItems.containsKey(name)) {

            Item existing = hashMapItems.get(name);

            int newQuantity = existing.getItemQuantity() + quantity;
            double newCost = cost;

            existing.setItemQuantity(newQuantity);
            existing.setItemCost(newCost);


            Item.updateItem(name, newQuantity, newCost);
            Item.deleteDuplicatesByName(name);

        } else {
            hashMapItems.put(name, newItem);
        }

        itemList.setAll(hashMapItems.values());
        return true;
    }


    public void removingDuplicates() {
        itemList.setAll(hashMapItems.values());
    }
}