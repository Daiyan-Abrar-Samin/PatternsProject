package org.patterns.smartexpensetracker.apps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.CreateAccountController;
import org.patterns.smartexpensetracker.views.CreateAccountView;

public class CreateAccountApplication extends Application {

    @Override
    public void start(Stage stage) {
        CreateAccountView view = new CreateAccountView();
        new CreateAccountController(view, stage);

        Scene scene = new Scene(view, 500, 400);
        stage.setTitle("Create Account");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
