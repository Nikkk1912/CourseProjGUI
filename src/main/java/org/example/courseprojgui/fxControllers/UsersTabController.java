package org.example.courseprojgui.fxControllers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.hibernate.HibernateShop;
import org.example.courseprojgui.model.Customer;
import org.example.courseprojgui.model.Manager;
import org.example.courseprojgui.model.User;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;


@Getter
@Setter
public class UsersTabController implements Initializable {

    public Text userNameText;
    public Text userSurnameText;
    public Text userShipText;
    public Text userBillText;
    public Text userCardText;
    public Text userBirthText;
    public TextField userNameField;
    public TextField userSurnameField;
    public TextField userShippingAdrField;
    public TextField userBillingAdrField;
    public TextField userCardNumField;
    public TextField userBirthDateField;
    public TableColumn<ManagerTableParameters, String> statusCol;
    public TableColumn<ManagerTableParameters, String> shippingCol;
    public TableColumn<ManagerTableParameters, String> billingCol;
    public TableColumn<ManagerTableParameters, String> birthdayCol;
    public Button removeMyWarehouseButton;
    public Text myWarehouseText;
    public TextField myWarehouseTextField;
    public TableColumn deleteColumn;
    private ObservableList<ManagerTableParameters> data = FXCollections.observableArrayList();
    public TableView<ManagerTableParameters> managerTable;
    public TableColumn<ManagerTableParameters, Integer> idCol;
    public TableColumn<ManagerTableParameters, String> loginCol;
    public TableColumn<ManagerTableParameters, String> nameCol;
    public TableColumn<ManagerTableParameters, String> surnameCol;
    public Text userEditingStatusText;
    public AnchorPane userCreationAnchorPaneBase;
    public Button createNewUserButton;
    public Text userStatusText;
    public TextField loginTextField;
    public PasswordField passwordTextField;
    public Button submitEnterInfoButton;
    public Button logOffButton;
    public Button editButton;
    public Button saveChangesButton;

    @Getter
    private MainController mainController;
    @Getter
    private static UsersTabController instance;
    private boolean isAdminNow = false;
    private GenericHibernate genericHibernate;
    private ShopTabController shopTabController;
    private User currentUser;


