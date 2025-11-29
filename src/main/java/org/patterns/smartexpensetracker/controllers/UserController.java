package org.patterns.smartexpensetracker.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import org.patterns.smartexpensetracker.models.User;

public class UserController {

    @FXML private TableView<User> tableView;
    @FXML private TableColumn<User, Integer> colUserId;
    @FXML private TableColumn<User, String> colUsername;
    @FXML private TableColumn<User, String> colPassword;
    @FXML private TableColumn<User, String> colFirstName;
    @FXML private TableColumn<User, String> colLastName;
    @FXML private TableColumn<User, String> colPhone;

    @FXML private TextField filterTextField;
    @FXML private TextField usernameText;
    @FXML private TextField passwordText;
    @FXML private TextField firstNameText;
    @FXML private TextField lastNameText;
    @FXML private TextField phoneText;

    public UserController() {

    }

    // This method runs after FXML is loaded
    @FXML
    private void initialize() {
        // Make all table columns stretch to fill the table width
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Setup table columns
        colUserId.setCellValueFactory(cell -> cell.getValue().userIdProperty().asObject());
        colUsername.setCellValueFactory(cell -> cell.getValue().usernameProperty());
        colPassword.setCellValueFactory(cell -> cell.getValue().passwordProperty());
        colFirstName.setCellValueFactory(cell -> cell.getValue().firstNameProperty());
        colLastName.setCellValueFactory(cell -> cell.getValue().lastNameProperty());
        colPhone.setCellValueFactory(cell -> cell.getValue().phoneNumberProperty());

        // Load all users initially
        tableView.setItems(getUsers());

        // Filter listener
        filterTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            String text = newVal.trim().toLowerCase();
            if (!text.isEmpty()) {
                tableView.setItems(filterUsers(text, text, text));
            } else {
                tableView.setItems(getUsers());
            }
        });
    }

    public ObservableList<User> getUsers() {
        return User.getAllUsers();
    }

    public ObservableList<User> filterUsers(String userName, String firstName, String lastName) {
        return User.filterUsers(userName, firstName, lastName);
    }

    // Button event handlers
    @FXML
    private void onCreate() {
        try {
            User.create(new User(0,
                    usernameText.getText(),
                    passwordText.getText(),
                    firstNameText.getText(),
                    lastNameText.getText(),
                    phoneText.getText()));

            Alert success = new Alert(Alert.AlertType.CONFIRMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("The user account has been successfully added");
            success.show();

            tableView.setItems(getUsers());
            clearFields();

        } catch (Exception ex) {
            Alert fail = new Alert(Alert.AlertType.ERROR);
            fail.setTitle("Error");
            fail.setHeaderText(null);
            fail.setContentText("User Creation Failed:\n" + ex.getMessage());
            fail.show();
        }
    }

    @FXML
    private void onClear() {
        clearFields();
    }

    @FXML
    private void onClose() {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }

    private void clearFields() {
        usernameText.clear();
        passwordText.clear();
        firstNameText.clear();
        lastNameText.clear();
        phoneText.clear();
    }
}
