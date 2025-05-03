module com.test.recipesystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.test.bandViewer to javafx.fxml;
    exports com.test.bandViewer;
}