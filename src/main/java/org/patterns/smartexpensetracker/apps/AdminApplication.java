package org.patterns.smartexpensetracker.apps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.AdminController;
import org.patterns.smartexpensetracker.controllers.UserController;
import org.patterns.smartexpensetracker.views.AdminView;

public class AdminApplication extends Application {

    @Override
    public void start(Stage stage) {
        UserController userController = new UserController();
        AdminView view = new AdminView(userController);
        new AdminController(view, userController);

        Scene scene = new Scene(view, 1000, 650);
        stage.setTitle("Admin - User Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
