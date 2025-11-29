package org.patterns.smartexpensetracker.apps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.MainMenuController;
import org.patterns.smartexpensetracker.views.MainMenuView;

public class MainMenuApplication extends Application {

    @Override
    public void start(Stage stage) {
        MainMenuView view = new MainMenuView();

        // ✅ attach controller so the buttons actually do something
        new MainMenuController(view, stage);

        Scene scene = new Scene(view, 1000, 650);
        stage.setTitle("Smart Expense Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}