    public UsersTabController() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = MainController.getInstance();
        shopTabController = ShopTabController.getInstance();
        genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());

        isEditable(false);
        openLoginFields(true);

        managerTable.setEditable(true);
        setAllCellFactories();
        fillManagerTable();

        myWarehouseTextField.setEditable(false);
        loadMyWarehouse();

    }

    private void setAllCellFactories() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginCol.setCellValueFactory(new PropertyValueFactory<>("login"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            String status = String.valueOf(event.getRowValue().status.getValue());
            if (status.equals("Manager")) {
                Manager manager = genericHibernate.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                manager.setName(event.getNewValue());
                genericHibernate.update(manager);
            } else if (status.equals("Customer")) {
                Customer customer = genericHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                customer.setName(event.getNewValue());
                genericHibernate.update(customer);
            }
        });
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));


        surnameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        surnameCol.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            String status = String.valueOf(event.getRowValue().status.getValue());
            if (status.equals("Manager")) {
                Manager manager = genericHibernate.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                manager.setSurname(event.getNewValue());
                genericHibernate.update(manager);
            } else if (status.equals("Customer")) {
                Customer customer = genericHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                customer.setSurname(event.getNewValue());
                genericHibernate.update(customer);
            }
        });
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));


        shippingCol.setCellFactory(TextFieldTableCell.forTableColumn());
        shippingCol.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            String status = String.valueOf(event.getRowValue().status.getValue());
            if (status.equals("Manager")) {
                return;
            } else if (status.equals("Customer")) {
                Customer customer = genericHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                customer.setShippingAddress(event.getNewValue());
                genericHibernate.update(customer);
            }
        });
        shippingCol.setCellValueFactory(new PropertyValueFactory<>("shippingAddress"));


        billingCol.setCellFactory(TextFieldTableCell.forTableColumn());
        billingCol.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            String status = String.valueOf(event.getRowValue().status.getValue());
            if (status.equals("Manager")) {
                return;
            } else if (status.equals("Customer")) {
                Customer customer = genericHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                customer.setBillingAddress(event.getNewValue());
                genericHibernate.update(customer);
            }
        });
        billingCol.setCellValueFactory(new PropertyValueFactory<>("billingAddress"));


        birthdayCol.setCellFactory(TextFieldTableCell.forTableColumn());
        birthdayCol.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(event.getNewValue());
            String status = String.valueOf(event.getRowValue().status.getValue());
            if (status.equals("Manager")) {
                return;
            } else if (status.equals("Customer")) {
                Customer customer = genericHibernate.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
                customer.setBirthDate(event.getNewValue());
                genericHibernate.update(customer);
            }
        });
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthDate"));


        Callback<TableColumn<ManagerTableParameters, Void>, TableCell<ManagerTableParameters, Void>> callback = param -> {
            final TableCell<ManagerTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        ManagerTableParameters row = getTableView().getItems().get(getIndex());

                        String status = String.valueOf(row.getStatus());
                        if (status.equals("Manager")) {
                            genericHibernate.delete(Manager.class, row.getId());
                        } else if (status.equals("Customer")) {
                            genericHibernate.delete(Customer.class, row.getId());
                        }
                        fillManagerTable();

                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };
        deleteColumn.setCellFactory(callback);
    }

    @FXML private void fillManagerTable() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        List<User> userList = genericHibernate.getAllRecords(User.class);
        data.clear();
        for (User m:userList) {
            ManagerTableParameters tableParam = new ManagerTableParameters();
            tableParam.setId(m.getId());
            tableParam.setLogin(m.getLogin());
            tableParam.setName(m.getName());
            tableParam.setSurname(m.getSurname());
            tableParam.setStatus(m.getClass().getSimpleName());
            if (m instanceof Customer) {
                Customer customer = (Customer) m;
                tableParam.setBillingAddress(customer.getBillingAddress());
                tableParam.setShippingAddress(customer.getShippingAddress());
                tableParam.setBirthDate(customer.getBirthDate().format(formatter));
            } else {
                tableParam.setBillingAddress("");
                tableParam.setShippingAddress("");
                tableParam.setBirthDate("");
            }
            data.add(tableParam);
        }
        managerTable.setItems(data);
    }

    @FXML private void openCreateNewUserMenu() {
        try {

            FXMLLoader loader = new FXMLLoader(UserCreationController.class.getResource("/org/example/courseprojgui/userCreationWindow.fxml"));
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

    @FXML private void deleteMeFromWarehouse() {
        Manager manager = (Manager) currentUser;
        manager.setWarehouse(null);
        genericHibernate.update(manager);
        currentUser = manager;
        myWarehouseTextField.clear();
    }

    @FXML private void logOff() {
        mainController.closeAllTabs();
        openLoginFields(true);
        currentUser=null;
        loginTextField.clear();
        passwordTextField.clear();
    }

    @FXML private void startEditInfo() {
        userEditingStatusText.setVisible(true);
        userEditingStatusText.setText("You can change user info");
        isEditable(true);
    }

    @FXML private void saveEditInfo() {

        isEditable(false);
        currentUser.setName(userNameField.getText());
        currentUser.setSurname(userSurnameField.getText());
        ((Customer) currentUser).setBillingAddress(userBillingAdrField.getText());
        ((Customer) currentUser).setShippingAddress(userShippingAdrField.getText());
        ((Customer) currentUser).setCardNumber(userCardNumField.getText());
        ((Customer) currentUser).setBirthDate(userBirthDateField.getText());
        genericHibernate.update(currentUser);

        userEditingStatusText.setText("Saved");
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> userEditingStatusText.setVisible(false));
        pause.play();
    }

    @FXML private void loadMyWarehouse() {
        myWarehouseTextField.clear();
        if(currentUser != null) {
            currentUser = genericHibernate.getEntityById(currentUser.getClass(), currentUser.getId());
        }
        if (currentUser != null && ((Manager) currentUser).getWarehouse() != null) {
            myWarehouseTextField.setText("Id: " + ((Manager) currentUser).getWarehouse().getId() + " | " + ((Manager) currentUser).getWarehouse().getAddress());
        }
    }

    private void openManagerField(boolean val) {
        userStatusText.setText("Manager");

        editButton.setVisible(!val);
        saveChangesButton.setVisible(!val);
        createNewUserButton.setVisible(!val);
        loginTextField.setVisible(!val);
        passwordTextField.setVisible(!val);
        submitEnterInfoButton.setVisible(!val);
        userEditingStatusText.setVisible(!val);

        managerTable.setVisible(val);
        logOffButton.setVisible(val);
        removeMyWarehouseButton.setVisible(val);
        myWarehouseText.setVisible(val);
        myWarehouseTextField.setVisible(val);
    }

    private void openCustomerField(boolean val) {
        userStatusText.setText("Customer");

        createNewUserButton.setVisible(!val);
        loginTextField.setVisible(!val);
        passwordTextField.setVisible(!val);
        submitEnterInfoButton.setVisible(!val);
        managerTable.setVisible(!val);
        userEditingStatusText.setVisible(!val);

        editButton.setVisible(val);
        saveChangesButton.setVisible(val);
        logOffButton.setVisible(val);
        userNameText.setVisible(val);
        userSurnameText.setVisible(val);
        userShipText.setVisible(val);
        userBillText.setVisible(val);
        userCardText.setVisible(val);
        userBirthText.setVisible(val);
        userNameField.setVisible(val);
        userSurnameField.setVisible(val);
        userShippingAdrField.setVisible(val);
        userBillingAdrField.setVisible(val);
        userCardNumField.setVisible(val);
        userBirthDateField.setVisible(val);

        userNameField.setText(currentUser.getName());
        userSurnameField.setText(currentUser.getSurname());
        userShippingAdrField.setText(((Customer) currentUser).getShippingAddress());
        userBillingAdrField.setText(((Customer) currentUser).getBillingAddress());
        userCardNumField.setText(((Customer) currentUser).getCardNumber());
        userBirthDateField.setText(String.valueOf(((Customer) currentUser).getBirthDate()));
    }

    private void openLoginFields(boolean val) {
        userStatusText.setText("Login");

        createNewUserButton.setVisible(val);
        loginTextField.setVisible(val);
        passwordTextField.setVisible(val);
        submitEnterInfoButton.setVisible(val);

        logOffButton.setVisible(!val);
        editButton.setVisible(!val);
        saveChangesButton.setVisible(!val);
        userEditingStatusText.setVisible(!val);
        managerTable.setVisible(!val);
        userNameText.setVisible(!val);
        userSurnameText.setVisible(!val);
        userShipText.setVisible(!val);
        userBillText.setVisible(!val);
        userCardText.setVisible(!val);
        userBirthText.setVisible(!val);
        userNameField.setVisible(!val);
        userSurnameField.setVisible(!val);
        userShippingAdrField.setVisible(!val);
        userBillingAdrField.setVisible(!val);
        userCardNumField.setVisible(!val);
        userBirthDateField.setVisible(!val);
        removeMyWarehouseButton.setVisible(!val);
        myWarehouseText.setVisible(!val);
        myWarehouseTextField.setVisible(!val);
    }

    public void checkAndLogin() throws IOException {
        HibernateShop hibernateShop = new HibernateShop(mainController.getEntityManagerFactory());
        User user = hibernateShop.getUserByCredentials(loginTextField.getText(), passwordTextField.getText());
        currentUser = user;
        if(user != null) {
            if (user instanceof Manager) {
                openManagerField(true);
                mainController.openAllTabs(true);
            }else if (user instanceof Customer) {
                openCustomerField(true);
                mainController.openAllTabs(false);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System message");
            alert.setHeaderText("Error during login");
            alert.setContentText("No such user");
            alert.showAndWait();
        }
    }

    private void isEditable(boolean val) {
        userNameField.setEditable(val);
        userSurnameField.setEditable(val);
        userShippingAdrField.setEditable(val);
        userBillingAdrField.setEditable(val);
        userCardNumField.setEditable(val);
        userBirthDateField.setEditable(val);
    }

    public void addNewUserToList (User user) {
        genericHibernate.create(user);
        fillManagerTable();
    }

}

