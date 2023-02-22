module com.example.demoo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.A7 to javafx.fxml;
    exports com.example.A7;
}