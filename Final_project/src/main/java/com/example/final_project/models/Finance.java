package com.example.final_project.models;

public class Finance {

    double revenue;
    double cost;
    double profit;

    public Finance(double revenue, double cost) {
        this.revenue = revenue;
        this.cost = cost;

    }

    public double getRevenue() {
        return revenue;
    }

    public double getCost() {
        return cost;
    }




    public void setProfit() {
        this.profit = revenue - cost;
    }
}
