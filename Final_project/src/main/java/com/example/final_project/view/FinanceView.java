package com.example.final_project.view;

import com.example.final_project.controllers.FinanceController;
import com.example.final_project.factory.PaneFactory;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class FinanceView extends VBox {
    FinanceController financeController= new FinanceController();



    public FinanceView(FinanceController controller){
        this.financeTools();
    }
    public  void financeTools(){
        TextField RevenueTf = PaneFactory.createTextField("Revenue");
        TextField CostTf = PaneFactory.createTextFieldUneditable("Cost");
        TextField profitTf = PaneFactory.createTextFieldUneditable("profit");
        Button calBtn = PaneFactory.createButton("Calculate");


        VBox financeBox = PaneFactory.createVBox(40,RevenueTf, CostTf, profitTf, calBtn);
        financeBox.setFillWidth(false);
        financeBox.setAlignment(Pos.CENTER);
        this.getChildren().add(financeBox);

        calBtn.setOnAction(event->{
            double revenu = Double.parseDouble(RevenueTf.getText());

            financeController.setProfit(revenu);
            double profit = financeController.getProfit();
            profitTf.setText(String.format("%.2f",profit));
            CostTf.setText(String.format("%2f",financeController.getCost()));



        });

    }





}
