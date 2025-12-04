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


//
//    public Timestamp getItemDate() {
//        return itemDate.get();
//    }
//
//


    public Item(int id, String name, double cost, Timestamp date) {
        this.itemId = new SimpleIntegerProperty(id);
        this.itemName = new SimpleStringProperty(name);
        this.itemCost = new SimpleDoubleProperty(cost);
        this.itemDate.set(date);
    }
    public Item( int itemId, String itemName, double itemCost) {
        this.itemId = new SimpleIntegerProperty(itemId);
        this.itemName = new SimpleStringProperty(itemName);
        this.itemCost = new SimpleDoubleProperty(itemCost);
    }
//    public Item(){
//        this.itemId = new SimpleIntegerProperty();
//        this.itemName = new SimpleStringProperty();
//        this.itemCost = new SimpleDoubleProperty();
//    }

//    public static void deleteItem(int selectedId) {
//        String sql = "DELETE FROM item WHERE itemId = ?";
//
//        try (Connection conn = database.ConnectionManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, selectedId);
//            stmt.executeUpdate();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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



//    public Item addItemWithBarcode(String name, double cost){
//        int generatedcode = Item.addItemAndGetCodebar(name, cost);
//        Item newItem = new Item(generatedcode, name, cost);
//        return newItem;
//    }

    public static Item addAndGetItem(String name, double cost) {
        String sql = "INSERT INTO item (itemName, itemCost, itemDate) VALUES (?, ?, ?)";
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters
            stmt.setString(1, name);
            stmt.setDouble(2, cost);
            stmt.setTimestamp(3, now);

            // Execute insert
            stmt.executeUpdate();

            // Get generated ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                // Return full Item object with ID, name, cost, and timestamp
                return new Item(generatedId, name, cost, now);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // insert failed
    }

//    public static int addItemAndGetCodebar(String name, double cost) {
//        String sql = "INSERT INTO item (itemName, itemCost, itemDate) VALUES (?, ?, ?)";
//
//        try (Connection conn = database.ConnectionManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//            stmt.setString(1, name);
//            stmt.setDouble(2, cost);
//            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
//
//            stmt.executeUpdate();
//
//            ResultSet rs = stmt.getGeneratedKeys();
//            if (rs.next()) {
//                return rs.getInt(1);
//            }
//            return -1;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }
//public static Item addAndGetElements(String name, double cost) {
//    String sql = "INSERT INTO item (itemName, itemCost, itemDate) VALUES (?, ?, ?)";
//    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
//
//    try (Connection conn = database.ConnectionManager.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//        stmt.setString(1, name);
//        stmt.setDouble(2, cost);
//        stmt.setTimestamp(3, now);
//
//        stmt.executeUpdate();
//
//        ResultSet rs = stmt.getGeneratedKeys();
//
//        if (rs.next()) {
//            int generatedId = rs.getInt(1);
//
//            // return the new Item object
//            return new Item(generatedId, name, cost, now);
//        }
//
//        return null; // insert failed but no exception
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//        return null;
//    }
//}

    public static List<Item> getItem(){
        List<Item> itemData = FXCollections.observableArrayList();
        String query = "SELECT itemBarCode, itemName, itemCost, itemDate FROM item";

        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while(rs.next()){
                int id = rs.getInt("itemBarCode");
                String name = rs.getString("itemName");
                double cost = rs.getDouble("itemCost");
                Timestamp date = rs.getTimestamp("itemDate");

                itemData.add(new Item(id, name, cost, date));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemData;
    }
    public static boolean deleteItem(int id) {
        String sql = "DELETE FROM item WHERE itemBarCode = ?";
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

//    public static boolean addItem( String name, double cost) {
//        String sql = "INSERT INTO item ( itemName, itemCost) VALUES (?, ?)";
//        try (Connection conn = database.ConnectionManager.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            //stmt.setInt(1, id);
//            stmt.setString(1, name);
//            stmt.setDouble(2, cost);
//            int rows = stmt.executeUpdate();
//            return rows > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }



}
