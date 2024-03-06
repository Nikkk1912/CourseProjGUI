package org.example.courseprojgui.fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.courseprojgui.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userCreationController implements Initializable {
    private UsersTabController usersTabController;
    public AnchorPane userCreationAncrPane;
    public Button userCreationAddButton;
    public Button userCreationClearButton;
    public Button userCreationBackButton;
    public TextField userCreationNameField;
    public TextField userCreationSurnameField;
    public TextField userCreationBillingField;
    public TextField userCreationShippingField;
    public TextField userCreationBirthField;
    public TextField userCreationCardField;
    public TextField userCreationLoginField;
    public TextField userCreationPasswordField;
    public Text userCreationText1;
    public Text userCreationText2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersTabController = UsersTabController.getInstance();

    }


    public void clearFields() {
          userCreationNameField.clear();
          userCreationSurnameField.clear();
          userCreationBillingField.clear();
          userCreationShippingField.clear();
          userCreationBirthField.clear();
          userCreationCardField.clear();
          userCreationLoginField.clear();
          userCreationPasswordField.clear();
    }

    public void closeWindow() {
        Stage stage = (Stage) userCreationBackButton.getScene().getWindow();
        stage.close();
    }

    public void saveUser() {
        String name = userCreationNameField.getText();
        String surname = userCreationSurnameField.getText();
        String ship = userCreationShippingField.getText();
        String bill = userCreationBillingField.getText();
        String card = userCreationCardField.getText();
        String birth = userCreationBirthField.getText();
        String login = userCreationLoginField.getText();
        String password = userCreationPasswordField.getText();

        if(login.isEmpty() && password.isEmpty()) {
            userCreationText1.setText("Login and password cant be empty");
            System.out.println("Empty login or password");
            return;
        }

        Customer customer = new Customer(name, surname, login, password, card, ship, bill, birth);

        usersTabController.addNewUserToList(customer);

        Stage stage = (Stage) userCreationAddButton.getScene().getWindow();
        stage.close();
    }
}
