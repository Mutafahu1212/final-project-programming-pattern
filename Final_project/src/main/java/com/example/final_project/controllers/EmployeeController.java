package com.example.final_project.controllers;

import com.example.final_project.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class EmployeeController {

    public EmployeeController() {}

    public ObservableList<Employee> getEmployees() {
        ObservableList<Employee> list = Employee.getAllEmployees();
        employeeQueue.add((Employee) list);
        raiseForLongTermEmployees();
        return list;
    }

    ObservableList<Employee> employeesList = FXCollections.observableArrayList();

    Queue<Employee> employeeQueue = new LinkedList<>();

    public boolean addEmployee(Employee employee) throws SQLException {
        boolean result = false;
        if(Employee.addEmployee(employee)){
            employeesList.add(employee);
            employeeQueue.add(employee);
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

    public ObservableList<Employee> searchEmployee(Employee employee){
        return Employee.searchEmployee(employee);
    }

    public double totalSalary(){
        double result = 0;

        for (Employee e : Employee.getAllEmployees()) {
            result += e.getSalary();
            System.out.println(result);
        }

        return result;
    }

    public void raiseForLongTermEmployees(){
        for (Employee employee1 : employeeQueue){
            int year = employee1.getYearsWorked();
            if (year > 2){
                double salary = employee1.getSalary();
                employee1.setSalary(salary + 2);
                Employee.updateEmployee(employee1);
            }
        }
    }

}
