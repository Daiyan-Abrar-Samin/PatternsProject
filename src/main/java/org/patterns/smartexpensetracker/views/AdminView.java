package org.patterns.smartexpensetracker.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import org.patterns.smartexpensetracker.controllers.UserController;
import org.patterns.smartexpensetracker.models.User;

public class AdminView extends BorderPane {

    private final TableView<User> tableView = new TableView<>();
    private final TextField filterField = new TextField();
    private final Button deleteButton = new Button("Delete User");
    private final Button viewTransactionsButton = new Button("View Transactions");
    private final Button closeButton = new Button("Close");

    private final UserController userController;

    public AdminView(UserController userController) {
        this.userController = userController;

        setPadding(new Insets(15));
        setStyle("-fx-background-color: #f0f8ff;");

        setTop(buildTop());
        setCenter(buildTable());
        setRight(buildButtons());

        refreshTable();
    }

    private HBox buildTop() {
        HBox box = new HBox(10);
        Label label = new Label("Filter by Username:");
        filterField.setPromptText("Username...");
        filterField.textProperty().addListener((obs, ov, nv) -> filter(nv));

        box.getChildren().addAll(label, filterField);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(5, 0, 10, 0));
        return box;
    }

    private TableView<User> buildTable() {
        tableView.setPlaceholder(new Label("No users found."));

        TableColumn<User, Integer> idCol = new TableColumn<>("User ID");
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

    private VBox buildButtons() {
        VBox box = new VBox(10, deleteButton, viewTransactionsButton, closeButton);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(10));
        return box;
    }

    public TableView<User> getTableView() { return tableView; }
    public Button getDeleteButton() { return deleteButton; }
    public Button getViewTransactionsButton() { return viewTransactionsButton; }
    public Button getCloseButton() { return closeButton; }

    public void refreshTable() {
        tableView.setItems(userController.getUsers());
    }

    private void filter(String usernamePart) {
        String val = usernamePart.trim();
        if (val.isEmpty()) {
            refreshTable();
        } else {
            tableView.setItems(userController.filterUsers(val, "", ""));
        }
    }
}