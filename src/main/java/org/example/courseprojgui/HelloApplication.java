package org.example.courseprojgui;

import atlantafx.base.theme.PrimerDark;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());

        Parent root = FXMLLoader.load(getClass().getResource("/org/example/courseprojgui/mainWindow.fxml"));

        stage.setTitle("Shop!");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 979.0,698.0));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}