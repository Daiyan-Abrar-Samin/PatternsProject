package org.patterns.smartexpensetracker.factories;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.patterns.smartexpensetracker.controllers.UserController;
import org.patterns.smartexpensetracker.models.User;
import org.patterns.smartexpensetracker.views.UserView;

public class UserFactory {
    private static TextField userIdText;
    private static TextField usernameText;
    private static TextField passwordText;
    private static TextField firstNameText;
    private static TextField lastNameText;
    private static TextField phoneText;

    public static HBox createUserForm(UserController controller, UserView view) {
        HBox hBox = new HBox(60);
        hBox.setPadding(new Insets(20));
        hBox.setAlignment(Pos.TOP_LEFT);

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

        // This is used to resize text-fields( col2, col4) when the application is maximized to full size
/*
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHgrow(Priority.NEVER);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHgrow(Priority.ALWAYS);
        formGrid.getColumnConstraints().addAll(col1, col2, col3, col4);
 */

        // Buttons stacked vertically
        VBox buttonBox = new VBox(15);
        buttonBox.setAlignment(Pos.TOP_CENTER);

        Button createButton = new Button("Create");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        Button closeButton = new Button("Close");

        createButton.setPrefWidth(150);
        updateButton.setPrefWidth(150);
        deleteButton.setPrefWidth(150);
        clearButton.setPrefWidth(150);
        closeButton.setPrefWidth(150);

        buttonBox.getChildren().addAll(createButton, updateButton, deleteButton, clearButton, closeButton);

        createButton.setOnAction(e -> {
            controller.createUser(
                    usernameText.getText(),
                    passwordText.getText(),
                    firstNameText.getText(),
                    lastNameText.getText(),
                    phoneText.getText()
            );
            view.refreshTableData();
            clearForm();
        });

        updateButton.setOnAction(e -> {
            controller.updateUser(
                    Integer.parseInt(userIdText.getText()),
                    usernameText.getText(),
                    passwordText.getText(),
                    firstNameText.getText(),
                    lastNameText.getText(),
                    phoneText.getText()
            );
            view.refreshTableData();
            clearForm();
        });

        deleteButton.setOnAction(e -> {
            controller.deleteUser(Integer.parseInt(userIdText.getText()));
            view.refreshTableData();
            clearForm();
        });

        clearButton.setOnAction(e -> {
            clearForm();
        });

        closeButton.setOnAction(e -> {
            closeButton.getScene().getWindow().hide();
        });

        // Combine form + buttons
        hBox.getChildren().addAll(formGrid, buttonBox);
        HBox.setHgrow(formGrid, Priority.ALWAYS);

        return hBox;
    }

    public static void loadSelectedUserIntoForm(TableView<User> tableView) {

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                userIdText.setText(String.valueOf(newSelection.getUserId()));
                usernameText.setText(newSelection.getUsername());
                passwordText.setText(newSelection.getPassword());
                firstNameText.setText(newSelection.getFirstName());
                lastNameText.setText(newSelection.getLastName());
                phoneText.setText(newSelection.getPhoneNumber());
            }
        });
    }

    private static void clearForm() {
        userIdText.clear();
        usernameText.clear();
        passwordText.clear();
        firstNameText.clear();
        lastNameText.clear();
        phoneText.clear();
    }
}
