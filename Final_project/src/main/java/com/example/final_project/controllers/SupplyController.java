package com.example.final_project.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.final_project.models.Supply;
import javafx.scene.control.TableView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class SupplyController {
    private TableView<Supply> table;
    Queue<Double> queue = new LinkedList<>();
    ObservableList<Supply> list;

    public void SupplyTotal(){
        for (Supply s : list){
            System.out.println(s.supplyCostProperty());
            System.out.println(s.supplyIdProperty());
            System.out.println(s.supplyNameProperty());
            queue.add(s.supplyCostProperty().get());
        }
        System.out.println(queue);
    }

    public SupplyController(){
        list = Supply.getSupply();
    }

    public ObservableList<Supply> getSupply(){
        System.out.println("i receave the supply");
        return Supply.getSupply();
    }
    public boolean removeSupply(int id){
        return Supply.deleteSupply(id);
    }
    public boolean addNewSupply(int id, String name, double cost) {
        System.out.println("I added the suppply");
        return Supply.addSupply(id, name, cost);
    }

    @Override
    public String toString() {
        return "SupplyController{" +
                "queue=" + queue +
                '}';
    }
}
