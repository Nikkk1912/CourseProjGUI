package org.example.courseprojgui.fxControllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class DeleteConfirm {

    public AnchorPane errorBody;
    public Button confirmButton;
    public Button rejectButton;
    private boolean confirmationResult;

    public void deleteConfirmation(Consumer<Boolean> callback) {
        try {
            FXMLLoader loader = new FXMLLoader(ErrorNotFilledPopUp.class.getResource("/org/example/courseprojgui/deleteConfirm.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Are you sure?");
            stage.setScene(scene);
            stage.show();
            callback.accept(confirmationResult);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean deleteAccept() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        if(confirmButton.isPressed()) {
            return true;
        } else {
            stage.close();
            return false;
        }

    }

    public boolean closeDeleteWindow() {
        Stage stage = (Stage) rejectButton.getScene().getWindow();
        stage.close();
        return true;
    }
}
