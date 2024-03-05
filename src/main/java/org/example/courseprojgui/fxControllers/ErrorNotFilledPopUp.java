package org.example.courseprojgui.fxControllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ErrorNotFilledPopUp implements Initializable {
    public Button okButton;

    @Override public void initialize(URL location, ResourceBundle resources) {

    }

    public static void showErrorPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(ErrorNotFilledPopUp.class.getResource("/org/example/courseprojgui/errorNotFilledPopUp.fxml"));
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