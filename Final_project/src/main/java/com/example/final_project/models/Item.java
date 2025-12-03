package com.example.final_project.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Item {
    private final IntegerProperty itemId;
    private final StringProperty itemName;
    private final DoubleProperty itemCost;




    public Item(int itemId, String itemName, double itemCost){
        this.itemId = new SimpleIntegerProperty(itemId);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemCost = new SimpleDoubleProperty(itemCost);
    }
    public IntegerProperty itemIdProperty(){
        return itemId;
    }
    public StringProperty itemNameProperty(){
        return itemName;
    }
    public DoubleProperty itemCostProperty(){
        return itemCost;
    }


    public  static List<Item> getItem(){
        List<Item> itemData = FXCollections.observableArrayList();
        String query = "Select itemId, itemName, itemCost from item";
        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                int supplyId = rs.getInt("itemId");
                String supplyName = rs.getString("itemName");
                double supplyCost = rs.getDouble("itemCost");

                itemData.add(new Item(supplyId, supplyName, supplyCost));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemData;
    }
    public static boolean deleteItem(int id) {
        String sql = "DELETE FROM item WHERE itemId = ?";
        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addItem(int id, String name, double cost) {
        String sql = "INSERT INTO item (itemId, itemName, itemCost) VALUES (?, ?, ?)";
        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setDouble(3, cost);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
