package com.example.final_project.controllers;

import com.example.final_project.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class EmployeeController {

    public EmployeeController() {}

    public ObservableList<Employee> getEmployees() {
        return Employee.getAllEmployees();
    }

    ObservableList<Employee> employeesList = FXCollections.observableArrayList();

    public boolean addEmployee(Employee employee) throws SQLException {
        boolean result = false;
        if(Employee.addEmployee(employee)){
            employeesList.add(employee);
            result = true;
        }
        return result;
    }

    public boolean removeEmployee(Employee employee) {
        boolean result = false;
        if(Employee.deleteEmployee(employee)){
            employeesList.remove(employee);
            result = true;
        }
        return result;
    }

    public boolean updateEmployee(Employee employee){
        return Employee.updateEmployee(employee);
    }

    public ObservableList<Employee>  searchEmployee(Employee employee){
        return Employee.searchEmployee(employee);
    }

    public double totalSalary(){
        double result = 0;

        for(int i = 0; i < employeesList.size(); i++){
            result += employeesList.get(i).getSalary();
        }
        return result;
    }
}
