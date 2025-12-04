package org.patterns.smartexpensetracker.factories;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UserFactory {

    public static class UserFormControls {
        public final TextField username;
        public final PasswordField password;
        public final TextField firstName;
        public final TextField lastName;
        public final TextField phone;
        public final Button actionButton;
        public final Button cancelButton;

        public UserFormControls(TextField username,
                                PasswordField password,
                                TextField firstName,
                                TextField lastName,
                                TextField phone,
                                Button actionButton,
                                Button cancelButton) {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phone = phone;
            this.actionButton = actionButton;
            this.cancelButton = cancelButton;
        }
    }

    public static VBox buildForm(String submitLabel) {
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        TextField first = new TextField();
        TextField last = new TextField();
        TextField phone = new TextField();

        username.setPromptText("Username");
        password.setPromptText("Password");
        first.setPromptText("First Name");
        last.setPromptText("Last Name");
        phone.setPromptText("Phone Number");

        Button actionBtn = new Button(submitLabel);
        Button cancelBtn = new Button("Cancel");

        GridPane grid = new GridPane();
        grid.setHgap(8);
        grid.setVgap(10);
        grid.setPadding(new Insets(15));

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);

        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        grid.add(new Label("First Name:"), 0, 2);
        grid.add(first, 1, 2);

        grid.add(new Label("Last Name:"), 0, 3);
        grid.add(last, 1, 3);

        grid.add(new Label("Phone:"), 0, 4);
        grid.add(phone, 1, 4);

        VBox box = new VBox(10, grid, actionBtn, cancelBtn);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(10));

        return box;
    }
}