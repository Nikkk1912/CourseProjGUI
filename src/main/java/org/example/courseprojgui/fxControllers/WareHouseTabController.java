package org.example.courseprojgui.fxControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.hibernate.HibernateShop;
import org.example.courseprojgui.model.Manager;
import org.example.courseprojgui.model.Product;
import org.example.courseprojgui.model.Warehouse;

import java.net.URL;
import java.util.List;
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
    public TextField warehouseAddressField;
    public TextField warehouseIdField;
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
        warehouseIdField.setEditable(false);
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
    }

    public void deleteWarehouse() {
        Warehouse warehouse = warehouseListOfWarehouses.getSelectionModel().getSelectedItem();

        List<Manager> adminsOfWarehouse = genericHibernate.getEntityByWarehouseId(Manager.class, warehouse.getId());
        for(Manager m:adminsOfWarehouse) {
            m.setWarehouse(null);
            genericHibernate.update(m);
        }
        List<Product> productsOfWarehouse = genericHibernate.getEntityByWarehouseId(Product.class, warehouse.getId());
        for(Product p:productsOfWarehouse) {
            p.setWarehouse(null);
            genericHibernate.update(p);
        }
        warehouse.setManagers(null);
        warehouse.setStock(null);
        genericHibernate.update(warehouse);

        warehouseListOfWarehouses.getItems().remove(warehouse);
        genericHibernate.delete(warehouse.getClass(), warehouse.getId());
        warehouseListProducts.getItems().clear();
        warehouseListAdmins.getItems().clear();
    }

    public void editWarehouse() {
        Warehouse warehouse = warehouseListOfWarehouses.getSelectionModel().getSelectedItem();
        if(warehouse != null) {
            warehouse.setAddress(warehouseAddressField.getText());
            genericHibernate.update(warehouse);
            warehouseAddressField.clear();
        }

    }

    public void createWarehouse() {
        Warehouse warehouse = new Warehouse(warehouseAddressField.getText());
        genericHibernate.create(warehouse);
        warehouseListOfWarehouses.getItems().add(warehouse);
        warehouseAddressField.clear();
    }

    public void loadWarehouse() {

        Warehouse warehouse = warehouseListOfWarehouses.getSelectionModel().getSelectedItem();
        if(warehouse != null) {
            warehouseIdField.setText(String.valueOf(warehouse.getId()));
            warehouseAddressField.setText(warehouse.getAddress());
            warehouseListProducts.getItems().clear();
            warehouseListProducts.getItems().addAll(genericHibernate.getEntityByWarehouseId(Product.class, warehouse.getId()));
            warehouseListAdmins.getItems().clear();
            warehouseListAdmins.getItems().addAll(genericHibernate.getEntityByWarehouseId(Manager.class, warehouse.getId()));
        }

    }

    public void assignManagerToWarehouse() {
        HibernateShop hibernateShop = new HibernateShop(mainController.getEntityManagerFactory());
        Warehouse warehouse = warehouseListOfWarehouses.getSelectionModel().getSelectedItem();
        Manager manager = (Manager) hibernateShop.getUserByCredentials(warehouseAdminLogField.getText(), warehouseAdminPassFiled.getText());
        System.out.println(manager);
        if (manager == null) {
            warehouseAssignStatusText.setVisible(true);
            warehouseAdminLogField.clear();
            warehouseAdminPassFiled.clear();
            return;
        }
        warehouseAssignStatusText.setVisible(false);
        if (manager.getWarehouse() == null || manager.getWarehouse().getId() != warehouse.getId()) {
            manager.setWarehouse(warehouse);
            genericHibernate.update(manager);
            warehouseListAdmins.getItems().add(manager);
        }
        warehouseAdminLogField.clear();
        warehouseAdminPassFiled.clear();
    }
}
