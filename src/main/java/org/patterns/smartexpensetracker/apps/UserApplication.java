package org.patterns.smartexpensetracker.apps;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.UserController;
import org.patterns.smartexpensetracker.views.UserView;

import java.io.IOException;

public class UserApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        UserController controller = new UserController();
//        UserView view = new UserView(controller);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/org/patterns/smartexpensetracker/UserView.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("User Information");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
