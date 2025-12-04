package org.patterns.smartexpensetracker.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.AdminController;
import org.patterns.smartexpensetracker.controllers.MainMenuController;
import org.patterns.smartexpensetracker.controllers.UserController;
import org.patterns.smartexpensetracker.models.User;

public class UserView extends BorderPane {

    private final TableView<User> tableView = new TableView<>();
    private final UserController controller;

    public UserView(UserController controller) {
        this.controller = controller;

        setPadding(new Insets(10));
        setStyle("-fx-background-color: #f5fff5;");

        setTop(buildHeader());
        setCenter(buildTable());

        refreshTable();
    }

    private HBox buildHeader() {
        Button back = new Button("Go Back");
        back.setOnAction(e -> goBack());

        HBox box = new HBox(back);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(10));
        return box;
    }

    private TableView<User> buildTable() {
        tableView.setPlaceholder(new Label("No users found"));

        TableColumn<User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<User, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableView.getColumns().addAll(idCol, usernameCol, firstNameCol, lastNameCol, phoneCol, roleCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        return tableView;
    }

    private void goBack() {
        Stage stage = (Stage) getScene().getWindow();

        AdminView adminView = new AdminView(new UserController());
        new AdminController(adminView, new UserController());

        stage.setScene(new Scene(adminView, 1000, 650));
        stage.setTitle("Admin Panel");
    }

    public void refreshTable() {
        tableView.setItems(controller.getUsers());
    }
}