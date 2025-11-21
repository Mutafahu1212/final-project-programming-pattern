package com.example.final_project.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.final_project.models.Supply;
import javafx.scene.control.TableView;

public class SupplyController {
    private TableView<Supply> table;

    public void initialize(){
        startRefreshThread();
    }

    private void startRefreshThread(){
        Thread refrecher = new Thread(() ->{
           ObservableList<Supply> lastData = FXCollections.observableArrayList();
           while(true){
               ObservableList<Supply> currentData = getSupply();
               if(!currentData.equals(lastData)){
                   Platform.runLater(()-> table.setItems(currentData));
                   lastData = FXCollections.observableArrayList(currentData);
               }
               try {Thread.sleep(300);} catch (Exception ignored) {}
           }
        });
        refrecher.setDaemon(true);
        refrecher.start();
    }

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
