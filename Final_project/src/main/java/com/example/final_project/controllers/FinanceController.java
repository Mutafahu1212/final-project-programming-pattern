package com.example.final_project.controllers;

import com.example.final_project.models.Finance;

public class FinanceController {
    double cost;

    Finance finance;
    public Finance getFinance(){
      return finance;
    }

  public void SetProfit(double revenu){
        ItemController itemController=  new ItemController();
        cost = itemController.getTotalItemsCost();
        System.out.println(cost);
        finance = new Finance(revenu,cost);
        finance.setProfit();
  }

  public double getProfit(){
        return finance.getProfit();
  }
  public double getCost(){
        return cost;
  }


}





