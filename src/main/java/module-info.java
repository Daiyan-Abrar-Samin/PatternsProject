module org.patterns.smartexpensetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;
    requires java.desktop;

    // FXML controllers need to be accessible by the FXMLLoader
    opens org.patterns.smartexpensetracker.controllers to javafx.fxml;

    // TableView + PropertyValueFactory use reflection (javafx.base)
    opens org.patterns.smartexpensetracker.models to javafx.base;

    // Export the packages that should be visible to other modules (if any)
    exports org.patterns.smartexpensetracker.apps;
    exports org.patterns.smartexpensetracker.models;
    exports org.patterns.smartexpensetracker.views;
    exports org.patterns.smartexpensetracker.controllers;
}
