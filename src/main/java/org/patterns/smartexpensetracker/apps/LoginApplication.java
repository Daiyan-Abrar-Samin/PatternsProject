package org.patterns.smartexpensetracker.apps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.views.LoginView;

public class LoginApplication extends Application {

    @Override
    public void start(Stage stage) {
        LoginView view = new LoginView(stage);
        Scene scene = new Scene(view, 1000, 650);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}