package org.example.courseprojgui.fxControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.hibernate.HibernateShop;
import org.example.courseprojgui.model.Customer;
import org.example.courseprojgui.model.Manager;

import java.net.URL;
import java.util.ResourceBundle;

public class UserCreationController implements Initializable {
    public RadioButton userCreationIsAdmin;
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
    private GenericHibernate genericHibernate;
    private HibernateShop hibernateShop;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usersTabController = UsersTabController.getInstance();
        genericHibernate = new GenericHibernate(usersTabController.getMainController().getEntityManagerFactory());
        hibernateShop = new HibernateShop(usersTabController.getMainController().getEntityManagerFactory());
    }


    @FXML private void clearFields() {
          userCreationNameField.clear();
          userCreationSurnameField.clear();
          userCreationBillingField.clear();
          userCreationShippingField.clear();
          userCreationBirthField.clear();
          userCreationCardField.clear();
          userCreationLoginField.clear();
          userCreationPasswordField.clear();
          userCreationIsAdmin.setSelected(false);
    }

    @FXML private void closeWindow() {
        Stage stage = (Stage) userCreationBackButton.getScene().getWindow();
        stage.close();
    }

    @FXML private void saveUser() {
        String name = userCreationNameField.getText();
        String surname = userCreationSurnameField.getText();
        String login = userCreationLoginField.getText();
        String password = userCreationPasswordField.getText();
        if(hibernateShop.getUserByCredentials(login, password) == null) {
            if (login.isEmpty() && password.isEmpty()) {
                userCreationText1.setText("Login and password cant be empty");
                return;
            }

            if (userCreationIsAdmin.isSelected()) {

                Manager manager = new Manager(login, password, name, surname, true);
                usersTabController.addNewUserToList(manager);

            } else {
                String ship = userCreationShippingField.getText();
                String bill = userCreationBillingField.getText();
                String card = userCreationCardField.getText();
                String birth = userCreationBirthField.getText();

                Customer customer = new Customer(name, surname, login, password, card, ship, bill, birth);

                usersTabController.addNewUserToList(customer);

            }
        } else {
            userCreationText1.setText("These credentials already taken");
            return;
        }
            Stage stage = (Stage) userCreationAddButton.getScene().getWindow();
            stage.close();

    }

    @FXML private void hideNonAdminFields() {
        if(userCreationIsAdmin.isSelected()) {
            userCreationShippingField.clear();
            userCreationBillingField.clear();
            userCreationCardField.clear();
            userCreationBirthField.clear();
            userCreationShippingField.setDisable(true);
            userCreationBillingField.setDisable(true);
            userCreationCardField.setDisable(true);
            userCreationBirthField.setDisable(true);
        } else {
            userCreationShippingField.setDisable(false);
            userCreationBillingField.setDisable(false);
            userCreationCardField.setDisable(false);
            userCreationBirthField.setDisable(false);
        }
    }

}

