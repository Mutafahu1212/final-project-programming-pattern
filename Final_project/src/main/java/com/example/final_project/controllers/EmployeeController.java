package com.example.final_project.controllers;

import com.example.final_project.models.Employee;
import javafx.collections.ObservableList;

public class EmployeeController {
    public EmployeeController() {
    }

    public ObservableList<Employee> getEmployees() {
        return Employee.getAllEmployees();
    }
}
