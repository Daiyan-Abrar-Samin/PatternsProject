package org.patterns.smartexpensetracker.controllers;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.apps.MainMenuApplication;
import org.patterns.smartexpensetracker.views.LoginView;

public class LoginController {

    private final LoginView view;

    // Temporary credentials — you can connect to DB later
    private final String validUser = "admin";
    private final String validPass = "1234";

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void login(Stage stage) {
        String user = view.getUsername();
        String pass = view.getPassword();

        if (user.equals(validUser) && pass.equals(validPass)) {
            try {
                stage.close();
                MainMenuApplication app = new MainMenuApplication();
                Stage menuStage = new Stage();
                app.start(menuStage);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.show();
        }
    }
}
