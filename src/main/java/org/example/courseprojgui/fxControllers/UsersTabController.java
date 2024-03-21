package org.example.courseprojgui.fxControllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.model.Customer;
import org.example.courseprojgui.model.Manager;
import org.example.courseprojgui.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


@Getter
@Setter
public class UsersTabController implements Initializable {
    public Text userEditingStatusText;
    public AnchorPane userCreationAncrPaneBase;
    public Button createNewUserButton;
    //private ArrayList<User> userGenList = new ArrayList<>();
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
    @Getter
    public ListView<User> userList;
    private MainController mainController;
    @Getter
    private static UsersTabController instance;
    private boolean isAdminNow = false;
    private GenericHibernate genericHibernate;

    public void addNewUserToList (User user) {
       userList.getItems().add(user);
       genericHibernate.create(user);
    }

    public UsersTabController() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = MainController.getInstance();
        genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
        isVisible(false);
        isEditable(false);

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

        userList.getItems().addAll(genericHibernate.getAllRecords(User.class));

        userEditingStatusText.setVisible(false);

    }

    private void isEditable(boolean val) {
        userNameField.setEditable(val);
        userSurnameField.setEditable(val);
        userShippingAdrField.setEditable(val);
        userBillingAdrField.setEditable(val);
        userCardNumField.setEditable(val);
        userBirthDateField.setEditable(val);
        userStatusField.setEditable(false);
    }
    private void neededFields(boolean isAdmin) {

        if(!isAdmin) {
            userShippingAdrField.setDisable(false);
            userBillingAdrField.setDisable(false);
            userCardNumField.setDisable(false);
            userBirthDateField.setDisable(false);
        } else if (isAdmin) {
            userShippingAdrField.setDisable(true);
            userBillingAdrField.setDisable(true);
            userCardNumField.setDisable(true);
            userBirthDateField.setDisable(true);
        }
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
    private void clearAllFields() {
        userNameField.clear();
        userSurnameField.clear();
        userShippingAdrField.clear();
        userBillingAdrField.clear();
        userCardNumField.clear();
        userBirthDateField.clear();
        userStatusField.clear();
    }

    @FXML private void login() {
        String login = loginTextField.getText();
        String password = passwordTextField1.getText();
        User currentUser = null;

        for (var i = 0; i < userList.getItems().size(); i++) {
            if (userList.getItems().get(i).getLogin().equals(login) && userList.getItems().get(i).getPassword().equals(password)) {
                if (userList.getItems().get(i) instanceof Manager) {
                    currentUser = userList.getItems().get(i);
                    isAdminNow = true;
                }
                if (userList.getItems().get(i) instanceof Customer) currentUser = userList.getItems().get(i);

                this.currentUser = currentUser;
                loginTextField.setDisable(true);
                loginTextField.clear();
                passwordTextField1.setDisable(true);
                passwordTextField1.clear();
                submitEnterInfoButton.setDisable(true);
                loginTextField.setVisible(false);
                passwordTextField1.setVisible(false);
                submitEnterInfoButton.setVisible(false);

                String curentText = "User`s page: " + this.currentUser.getName() + " " + this.currentUser.getSurname();
                userStatusText.setText(curentText);

                isVisible(true);
                clearAllFields();

                userNameField.setText(this.currentUser.getName());
                userSurnameField.setText(this.currentUser.getSurname());

                if (currentUser instanceof Manager) {
                    neededFields(true);
                    userStatusField.setText("Manager");

                } else if(currentUser instanceof Customer) {
                    neededFields(false);
                    userStatusField.setText("Customer");
                    userShippingAdrField.setText(((Customer) currentUser).getShippingAddress());
                    userBillingAdrField.setText(((Customer) currentUser).getBillingAddress());
                    userCardNumField.setText(((Customer) currentUser).getCardNumber());
                    userBirthDateField.setText(String.valueOf(((Customer) currentUser).getBirthDate()));
                } else {
                    System.out.println("current - null");
                }

                mainController.openAllTabs(isAdminNow);
                createNewUserButton.setVisible(false);
                createNewUserButton.setDisable(true);
            } else {
                System.out.println("Not found");
            }
        }

    }

    @FXML private void logOff() {
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
        createNewUserButton.setVisible(true);
        createNewUserButton.setDisable(false);
    }

    @FXML private void loadUserData() {
        User user = userList.getSelectionModel().getSelectedItem();

        if (user instanceof Manager) {
            clearAllFields();
            neededFields(true);
            userNameField.setText(user.getName());
            userSurnameField.setText(user.getSurname());
            userStatusField.setText("Manager");
        } else if (user instanceof Customer) {
            clearAllFields();
            neededFields(false);
            userNameField.setText(user.getName());
            userSurnameField.setText(user.getSurname());
            userStatusField.setText("Customer");
            userShippingAdrField.setText(((Customer) user).getShippingAddress());
            userBillingAdrField.setText(((Customer) user).getBillingAddress());
            userCardNumField.setText(((Customer) user).getCardNumber());
            userBirthDateField.setText(String.valueOf(((Customer) user).getBirthDate()));

        }
    }

    @FXML private void startEditInfo() {
        userEditingStatusText.setVisible(true);
        userEditingStatusText.setText("You can change user info");


        if(currentUser instanceof Manager) {
            if(userList.getSelectionModel().getSelectedItem() != currentUser) {
                userEditingStatusText.setText("You can only own info");
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> userEditingStatusText.setVisible(false));
                pause.play();
                return;
            }
            neededFields(true);
            isEditable(true);

        } else if (currentUser instanceof Customer) {
            neededFields(false);
            isEditable(true);
        }
    }

    @FXML private void saveEditInfo() {
        int userIndex = 0;
        for (var i = 0; i < userList.getItems().size(); i++) {
            if(userList.getItems().get(i).getName() == currentUser.getName() && userList.getItems().get(i).getSurname() == currentUser.getSurname()) {
                userIndex = i;
            }
        }
        currentUser.setName(userNameField.getText());
        currentUser.setSurname(userSurnameField.getText());

        if(currentUser instanceof Manager) {
            neededFields(true);
            isEditable(false);

        } else if (currentUser instanceof Customer) {
            neededFields(false);
            isEditable(false);

            ((Customer) currentUser).setBillingAddress(userBillingAdrField.getText());
            ((Customer) currentUser).setShippingAddress(userShippingAdrField.getText());
            ((Customer) currentUser).setCardNumber(userCardNumField.getText());
            ((Customer) currentUser).setBirthDate(userBirthDateField.getText());
        }
        String curentText = "User`s page: " + currentUser.getName() + " " + currentUser.getSurname();
        userStatusText.setText(curentText);

        //userList.getItems().clear();
        userList.getItems().set(userIndex, currentUser);
        genericHibernate.update(currentUser);

        userEditingStatusText.setText("Saved");
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> userEditingStatusText.setVisible(false));
        pause.play();
    }

    @FXML private void openCreateNewUserMenu() {
        try {

            FXMLLoader loader = new FXMLLoader(ErrorNotFilledPopUp.class.getResource("/org/example/courseprojgui/userCreationWindow.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New Customer");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}

