package org.patterns.smartexpensetracker.controllers;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.patterns.smartexpensetracker.apps.TransactionApplication;
import org.patterns.smartexpensetracker.apps.UserApplication;
import org.patterns.smartexpensetracker.views.MainMenuView;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainMenuController {
    private final MainMenuView mmv;
    private final Stage mainStage;

    public MainMenuController(MainMenuView mmv, Stage mainStage) {
        this.mmv = mmv;
        this.mainStage = mainStage;
        openUserApplication();
        openTransactionApplication();
    }

    private void openUserApplication() {
        mmv.getUserButton().setOnAction(event -> {
            try {
                UserApplication userApplication = new UserApplication();
                Stage userStage = new Stage();
                userApplication.start(userStage);

                mainStage.close();
            } catch (Exception ex) {
//                System.out.println(ex.getMessage());
//                Logger.getLogger(MainMenuController.class.getName()).log(Level.SEVERE, null, ex);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Unable to open User Application:\n" + ex.getMessage());
                alert.show();
            }
        });
    }

    private void openTransactionApplication() {
        mmv.getTransactionButton().setOnAction(event -> {
            try {
                TransactionApplication transactionApplication = new TransactionApplication();
                Stage userStage = new Stage();
                transactionApplication.start(userStage);

                mainStage.close();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Unable to open Transaction Application:\n" + ex.getMessage());
                alert.show();
            }
        });
    }
}
