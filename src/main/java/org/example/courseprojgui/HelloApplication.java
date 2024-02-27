package org.example.courseprojgui;

import atlantafx.base.theme.Dracula;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-window.fxml"));
        Application.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());


        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Shop!");
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}