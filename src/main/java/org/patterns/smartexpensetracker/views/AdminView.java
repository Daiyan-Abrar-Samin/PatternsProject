package org.patterns.smartexpensetracker.views;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.models.User;
import org.patterns.smartexpensetracker.controllers.AdminController;
import org.patterns.smartexpensetracker.controllers.UserController;

public class AdminView extends BorderPane {

    private final TableView<User> userTable = new TableView<>();
    private final Button btnDelete = new Button("Delete User");
    private final Button btnViewTransactions = new Button("View Transactions");
    private final Button btnClose = new Button("Close");
    private final UserController userController;

    public AdminView() {
        this.userController = new UserController();

        setPadding(new Insets(10));

        setTop(buildHeader());
        setCenter(buildTable());
        setBottom(buildButtons());

        refreshTable();

        // IMPORTANT: wire controller AFTER view is built
        new AdminController(this, userController);
    }

    private HBox buildHeader() {
        Label title = new Label("Admin Panel - Users");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        HBox box = new HBox(title);
        box.setPadding(new Insets(10));
        return box;
    }

    private TableView<User> buildTable() {
        userTable.setPlaceholder(new Label("No users found"));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> firstCol = new TableColumn<>("First Name");
        firstCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastCol = new TableColumn<>("Last Name");
        lastCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        userTable.getColumns().addAll(usernameCol, firstCol, lastCol, phoneCol);
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        return userTable;
    }

    private HBox buildButtons() {
        HBox box = new HBox(10);
        box.setPadding(new Insets(10));

        btnDelete.setPrefWidth(150);
        btnViewTransactions.setPrefWidth(150);
        btnClose.setPrefWidth(100);

        box.getChildren().addAll(btnDelete, btnViewTransactions, btnClose);
        return box;
    }

    // ================ REQUIRED METHODS FOR CONTROLLER ================

    public Button getDeleteButton() {
        return btnDelete;
    }

    public Button getViewTransactionsButton() {
        return btnViewTransactions;
    }

    public Button getCloseButton() {
        return btnClose;
    }

    public TableView<User> getTableView() {
        return userTable;
    }

    public void refreshTable() {
        userTable.setItems(userController.getUsers());
    }
}
