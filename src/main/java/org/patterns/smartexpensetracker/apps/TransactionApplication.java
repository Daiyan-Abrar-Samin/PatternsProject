package org.patterns.smartexpensetracker.apps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.controllers.TransactionController;
import org.patterns.smartexpensetracker.views.TransactionView;

import java.io.IOException;

public class TransactionApplication extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        TransactionController controller = new TransactionController();
        TransactionView view = new TransactionView(controller);

        Scene scene = new Scene(view, 1000, 650);
        stage.setTitle("Expenses");
        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
