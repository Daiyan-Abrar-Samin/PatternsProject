package org.patterns.smartexpensetracker.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.MainMenuController;
import org.patterns.smartexpensetracker.controllers.UserController;
import org.patterns.smartexpensetracker.factories.UserFactory;
import org.patterns.smartexpensetracker.models.User;

public class UserView extends BorderPane {
    TextField filterTextField;
    private final TableView<User> tableView = new TableView<>();
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;

        this.setStyle("-fx-background-color: #e9fff4;");
        setPadding(new Insets(15));

        // Filtering and Go Back button
        this.setTop(buildTopSection());

        // TableView
        this.setCenter(createTable());

        // User form and CRUD buttons from UserFactory class
        this.setBottom(UserFactory.createUserForm(userController, this));

        // Initial load of table data
        refreshTableData();
        UserFactory.loadSelectedUserIntoForm(tableView);
    }

    private HBox buildTopSection() {
        HBox topHBox = new HBox(5);

        Label filterLabel = new Label("Data Filtering:");
        filterTextField = new TextField();
        filterTextField.setPrefWidth(245);
        filterTextField.setPromptText("Search Username / First Name / Last Name");

        filterTextField.textProperty().addListener((obs, oldVal, newVal) -> filter(newVal));

        Button back = new Button("GO BACK");
        back.setOnAction(e -> goBack());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topHBox.getChildren().addAll(filterLabel, filterTextField, spacer, back);
        topHBox.setPadding(new Insets(5, 0, 10, 0));
        topHBox.setAlignment(Pos.CENTER_LEFT);

        return topHBox;
    }

    private TableView<User> createTable() {
        tableView.setPlaceholder(new Label("No content in table"));

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

        return tableView;
    }

    // Refresh table data to the most updated
    public void refreshTableData() {
        tableView.setItems(userController.getUsers());
    }

    // Data filtering through Username, First Name and Last Name
    private void filter(String val) {
        String textValue = val.trim().toLowerCase();

        if (!textValue.isEmpty()) {
            tableView.setItems(userController.filterUsers(textValue, textValue, textValue));
        } else {
            tableView.setItems(userController.getUsers());
        }
    }

    // It takes back to MainMenuApplication
    private void goBack() {
        Stage stage = (Stage) getScene().getWindow();

        MainMenuView menuView = new MainMenuView();
        new MainMenuController(menuView, stage);

        stage.setScene(new Scene(menuView, 1000, 650));
        stage.setTitle("Smart Expense Tracker");
    }
}
