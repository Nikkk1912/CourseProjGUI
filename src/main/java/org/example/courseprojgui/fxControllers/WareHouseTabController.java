package org.example.courseprojgui.fxControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.model.Manager;
import org.example.courseprojgui.model.Product;
import org.example.courseprojgui.model.Warehouse;

import java.net.URL;
import java.util.ResourceBundle;

public class WareHouseTabController implements Initializable {

    public Text warehouseStatusText;
    public ListView<Warehouse> warehouseListOfWarehouses;
    public Button warehouseCreateButton;
    public Button warehouseEditButton;
    public Button warehouseDeleteButton;
    public Text warehouseAddressText;
    public ListView<Manager> warehouseListAdmins;
    public ListView<Product> warehouseListProducts;
    public TextField warehouseAdminLogField;
    public TextField warehouseAdminPassFiled;
    public Button warehouseAssignAdminButton;
    public Text warehouseAssignStatusText;
    private UsersTabController usersTabController;
    private MainController mainController;
    GenericHibernate genericHibernate;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
        warehouseAssignStatusText.setVisible(false);
        warehouseListOfWarehouses.getItems().setAll(genericHibernate.getAllRecords(Warehouse.class));

        warehouseListOfWarehouses.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Warehouse warehouse, boolean empty) {
                super.updateItem(warehouse, empty);
                if (empty || warehouse == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText("ID: " + warehouse.getId() + " | " + warehouse.getAddress());
                }
            }
        });

        warehouseListAdmins.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Manager manager, boolean empty) {
                super.updateItem(manager, empty);
                if (empty || manager == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText(manager.getName() + " : " + manager.getSurname());
                }
            }
        });

        warehouseListProducts.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText(product.getTitle() + " : " + product.getQuantity());
                }
            }
        });


//        Warehouse test1 = new Warehouse("Vilnius");
//        warehouseListOfWarehouses.getItems().add(test1);
//        Warehouse test2 = new Warehouse("Kaunas");
//        warehouseListOfWarehouses.getItems().add(test2);
//        genericHibernate.create(test1);
//        genericHibernate.create(test2);
    }

    public void deleteWarehouse() {
    }

    public void editWarehouse() {
    }

    public void createWarehouse() {
    }

    public void loadWarehouse() {

        Warehouse warehouse = warehouseListOfWarehouses.getSelectionModel().getSelectedItem();
        if(warehouse != null) {
            warehouseAddressText.setText("Address: " + warehouse.getAddress());
            warehouseListProducts.getItems().clear();
            warehouseListProducts.getItems().addAll(genericHibernate.getEntityByWarehouseId(Product.class, warehouse.getId()));
            System.out.println(warehouseListProducts.getItems());
            warehouseListAdmins.getItems().clear();
            warehouseListAdmins.getItems().addAll(genericHibernate.getEntityByWarehouseId(Manager.class, warehouse.getId()));
        }

    }

}
