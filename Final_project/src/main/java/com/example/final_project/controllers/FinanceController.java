package com.example.final_project.controllers;

import com.example.final_project.models.Finance;

public class FinanceController {
    double cost;
    ItemController itemController;
    EmployeeController employeeController;

    Finance finance;
    public Finance getFinance(){
      return finance;
    }

  public void setProfit(double revenu){
        itemController=  new ItemController();
        employeeController = new EmployeeController();
        System.out.println("employee salary total "+employeeController.totalSalary());
        cost = itemController.getTotalItemsCost()+employeeController.totalSalary();
        System.out.println(cost);
        finance = new Finance(revenu,cost);
        finance.setProfit();
  }

  public double getProfit(){
        return finance.getProfit();
  }
  public double getCost(){
        return finance.getCost();
  }


}





