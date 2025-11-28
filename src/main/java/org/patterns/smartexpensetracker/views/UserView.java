package org.patterns.smartexpensetracker.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.UserController;
import org.patterns.smartexpensetracker.models.User;

public class UserView extends VBox {
    TextField filterTextField;
    private final TableView<User> tableView;
    private final UserController userController;

    private TextField usernameText;
    private TextField passwordText;
    private TextField firstNameText;
    private TextField lastNameText;
    private TextField phoneText;

    public UserView(UserController userController) {
        this.userController = userController;
        tableView = new TableView<>();
        filterInterface();
        createTable();
        bindTableData();
        bindTextFields();
        crudButtons();
        this.getChildren().addAll(filterInterface(), tableView, bindTextFields(), crudButtons());
    }

    private HBox filterInterface() {
        HBox hBox = new HBox(5);
        Label filterLabel = new Label("Data Filtering:");
        filterTextField = new TextField();
//        Button searchButton = new Button("Search");

//        searchButton.setOnAction(event -> {
//            String username = filterTextField.getText().trim().toLowerCase();
//            String firstName = filterTextField.getText().trim().toLowerCase();
//            String lastName = filterTextField.getText().trim().toLowerCase();
//            tableView.setItems(userController.filterUsers(username, firstName, lastName));
//        });

        filterTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            String textValue = newValue.trim().toLowerCase();
            if (!textValue.isEmpty()) {
                tableView.setItems(userController.filterUsers(textValue, textValue, textValue));
            } else {
                tableView.setItems(userController.getUsers());
            }
        });

        hBox.getChildren().addAll(filterLabel, filterTextField); // searchButton in case button event handler
        hBox.setPadding(new Insets(5, 0, 10, 0));
        hBox.setAlignment(Pos.CENTER_LEFT);

        return hBox;
    }

    private void createTable() {
        TableColumn<User, Integer> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        tableView.getColumns().addAll(userIdCol, usernameCol, passwordCol, firstNameCol, lastNameCol, phoneCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    }

    private void bindTableData() {
        tableView.setItems(userController.getUsers());
    }

    private HBox bindTextFields() {
        HBox hBox = new HBox(5);

        Label usernameLabel = new Label("Username: ");
        usernameText = new TextField("");
        Label passwordLabel = new Label("Password: ");
        passwordText = new TextField("");
        Label firstNameLabel = new Label("First Name: ");
        firstNameText = new TextField("");
        Label lastNameLabel = new Label("Last Name: ");
        lastNameText = new TextField("");
        Label phoneLabel = new Label("Phone: ");
        phoneText = new TextField("");

        hBox.getChildren().addAll(usernameLabel, usernameText,
                passwordLabel, passwordText,
                firstNameLabel, firstNameText,
                lastNameLabel, lastNameText,
                phoneLabel, phoneText);
        hBox.setPadding(new Insets(10, 0, 10, 0));
        hBox.setAlignment(Pos.CENTER_LEFT);

        return hBox;
    }

    private HBox crudButtons() {
        HBox hBox = new HBox(10);
        Button createButton = new Button("Create");

//        createButton.setOnAction(event -> {
//            userController.createUser(
//                    usernameText.getText(),
//                    passwordText.getText(),
//                    firstNameText.getText(),
//                    lastNameText.getText(),
//                    phoneText.getText()
//            );
//            bindTableData();
//            clearForm();
//        });

        hBox.getChildren().addAll(createButton, clear(), close());
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private void clearForm() {
        usernameText.clear();
        passwordText.clear();
        firstNameText.clear();
        lastNameText.clear();
        phoneText.clear();
    }

    private Button clear() {
        Button clear = new Button("Clear");

        clear.setOnAction(event -> {
            usernameText.clear();
            passwordText.clear();
            firstNameText.clear();
            lastNameText.clear();
            phoneText.clear();
        });

        return clear;
    }

    private Button close() {
        Button close = new Button("Close");

        close.setOnAction(event -> {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        });

        return close;
    }
}
