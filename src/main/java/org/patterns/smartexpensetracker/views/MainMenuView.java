package org.patterns.smartexpensetracker.views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuView extends VBox {
    private final Button userButton;
    private final Button transactionButton;

    public MainMenuView() {
        this.userButton = new Button("User Information");
        this.transactionButton = new Button("Expenses");

        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
//        this.setStyle("-fx-padding: 50;");

        this.getChildren().addAll(userButton, transactionButton);

//      Uncomment this whole block of code to make the Expense button work.
        transactionButton.setOnAction(event -> {
            try {
                // Create view exactly as TransactionApplication does
                org.patterns.smartexpensetracker.controllers.TransactionController controller =
                        new org.patterns.smartexpensetracker.controllers.TransactionController();

                org.patterns.smartexpensetracker.views.TransactionView view =
                        new org.patterns.smartexpensetracker.views.TransactionView(controller);

                javafx.stage.Stage stage = (javafx.stage.Stage) this.getScene().getWindow();
                stage.setScene(new javafx.scene.Scene(view, 1000, 650));
                stage.setTitle("Expenses");

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Button getUserButton() {
        return userButton;
    }

    public Button getTransactionButton() {
        return transactionButton;
    }
}

