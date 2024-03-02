package org.example.courseprojgui.fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ErrorPopUp {

    public Button okButton;

    public static void showErrorPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(ErrorPopUp.class.getResource("/org/example/courseprojgui/error-popUp.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Error");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeErrorWindow() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }
}