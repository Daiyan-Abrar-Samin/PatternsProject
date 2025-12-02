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
import org.patterns.smartexpensetracker.models.User;

public class UserView extends VBox {
    TextField filterTextField;
    private final TableView<User> tableView;
    private final UserController userController;

    private TextField userIdText;
    private TextField usernameText;
    private TextField passwordText;
    private TextField firstNameText;
    private TextField lastNameText;
    private TextField phoneText;

    public UserView(UserController userController) {
        this.userController = userController;
        tableView = new TableView<>();

        this.setStyle("-fx-background-color: #e9fff4;");
        setPadding(new Insets(15));

        buildTopSection();
        createTable();
        refreshTableData();
        buildBottomSection();
        bindTableSelection();

        this.getChildren().addAll(buildTopSection(), tableView, buildBottomSection());
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

    private void refreshTableData() {
        tableView.setItems(userController.getUsers());
    }

    private void filter(String val) {
        String textValue = val.trim().toLowerCase();

        if (!textValue.isEmpty()) {
            tableView.setItems(userController.filterUsers(textValue, textValue, textValue));
        } else {
            tableView.setItems(userController.getUsers());
        }
    }

    private HBox buildBottomSection() {
        HBox bottomSection = new HBox(60);
        bottomSection.setPadding(new Insets(20));
        bottomSection.setAlignment(Pos.TOP_LEFT);

        // Grid pane for labels and text-fields
        GridPane formGrid = new GridPane();
        formGrid.setHgap(15);
        formGrid.setVgap(30);

        userIdText = new TextField();
        usernameText = new TextField();
        passwordText = new TextField();
        firstNameText = new TextField();
        lastNameText = new TextField();
        phoneText = new TextField();

        formGrid.add(new Label("User ID:"), 0, 0);
        formGrid.add(userIdText, 1, 0);

        formGrid.add(new Label("Username:"), 0, 1);
        formGrid.add(usernameText, 1, 1);

        formGrid.add(new Label("Password:"), 0, 2);
        formGrid.add(passwordText, 1, 2);

        formGrid.add(new Label("First Name:"), 2, 0);
        formGrid.add(firstNameText, 3, 0);

        formGrid.add(new Label("Last Name:"), 2, 1);
        formGrid.add(lastNameText, 3, 1);

        formGrid.add(new Label("Phone:"), 2, 2);
        formGrid.add(phoneText, 3, 2);

        int fieldWidth = 275; // consistent width for all fields
        userIdText.setPrefWidth(fieldWidth);
        usernameText.setPrefWidth(fieldWidth);
        passwordText.setPrefWidth(fieldWidth);
        firstNameText.setPrefWidth(fieldWidth);
        lastNameText.setPrefWidth(fieldWidth);
        phoneText.setPrefWidth(fieldWidth);

/*
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.NEVER);
        formGrid.getColumnConstraints().addAll(col1, col2, col3, col4);
 */

        // Buttons stacked vertically
        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        Button createButton = create();
        createButton.setPrefWidth(150);

        Button updateButton = update();
        updateButton.setPrefWidth(150);

        Button deleteButton = delete();
        deleteButton.setPrefWidth(150);

        Button clearButton = clear();
        clearButton.setPrefWidth(150);

        Button closeButton = close();
        closeButton.setPrefWidth(150);

        buttonBox.getChildren().addAll(createButton, updateButton, deleteButton, clearButton, closeButton);

        // Combine form + buttons
        bottomSection.getChildren().addAll(formGrid, buttonBox);
        HBox.setHgrow(formGrid, Priority.ALWAYS);

        return bottomSection;
    }

    private void bindTableSelection() {

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Populate text fields with selected user
                userIdText.setText(String.valueOf(newSelection.getUserId()));
                usernameText.setText(newSelection.getUsername());
                passwordText.setText(newSelection.getPassword());
                firstNameText.setText(newSelection.getFirstName());
                lastNameText.setText(newSelection.getLastName());
                phoneText.setText(newSelection.getPhoneNumber());
            }
        });
    }

    private Button create() {
        Button create = new Button("Create");

        create.setOnAction(e -> {
            userController.createUser(
                    usernameText.getText(),
                    passwordText.getText(),
                    firstNameText.getText(),
                    lastNameText.getText(),
                    phoneText.getText()
            );
            refreshTableData();
            clearForm();
        });

        return create;
    }

    private Button update() {
        Button update = new Button("Update");

        update.setOnAction(e -> {
            userController.updateUser(
                    Integer.parseInt(userIdText.getText()),
                    usernameText.getText(),
                    passwordText.getText(),
                    firstNameText.getText(),
                    lastNameText.getText(),
                    phoneText.getText()
            );
            refreshTableData();
            clearForm();
        });

        return update;
    }

    private Button delete() {
        Button delete = new Button("Delete");

        delete.setOnAction(e -> {
            userController.deleteUser(Integer.parseInt(userIdText.getText()));
            refreshTableData();
            clearForm();
        });

        return delete;
    }

    private void clearForm() {
        userIdText.clear();
        usernameText.clear();
        passwordText.clear();
        firstNameText.clear();
        lastNameText.clear();
        phoneText.clear();
    }

    private Button clear() {
        Button clear = new Button("Clear");

        clear.setOnAction(event -> {
            userIdText.clear();
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

    private void goBack() {
        Stage stage = (Stage) getScene().getWindow();

        MainMenuView menuView = new MainMenuView();
        new MainMenuController(menuView, stage);

        stage.setScene(new Scene(menuView, 1000, 650));
        stage.setTitle("Smart Expense Tracker");
    }
}
