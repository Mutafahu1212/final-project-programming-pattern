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

    public int getId() {
        return id.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public double getSalary() {
        return salary.get();
    }

    public int getHours_worked() {
        return hours_worked.get();
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

    public static boolean addEmployee(Employee employee) throws SQLException {
        boolean result = false;
        String sql = "Insert into Employee (firstName, lastName, salary, hoursWorked) Values (?, ?, ?, ?)";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);) {
//            pstm.setInt(1, employee.getId());
            pstm.setString(1,employee.getFirstName());
            pstm.setString(2, employee.getLastName());
            pstm.setDouble(3,employee.getSalary());
            pstm.setInt(4, employee.getHours_worked());

            int rowInserted = pstm.executeUpdate();
            if(rowInserted > 0){
                result = true;
            }
        } catch (SQLException e) {
            result = false;
            throw new RuntimeException(e);
        }
        return result;
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

    public static boolean updateEmployee(Employee employee){
        boolean result = false;
        String sql = "Update Employee Set firstName=?, lastName=?, salary=?, hoursWorked=? Where id=?";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);) {
            pstm.setString(1,employee.getFirstName());
            pstm.setString(2, employee.getLastName());
            pstm.setDouble(3,employee.getSalary());
            pstm.setInt(4, employee.getHours_worked());
            pstm.setInt(5, employee.getId());


            int rowUpdated = pstm.executeUpdate();
            if(rowUpdated > 0){
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static boolean deleteEmployee(Employee employee){
        boolean result = false;
        String sql = "Delete from Employee Where id=?";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);) {
            pstm.setInt(1, employee.getId());

            int rowDeleted = pstm.executeUpdate();
            if(rowDeleted > 0){
                result = true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

//    public static boolean searchLastName(Employee employee){
//        boolean result = false;
//        String sql ="Select From Employee Where lName Like ?";
//
//        return result;
//    }
}
