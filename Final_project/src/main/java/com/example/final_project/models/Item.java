package com.example.final_project.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Item {

    private final IntegerProperty itemId;
    private final StringProperty itemName;
    private final IntegerProperty itemQuantity;
    private final DoubleProperty itemCost;
    private final ObjectProperty<Timestamp> itemDate = new SimpleObjectProperty<>();


    public Item(int id, String name, int quantity, double cost, Timestamp date) {
        this.itemId = new SimpleIntegerProperty(id);
        this.itemName = new SimpleStringProperty(name);
        this.itemQuantity = new SimpleIntegerProperty(quantity);
        this.itemCost = new SimpleDoubleProperty(cost);
        this.itemDate.set(date);
    }


    public IntegerProperty itemIdProperty() {
        return itemId;
    }

    public StringProperty itemNameProperty() {
        return itemName;
    }

    public IntegerProperty itemQuantityProperty() {
        return itemQuantity;
    }

    public DoubleProperty itemCostProperty() {
        return itemCost;
    }

    public ObjectProperty<Timestamp> itemDateProperty() {
        return itemDate;
    }


    public int getItemId() {
        return itemId.get();
    }

    public String getItemName() {
        return itemName.get();
    }

    public int getItemQuantity() {
        return itemQuantity.get();
    }

    public double getItemCost() {
        return itemCost.get();
    }

    public Timestamp getItemDate() {
        return itemDate.get();
    }


    public void setItemName(String name) {
        this.itemName.set(name);
    }

    public void setItemQuantity(int quantity) {
        this.itemQuantity.set(quantity);
    }

    public void setItemCost(double cost) {
        this.itemCost.set(cost);
    }

    public void setItemDate(Timestamp date) {
        this.itemDate.set(date);
    }


    public static Item addAndGetItem(String name, int quantity, double cost) {
        String sql = "INSERT INTO item (itemName, itemQuantity, itemCost, itemDate) VALUES (?, ?, ?, ?)";
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setInt(2, quantity);
            stmt.setDouble(3, cost);
            stmt.setTimestamp(4, now);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                return new Item(generatedId, name, quantity, cost, now);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Item> getItem() {
        ObservableList<Item> itemData = FXCollections.observableArrayList();
        String query = "SELECT itemBarCode, itemName, itemQuantity, itemCost, itemDate FROM item";

        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("itemBarCode");
                String name = rs.getString("itemName");
                int quantity = rs.getInt("itemQuantity");
                double cost = rs.getDouble("itemCost");
                Timestamp date = rs.getTimestamp("itemDate");

                itemData.add(new Item(id, name, quantity, cost, date));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemData;
    }

    public static List<Item> searchItemByName(String name) {
        List<Item> filtered = new ArrayList<>();
        String sql = "SELECT itemBarCode, itemName, itemQuantity, itemCost, itemDate FROM item WHERE itemName LIKE ?";

        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String fuzzy = "%" + String.join("%", name.split("")) + "%" +"%" + String.join("%", name.split("")) + "%";

            ps.setString(1, fuzzy);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("itemBarCode");
                    String itemName = rs.getString("itemName");
                    int quantity = rs.getInt("itemQuantity");
                    double cost = rs.getDouble("itemCost");
                    Timestamp date = rs.getTimestamp("itemDate");

                    filtered.add(new Item(id, itemName, quantity, cost, date));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return filtered;
    }


    public static boolean deleteItem(String name) {
        String sql = "DELETE FROM item WHERE itemName = ?";
        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateItem(String name, int quantity, double cost) {
        String sql = "UPDATE item SET itemQuantity = ?, itemCost = ? WHERE itemName = ?";

        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quantity);
            stmt.setDouble(2, cost);
            stmt.setString(3, name);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteDuplicatesByName(String name) {
        String sql = """
        DELETE FROM item 
        WHERE itemName = ? 
        AND itemBarCode NOT IN (
            SELECT MIN(itemBarCode) FROM item WHERE itemName = ?
        );
        """;

        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}