package org.patterns.smartexpensetracker.views;

import javafx.collections.FXCollections;
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
import org.patterns.smartexpensetracker.session.Session;

public class UserSettingsView extends BorderPane {

    private final TableView<User> tableView = new TableView<>();
    private final TextField usernameField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final TextField firstNameField = new TextField();
    private final TextField lastNameField = new TextField();
    private final TextField phoneField = new TextField();
    private final Button saveButton = new Button("Save Changes");
    private final Button backButton = new Button("GO BACK");

    private final UserController userController;

    public UserSettingsView(UserController userController) {
        this.userController = userController;

        setPadding(new Insets(15));
        setStyle("-fx-background-color: #e9fff4;");

        setTop(buildTop());
        setCenter(buildTable());
        setBottom(buildForm());

        loadCurrentUser();
    }

    private HBox buildTop() {
        HBox top = new HBox(backButton);
        top.setAlignment(Pos.CENTER_RIGHT);
        top.setPadding(new Insets(5, 0, 10, 0));

        backButton.setOnAction(e -> goBack());

        return top;
    }

    private TableView<User> buildTable() {
        tableView.setPlaceholder(new Label("No user loaded."));

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<User, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<User, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        tableView.getColumns().addAll(usernameCol, firstNameCol, lastNameCol, phoneCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) {
                usernameField.setText(n.getUsername());
                passwordField.setText(n.getPassword());
                firstNameField.setText(n.getFirstName());
                lastNameField.setText(n.getLastName());
                phoneField.setText(n.getPhoneNumber());
            }
        });

        return tableView;
    }

    private VBox buildForm() {
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        firstNameField.setPromptText("First Name");
        lastNameField.setPromptText("Last Name");
        phoneField.setPromptText("Phone Number");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);

        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        grid.add(new Label("First Name:"), 0, 2);
        grid.add(firstNameField, 1, 2);

        grid.add(new Label("Last Name:"), 0, 3);
        grid.add(lastNameField, 1, 3);

        grid.add(new Label("Phone:"), 0, 4);
        grid.add(phoneField, 1, 4);

        saveButton.setOnAction(e -> saveChanges());

        VBox box = new VBox(10, grid, saveButton);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));
        return box;
    }

    private void loadCurrentUser() {
        User current = Session.getCurrentUser();
        if (current != null) {
            tableView.setItems(FXCollections.observableArrayList(current));
            tableView.getSelectionModel().selectFirst();
        }
    }

    private void saveChanges() {
        User current = Session.getCurrentUser();
        if (current == null) {
            showError("No logged-in user.");
            return;
        }

        userController.updateUser(
                current.getUserId(),
                usernameField.getText().trim(),
                passwordField.getText().trim(),
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                phoneField.getText().trim(),
                current.getRole()
        );

        Session.setCurrentUser(new User(
                current.getUserId(),
                usernameField.getText().trim(),
                passwordField.getText().trim(),
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                phoneField.getText().trim(),
                current.getRole()
        ));

        loadCurrentUser();
    }

    private void goBack() {
        Stage stage = (Stage) getScene().getWindow();
        MainMenuView menuView = new MainMenuView();
        new MainMenuController(menuView, stage);
        stage.setScene(new Scene(menuView, 1000, 650));
        stage.setTitle("Smart Expense Tracker");
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.show();
    }
}