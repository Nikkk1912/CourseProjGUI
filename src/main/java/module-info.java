module org.example.courseprojgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires atlantafx.base;

    requires org.kordamp.bootstrapfx.core;

    opens org.example.courseprojgui to javafx.fxml;
    exports org.example.courseprojgui;
    exports org.example.courseprojgui.fxControllers;
    opens org.example.courseprojgui.fxControllers to javafx.fxml;
}