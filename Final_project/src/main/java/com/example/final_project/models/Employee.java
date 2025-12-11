package com.example.final_project.models;

import database.ConnectionManager;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Employee {
    private final IntegerProperty id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final DoubleProperty salary;
    private final IntegerProperty hoursWorked;
    private final IntegerProperty yearsWorked;
    private final StringProperty phoneNumber;
    private final StringProperty email;

    public Employee(int id, String firstName, String lastName, double salary, int hoursWorked, int yearsWorked, String phoneNumber, String email) {
        this.id = new SimpleIntegerProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.salary = new SimpleDoubleProperty(salary);
        this.hoursWorked = new SimpleIntegerProperty(hoursWorked);
        this.yearsWorked = new SimpleIntegerProperty(yearsWorked);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
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

    public int getHoursWorked() {
        return hoursWorked.get();
    }

    public int getYearsWorked() {
        return yearsWorked.get();
    }

    public IntegerProperty yearsWorkedProperty() {
        return yearsWorked;
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setSalary(double salary2){
        salary.set(salary2);
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
        return hoursWorked;
    }

    public static boolean addEmployee(Employee employee) throws SQLException {
        boolean result = false;
        String sql = "Insert into Employee (firstName, lastName, salary, hoursWorked, yearsWorked, phoneNumber, email) Values (?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);) {
            pstm.setString(1,employee.getFirstName());
            pstm.setString(2, employee.getLastName());
            pstm.setDouble(3,employee.getSalary());
            pstm.setInt(4, employee.getHoursWorked());
            pstm.setInt(5, employee.getYearsWorked());
            pstm.setString(6, employee.getPhoneNumber());
            pstm.setString(7, employee.getEmail());

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
                int yearsWorked = rs.getInt("yearsWorked");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");

                Employee employee = new Employee(eId, firstName, lastName, salary, hoursWorked, yearsWorked, phoneNumber, email);
                employeeData.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employeeData;
    }

    public static boolean updateEmployee(Employee employee){
        boolean result = false;
        String sql = "Update Employee Set firstName=?, lastName=?, salary=?, hoursWorked=?, yearsWorked=?, phoneNumber=?, email=? Where id=?";

        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);) {
            pstm.setString(1,employee.getFirstName());
            pstm.setString(2, employee.getLastName());
            pstm.setDouble(3,employee.getSalary());
            pstm.setInt(4, employee.getHoursWorked());
            pstm.setInt(5, employee.getYearsWorked());
            pstm.setString(6, employee.getPhoneNumber());
            pstm.setString(7, employee.getEmail());
            pstm.setInt(8, employee.getId());

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

    public static ObservableList<Employee> searchEmployee(Employee employee){
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        String sql = "Select * from Employee Where lastName Like ?";
        try(Connection conn = ConnectionManager.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);) {
            pstm.setString(1, "%" +employee.getLastName() +"%");

            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                int eId = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                double salary = rs.getDouble("salary");
                int hoursWorked = rs.getInt("hoursWorked");
                int yearsWorked = rs.getInt("yearsWorked");
                String phoneNumber = rs.getString("phoneNumber");
                String email = rs.getString("email");

                Employee employee2 = new Employee(eId, firstName, lastName, salary, hoursWorked, yearsWorked, phoneNumber, email);
                employees.add(employee2);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }

}
