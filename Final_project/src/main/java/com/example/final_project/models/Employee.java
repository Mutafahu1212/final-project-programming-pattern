package com.example.final_project.models;

import database.ConnectionManager;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final DoubleProperty salary;
    private final IntegerProperty hours_worked;


    public Employee(int id, String firstName, String lastName, double salary, int hours_worked) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.salary = new SimpleDoubleProperty(salary);
        this.hours_worked = new SimpleIntegerProperty(hours_worked);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public DoubleProperty salaryProperty() {
        return salary;
    }

    public IntegerProperty hours_workedProperty() {
        return hours_worked;
    }

    public static ObservableList<Employee> getAllEmployees(){
        ObservableList<Employee> employeeData = FXCollections.observableArrayList();
        String spl = "SELECT * FROM employee";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(spl);) {
            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                int eId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                double salary = rs.getDouble("salary");
                int hoursWorked = rs.getInt("hoursWorked");
                Employee employee = new Employee(eId, firstName, lastName, salary, hoursWorked);
                employeeData.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeData;
    }
}

