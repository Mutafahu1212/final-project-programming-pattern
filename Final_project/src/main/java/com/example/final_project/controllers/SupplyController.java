package com.example.final_project.controllers;

import com.example.final_project.view.EmployeeView;
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
    private final ObservableList<Supply> supplyList = FXCollections.observableArrayList();
    private TableView<Supply> table;
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

    public SupplyController(){
        supplyList.addAll(Supply.getSupply());
    }

    public ObservableList<Supply> getSupply(){
        System.out.println("i receave the supply");
        return supplyList;
    }
    public boolean removeSupply(int id){
        Supply newSupply = new Supply(id, "deete", 0);
        supplyList.add(newSupply);
        return Supply.deleteSupply(id);
    }


    public boolean addNewSupply(int id, String name, double cost) {


        Supply s = new Supply(id, name, cost);




        supplyList.add(s);
        System.out.println("I added the supply");
        return Supply.addSupply(id, name, cost);
    }



//    @Override
//    public String toString() {
//        return "SupplyController{" +
//                "queue=" + queue +
//                '}';
//    }
}
