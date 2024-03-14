module org.example.courseprojgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires atlantafx.base;
    requires mysql.connector.j;
    requires java.sql;
    requires org.kordamp.bootstrapfx.core;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    opens org.example.courseprojgui to javafx.fxml;
    exports org.example.courseprojgui;
    opens org.example.courseprojgui.fxControllers to javafx.fxml;
    exports org.example.courseprojgui.fxControllers;
    exports org.example.courseprojgui.model;
    exports org.example.courseprojgui.enums;

}