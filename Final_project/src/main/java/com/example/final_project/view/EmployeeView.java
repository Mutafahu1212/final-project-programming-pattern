package com.example.final_project.view;

import com.example.final_project.controllers.EmployeeController;
import com.example.final_project.factory.PaneFactory;
import com.example.final_project.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class EmployeeView extends VBox {
    private final TableView<Employee> tableView;
    private final EmployeeController employeeController;

    TextField idTextField;
    TextField fNameTextField;
    TextField lNameTextField;
    TextField salaryTextField;
    TextField hoursWorkedTextField;

    public EmployeeView(EmployeeController employeeController) {
        this.employeeController = employeeController;
        this.tableView = new TableView<>();
        this.createSearchBar();
        this.back();
        this.createTable();
        this.getChildren().add(tableView);
        this.bindTableData();
        this.textFields();
        this.addEmployee();
        this.deleteEmployee();
        this.updateEmployee();
    }

    public void textFields(){
        idTextField = new TextField();
        idTextField.setPromptText("Id");
        fNameTextField = new TextField();
        fNameTextField.setPromptText("First Name");
        lNameTextField = new TextField();
        lNameTextField.setPromptText("Last Name");
        salaryTextField = new TextField();
        salaryTextField.setPromptText("Salary");
        hoursWorkedTextField = new TextField();
        hoursWorkedTextField.setPromptText("Work hours");
        HBox hBox = new HBox();
        hBox.getChildren().addAll(idTextField, fNameTextField, lNameTextField, salaryTextField, hoursWorkedTextField);
        this.getChildren().add(hBox);

    }

    private void createSearchBar(){
        Label searchlabel = new Label("First Name");
        this.getChildren().add(searchlabel);

        TextField searchTextField = new TextField();
        this.getChildren().add(searchTextField);

        Button searchBtn = new Button("Search");
        HBox searchbox = new HBox(10);
        searchbox.getChildren().addAll(searchlabel,searchTextField, searchBtn);
        this.getChildren().add(searchbox);

        searchBtn.setOnAction(event ->{
            String firstName = searchTextField.getText();
            if (firstName == null) firstName = "";

            ObservableList<Employee> searchFirstName = FXCollections.observableArrayList();

            for (Employee emp : employeeController.getEmployees()) {
                String empName = String.valueOf(emp.firstNameProperty().get());
                if (empName.equalsIgnoreCase(firstName)) {
                    searchFirstName.add(emp);
                    tableView.setItems(searchFirstName);
                    break;
                }
                tableView.setItems(employeeController.getEmployees());
            }
        });
    }

    private void back(){
        Button backButton = PaneFactory.backButton();
        HBox hbox = new HBox(10);
        hbox.getChildren().add(backButton);
        this.getChildren().add(hbox);
    }

    private void addEmployee(){//add
        Button addButton = PaneFactory.createButton("Add Employee");
        HBox hbox = new HBox(10);
        hbox.getChildren().add(addButton);
        this.getChildren().add(hbox);

        addButton.setOnAction( actionEvent -> {
            String fName = fNameTextField.getText();
            String lName = lNameTextField.getText();
            double salary = Double.parseDouble(salaryTextField.getText());
            int hoursWorked = Integer.parseInt(hoursWorkedTextField.getText());

            Employee employee = new Employee(0, fName, lName, salary, hoursWorked);
            try {
                employeeController.addEmployee(employee);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            tableView.setItems(employeeController.getEmployees());
//                    tableView.refresh();
        });
    }

    private void deleteEmployee(){
        Button deleteButton = PaneFactory.createButton("Delete");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(deleteButton);
        this.getChildren().add(hBox);

        deleteButton.setOnAction(actionEvent -> {
            int id = Integer.parseInt(idTextField.getText());

            Employee employee = new Employee(id, "", "", 0, 0);

            employeeController.removeEmployee(employee);
            tableView.setItems(employeeController.getEmployees());
        });
    }

    public void updateEmployee(){
        Button updateButton = PaneFactory.createButton("Update");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(updateButton);
        this.getChildren().add(hBox);

        updateButton.setOnAction(actionEvent -> {
            int id = Integer.parseInt(idTextField.getText());
            String fName = fNameTextField.getText();
            String lName = lNameTextField.getText();
            double salary = Double.parseDouble(salaryTextField.getText());
            int hoursWorked = Integer.parseInt(hoursWorkedTextField.getText());

            Employee employee = new Employee(id, fName, lName, salary, hoursWorked);

            employeeController.updateEmployee(employee);
            tableView.setItems(employeeController.getEmployees());
        });
    }

    private void createTable() {
        TableColumn<Employee, Integer> idCol = new TableColumn<>("Id");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Employee, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));

        TableColumn<Employee, Integer> hoursCol = new TableColumn<>("Hours Worked");
        hoursCol.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));

        tableView.getColumns().addAll(idCol, firstNameCol, lastNameCol, salaryCol, hoursCol);
    }

    private void bindTableData() {
        tableView.setItems(employeeController.getEmployees());
    }

}