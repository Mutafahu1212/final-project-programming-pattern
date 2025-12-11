package com.example.final_project.view;

import com.example.final_project.controllers.EmployeeController;
import com.example.final_project.factory.PaneFactory;
import com.example.final_project.models.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class EmployeeView extends VBox {
//    Logger logger = Logger.getLogger(EmployeeView.class.getName());
//    FileHandler fileHandler;// False to re-write file
//
//    {
//        try {
//            fileHandler = new FileHandler("src/logfile.log", true);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    fileHandler.setFormatter(new SimpleFormatter());
//
//    logger.addHandler(fileHandler);

    private final TableView<Employee> tableView;
    private final EmployeeController employeeController;

    TextField idTextField;
    TextField fNameTextField;
    TextField lNameTextField;
    TextField salaryTextField;
    TextField hoursWorkedTextField;
    TextField yearsWorkedTextField;
    TextField phoneNumberTextField;
    TextField emailTextField;

    public EmployeeView(EmployeeController employeeController) {
        this.employeeController = employeeController;
        this.tableView = new TableView<>();
        this.back();
        this.searchButton();
        this.createTable();
        this.getChildren().add(tableView);
        this.bindTableData();
        this.textFields();
        this.buttons();

    }

    public void buttons(){
        Button add = addEmployee();
        Button delete = deleteEmployee();
        Button update = updateEmployee();
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(add, delete, update);
        this.getChildren().add(hBox);


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
        yearsWorkedTextField = new TextField();
        yearsWorkedTextField.setPromptText("Years Worked");
        phoneNumberTextField = new TextField();
        phoneNumberTextField.setPromptText("Phone");
        emailTextField = new TextField();
        emailTextField.setPromptText("Email");


        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(idTextField, fNameTextField, lNameTextField, salaryTextField, hoursWorkedTextField, yearsWorkedTextField, phoneNumberTextField, emailTextField);
        this.getChildren().add(hBox);

    }


    private void searchButton(){
        TextField searchTextField = new TextField();
        searchTextField.setPromptText("Last Name");
        Button searchButton = new Button("Search");
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(searchButton, searchTextField);
        this.getChildren().add(hBox);

        searchButton.setOnAction(actionEvent -> search(searchTextField.getText()));
        searchTextField.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                search(searchTextField.getText());
            }
        });
    }

    private void search(String lastName){
        Employee employee = new Employee(0, "",lastName,0,0, 0, "", "");
        tableView.setItems(employeeController.searchEmployee(employee));
    }

    private void back(){
        Button backButton = PaneFactory.backButton();
        HBox hbox = new HBox(10);
        hbox.getChildren().add(backButton);
        this.getChildren().add(hbox);
    }

    private Button addEmployee(){//add
        Button addButton = PaneFactory.createButton("Add Employee");
        HBox hbox = new HBox(10);
        hbox.getChildren().add(addButton);
        this.getChildren().add(hbox);

        addButton.setOnAction( actionEvent -> {
            if(fNameTextField.getText().isEmpty() || lNameTextField.getText().isEmpty() ||
                    salaryTextField.getText().isEmpty() || hoursWorkedTextField.getText().isEmpty() ||
                    yearsWorkedTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty() ||
                    emailTextField.getText().isEmpty())
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Enter all information");
                errorAlert.show();

            } else {

                String fName = fNameTextField.getText();
                String lName = lNameTextField.getText();
                double salary = Double.parseDouble(salaryTextField.getText());
                int hoursWorked = Integer.parseInt(hoursWorkedTextField.getText());
                int yearsWorked = Integer.parseInt(yearsWorkedTextField.getText());
                String phoneNumber = phoneNumberTextField.getText();
                String email = emailTextField.getText();

                Employee employee = new Employee(0, fName, lName, salary, hoursWorked, yearsWorked, phoneNumber, email);
                try {
                    employeeController.addEmployee(employee);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                tableView.setItems(employeeController.getEmployees());
            }
        });

        return addButton;
    }

    private Button deleteEmployee(){
        Button deleteButton = PaneFactory.createButton("Delete");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(deleteButton);
        this.getChildren().add(hBox);

        deleteButton.setOnAction(actionEvent -> {
            int id = Integer.parseInt(idTextField.getText());

            Employee employee = new Employee(id, "", "", 0, 0, 0, "", "");

            employeeController.removeEmployee(employee);
            tableView.setItems(employeeController.getEmployees());
        });

        return deleteButton;
    }

    public Button updateEmployee(){
        Button updateButton = PaneFactory.createButton("Update");

        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(updateButton);
        this.getChildren().add(hBox);

        updateButton.setOnAction(actionEvent -> {
            if(fNameTextField.getText().isEmpty() || lNameTextField.getText().isEmpty() ||
                    salaryTextField.getText().isEmpty() || hoursWorkedTextField.getText().isEmpty() ||
                    yearsWorkedTextField.getText().isEmpty() || phoneNumberTextField.getText().isEmpty() ||
                    emailTextField.getText().isEmpty())
            {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("Enter all information");
                errorAlert.show();

            } else {
                int id = Integer.parseInt(idTextField.getText());
                String fName = fNameTextField.getText();
                String lName = lNameTextField.getText();
                double salary = Double.parseDouble(salaryTextField.getText());
                int hoursWorked = Integer.parseInt(hoursWorkedTextField.getText());
                int yearsWorked = Integer.parseInt(yearsWorkedTextField.getText());
                String phoneNumber = phoneNumberTextField.getText();
                String email = emailTextField.getText();

                Employee employee = new Employee(id, fName, lName, salary, hoursWorked, yearsWorked, phoneNumber, email);

                employeeController.updateEmployee(employee);
                tableView.setItems(employeeController.getEmployees());
            }
        });

        return updateButton;
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

        TableColumn<Employee, Integer> yearsCol = new TableColumn<>("Years Worked");
        yearsCol.setCellValueFactory(new PropertyValueFactory<>("yearsWorked"));

        TableColumn<Employee, String> phoneNumberCol = new TableColumn<>("Phone Number");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.getColumns().addAll(idCol, firstNameCol, lastNameCol, salaryCol, hoursCol, yearsCol, phoneNumberCol, emailCol);
    }

    private void bindTableData() {
        tableView.setItems(employeeController.getEmployees());
    }

}
