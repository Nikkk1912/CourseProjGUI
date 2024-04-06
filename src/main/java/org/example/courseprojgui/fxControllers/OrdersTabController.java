package org.example.courseprojgui.fxControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.hibernate.HibernateShop;
import org.example.courseprojgui.model.Cart;
import org.example.courseprojgui.model.Product;
import org.example.courseprojgui.model.User;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrdersTabController implements Initializable {


    public Text myCartsText;
    public ListView<Cart> cartsList;
    public ListView<Product> productList;
    public TextField ownerField;
    public TextField managerField;
    public TextField idField;
    public Button deleteButton;
    public Button refreshButton;
    private HibernateShop hibernateShop;
    private GenericHibernate genericHibernate;
    private UsersTabController usersTabController;
    private ShopTabController shopTabController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainController mainController = MainController.getInstance();
        hibernateShop = new HibernateShop(mainController.getEntityManagerFactory());
        genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
        usersTabController = UsersTabController.getInstance();
        shopTabController = ShopTabController.getInstance();

        setCellFactories();

        ownerField.setEditable(false);
        managerField.setEditable(false);
        idField.setEditable(false);
    }

    @FXML private void loadCartData() {
        User currentUser = genericHibernate.getEntityById(usersTabController.getCurrentUser().getClass(), usersTabController.getCurrentUser().getId());
        Cart cart = cartsList.getSelectionModel().getSelectedItem();

        myCartsText.setText("Orders : " + usersTabController.getCurrentUser().getName() + " " + usersTabController.getCurrentUser().getSurname());
        ownerField.setText(currentUser.getName() + " " + currentUser.getSurname());
        if(cart != null) {
            managerField.setText(cart.getManager().getName() + " " + cart.getManager().getSurname());
            idField.setText(String.valueOf(cart.getId()));
            loadProductData(cart);
        }
    }

    private void loadProductData(Cart cart) {
        productList.getItems().setAll(cart.getItemsToBuy());
    }

    private void setCellFactories() {
        cartsList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Cart cart, boolean empty) {
                super.updateItem(cart, empty);
                if (empty || cart == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText("Cart " + cart.getId() + " | Manager: " + cart.getManager().getName() + " | " + cart.getManager().getSurname());
                }
            }
        });

        productList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText("Id: " + product.getId() + " | " + product.getClass().getSimpleName() + " | " + product.getTitle() + " : " + product.getQuantity());
                }
            }
        });
    }

    @FXML private void deleteCart() {
        Cart cart = cartsList.getSelectionModel().getSelectedItem();
        hibernateShop.deleteCart(cart.getId());
        cartsList.getItems().remove(cart);
        productList.getItems().clear();
        shopTabController.updateProductList();
        idField.clear();
        managerField.clear();
        ownerField.clear();

    }

    @FXML private void refreshCartsList() {
        User currentUser = genericHibernate.getEntityById(usersTabController.getCurrentUser().getClass(), usersTabController.getCurrentUser().getId());
        List<Cart> carts = hibernateShop.getCartsByCustomerId(currentUser.getId());
        cartsList.getItems().setAll(carts);
    }
}
