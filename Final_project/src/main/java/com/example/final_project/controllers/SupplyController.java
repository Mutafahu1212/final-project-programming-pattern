package com.example.final_project.controllers;

import javafx.collections.ObservableList;
import com.example.final_project.models.Supply;

public class SupplyController {

    public SupplyController(){

    }

    public ObservableList<Supply> getSupply(){
        return Supply.getSupply();
    }
    public boolean removeSupply(int id){
        return Supply.deleteSupply(id);
    }
    public boolean addNewSupply(int id, String name, double cost) {
        return Supply.addSupply(id, name, cost);
    }
}
