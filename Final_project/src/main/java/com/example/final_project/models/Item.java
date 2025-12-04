package com.example.final_project.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;



public class Item {



    private final IntegerProperty itemId;
    private final StringProperty itemName;
    private final DoubleProperty itemCost;
    private final ObjectProperty<Timestamp> itemDate = new SimpleObjectProperty<>();




    public Item( String itemName, double itemCost) {
        this.itemId = new SimpleIntegerProperty();
        this.itemName = new SimpleStringProperty(itemName);
        this.itemCost = new SimpleDoubleProperty(itemCost);
    }
    public Item( int itemId, String itemName, double itemCost) {
        this.itemId = new SimpleIntegerProperty(itemId);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemCost = new SimpleDoubleProperty(itemCost);
    }
    public Item(){
        this.itemId = new SimpleIntegerProperty();
        this.itemName = new SimpleStringProperty();
        this.itemCost = new SimpleDoubleProperty();
    }

    public static void deleteItem(int selectedId) {
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
    public ObjectProperty<Timestamp> itemDateProperty(){
        return itemDate;
    }



    public Item addItemWithBarcode(String name, double cost){
        int generatedcode = Item.addItemElements(name, cost);
        Item newItem = new Item(generatedcode, name, cost);
        return newItem;
    }


    public static int addItemElements(String name, double cost) {
        String sql = "INSERT INTO item (itemName, itemCost, itemDate) VALUES (?, ?, ?)";

        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setDouble(2, cost);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // save current datetime as Timestamp

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public  static List<Item> getItem(){
        List<Item> itemData = FXCollections.observableArrayList();
        String query = "Select itemBarCode, itemName, itemCost from item";
        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                int supplyId = rs.getInt("itemBarCode");
                String supplyName = rs.getString("itemName");
                double supplyCost = rs.getDouble("itemCost");

                itemData.add(new Item(supplyId, supplyName, supplyCost));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemData;
    }
//    public static boolean deleteItem(int id) {
//        String sql = "DELETE FROM item WHERE itemId = ?";
//        try (Connection conn = database.ConnectionManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            int rows = stmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public static boolean addItem( String name, double cost) {
        String sql = "INSERT INTO item ( itemName, itemCost) VALUES (?, ?)";
        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            //stmt.setInt(1, id);
            stmt.setString(1, name);
            stmt.setDouble(2, cost);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
