package org.patterns.smartexpensetracker.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.view.MainMenuView;

import java.io.IOException;

public class MainMenuApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        MainMenuView menuView = new MainMenuView();

        Scene scene = new Scene(menuView, 400, 300);
        stage.setTitle("Smart Expense Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
