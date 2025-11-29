package com.example.final_project.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Supply {
    private final IntegerProperty supplyId;
    private final StringProperty supplyName;
    private final DoubleProperty supplyCost;




    public Supply(int supplyId, String supplyName, double supplyCost){
        this.supplyId = new SimpleIntegerProperty(supplyId);
        this.supplyName = new SimpleStringProperty(supplyName);
        this.supplyCost = new SimpleDoubleProperty(supplyCost);
    }
    public IntegerProperty supplyIdProperty(){
        return supplyId;
    }
    public StringProperty supplyNameProperty(){
        return supplyName;
    }
    public DoubleProperty supplyCostProperty(){
        return supplyCost;
    }


    public  static List<Supply> getSupply(){
        List<Supply> supplyData = FXCollections.observableArrayList();
        String query = "Select SupplyId, SupplyName, SupplyCost from supply";
        try (Connection conn = database.ConnectionManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                int supplyId = rs.getInt("SupplyId");
                String supplyName = rs.getString("SupplyName");
                double supplyCost = rs.getDouble("SupplyCost");

                supplyData.add(new Supply(supplyId, supplyName, supplyCost));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplyData;
    }
    public static boolean deleteSupply(int id) {
        String sql = "DELETE FROM supply WHERE SupplyId = ?";
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

    public static boolean addSupply(int id, String name, double cost) {
        String sql = "INSERT INTO supply (SupplyId, SupplyName, SupplyCost) VALUES (?, ?, ?)";
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
