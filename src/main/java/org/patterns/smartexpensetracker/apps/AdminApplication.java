package org.patterns.smartexpensetracker.apps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.views.AdminView;

import static javafx.application.Application.launch;

public class AdminApplication extends Application {

    @Override
    public void start(Stage stage) {
        AdminView view = new AdminView();
        Scene scene = new Scene(view, 1000, 650);
        stage.setScene(scene);
        stage.setTitle("Admin Panel");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}