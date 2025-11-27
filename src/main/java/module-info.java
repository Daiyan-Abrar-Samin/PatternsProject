module org.patterns.smartexpensetracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports org.patterns.smartexpensetracker.apps;
    opens org.patterns.smartexpensetracker.models to javafx.base;
    opens org.patterns.smartexpensetracker.views to javafx.fxml;
}