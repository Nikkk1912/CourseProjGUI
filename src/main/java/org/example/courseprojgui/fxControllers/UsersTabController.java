package org.example.courseprojgui.fxControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.Setter;
import org.example.courseprojgui.model.Customer;
import org.example.courseprojgui.model.Manager;
import org.example.courseprojgui.model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class UsersTabController implements Initializable {
    @Getter
    @Setter

    private ArrayList<User> userGenList = new ArrayList<>();
    @Getter
    private User currentUser;
    public Text userStatusText;
    public TextField loginTextField;
    public TextField passwordTextField1;
    public Button submitEnterInfoButton;
    public Button logOffButton;
    public Button editButton;
    public TextField userNameField;
    public TextField userSurnameField;
    public TextField userShippingAdrField;
    public TextField userBillingAdrField;
    public TextField userCardNumField;
    public TextField userBirthDateField;
    public TextField userStatusField;
    public Button saveChangesButton;
    public Text userNameText;
    public Text userSurnameText;
    public Text userShipText;
    public Text userBillText;
    public Text userCardText;
    public Text userBirthText;
    public Text userStatText;
    public ListView<User> userList;
    private MainController mainController;
    @Getter
    private static UsersTabController instance;

    private boolean isAdminNow = false;

    public boolean getIsAdminNow() {
        return isAdminNow;
    }

    public UsersTabController() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = MainController.getInstance();
        isVisible(false);
        isEditable(false);
        userGenList.add(new Manager("admin", "admin", "admin", "test", true));
        userGenList.add(new Customer("cust", "test", "cust", "cust", "1234", "home", "bank", "19 12 2004"));

            userList.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(User user, boolean empty) {
                    super.updateItem(user, empty);
                    if (empty || user == null) {
                        setText(null);
                    } else {
                        setFont(Font.font(16));
                        setText(user.getName() + " : " + user.getSurname());
                    }
                }
            });
        userList.getItems().addAll(userGenList);

    }

    private void isEditable(boolean val) {
        userNameField.setEditable(val);
        userSurnameField.setEditable(val);
        userShippingAdrField.setEditable(val);
        userBillingAdrField.setEditable(val);
        userCardNumField.setEditable(val);
        userBirthDateField.setEditable(val);
        userStatusField.setEditable(val);
    }

    private void isDisable(boolean val) {
        userNameField.setDisable(val);
        userSurnameField.setDisable(val);
        userShippingAdrField.setDisable(val);
        userBillingAdrField.setDisable(val);
        userCardNumField.setDisable(val);
        userBirthDateField.setDisable(val);
        userStatusField.setDisable(val);
    }

    private void isVisible(boolean val) {
        logOffButton.setVisible(val);
        logOffButton.setDisable(!val);
        editButton.setVisible(val);
        editButton.setDisable(!val);
        userNameField.setVisible(val);
        userNameField.setDisable(!val);
        userSurnameField.setVisible(val);
        userSurnameField.setDisable(!val);
        userShippingAdrField.setVisible(val);
        userShippingAdrField.setDisable(!val);
        userBillingAdrField.setVisible(val);
        userBillingAdrField.setDisable(!val);
        userCardNumField.setVisible(val);
        userCardNumField.setDisable(!val);
        userBirthDateField.setVisible(val);
        userBirthDateField.setDisable(!val);
        userStatusField.setVisible(val);
        userStatusField.setDisable(!val);
        saveChangesButton.setVisible(val);
        saveChangesButton.setDisable(!val);
        userNameText.setVisible(val);
        userSurnameText.setVisible(val);
        userShipText.setVisible(val);
        userBillText.setVisible(val);
        userCardText.setVisible(val);
        userBirthText.setVisible(val);
        userStatText.setVisible(val);
        userList.setVisible(val);
        userNameText.setDisable(!val);
        userSurnameText.setDisable(!val);
        userShipText.setDisable(!val);
        userBillText.setDisable(!val);
        userCardText.setDisable(!val);
        userBirthText.setDisable(!val);
        userStatText.setDisable(!val);
        userList.setDisable(!val);

        if(currentUser instanceof Customer || currentUser == null) {
            userList.setVisible(false);
            userList.setDisable(true);
        }
    }

    @FXML
    private void login() {
        String login = loginTextField.getText();
        String password = passwordTextField1.getText();
        User currentUser = null;

        for (var i = 0; i < userGenList.size(); i++) {
            if (userGenList.get(i).getLogin().equals(login) && userGenList.get(i).getPassword().equals(password)) {
                if (userGenList.get(i) instanceof Manager) {
                    currentUser = userGenList.get(i);
                    isAdminNow = true;
                }
                if (userGenList.get(i) instanceof Customer) currentUser = userGenList.get(i);
                this.currentUser = currentUser;
                loginTextField.setDisable(true);
                loginTextField.clear();
                passwordTextField1.setDisable(true);
                passwordTextField1.clear();
                submitEnterInfoButton.setDisable(true);
                loginTextField.setVisible(false);
                passwordTextField1.setVisible(false);
                submitEnterInfoButton.setVisible(false);

                String currnetText = userStatusText.getText();
                currnetText = currnetText + " " + this.currentUser.getName() + " " + this.currentUser.getSurname();
                userStatusText.setText(currnetText);
                isVisible(true);
                fillFields();
                mainController.openAllTabs(isAdminNow);

            } else {
                System.out.println("not valid");
            }
        }

    }
    @FXML
    private void logOff() {
        this.currentUser = null;
        loginTextField.setDisable(false);
        passwordTextField1.setDisable(false);
        submitEnterInfoButton.setDisable(false);
        loginTextField.setVisible(true);
        passwordTextField1.setVisible(true);
        submitEnterInfoButton.setVisible(true);
        userStatusText.setText("User`s page:");
        isVisible(false);
        mainController.closeAllTabs();
        isAdminNow = false;
    }

    private void fillFields() {
        userNameField.setText(this.currentUser.getName());
        userSurnameField.setText(this.currentUser.getSurname());

        if (currentUser instanceof Manager) {
            userStatusField.setText("Admin");

        } else if(currentUser instanceof Customer) {
            userStatusField.setText("Customer");
            userShippingAdrField.setText(((Customer) currentUser).getShippingAddress());
            userBillingAdrField.setText(((Customer) currentUser).getBillingAddress());
            userCardNumField.setText(((Customer) currentUser).getCardNumber());
            userBirthDateField.setText(String.valueOf(((Customer) currentUser).getBirthDate()));
        } else {
            System.out.println("current - null");
        }
    }
}

