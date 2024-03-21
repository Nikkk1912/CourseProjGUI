package org.example.courseprojgui.fxControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.model.Cart;
import org.example.courseprojgui.model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ShopTabController implements Initializable {
    public Text shopUserText;
    public ListView<Product> shopProductList;
    public TextField shopAmountField;
    public TextField shopCurrentProdField;
    public TextField shopPriceField;
    public TextField shopTotalPriceField;
    public Button shopAddButton;
    public ListView<Product> shopCartList;
    public Button shopCompleteButton;
    public Button shopRemoveButton;
    public Button shopClearButton;
    public Button shopShowButton;
    private ProductTabController productTabController;
    private UsersTabController usersTabController;
    private GenericHibernate genericHibernate;
    private MainController mainController;
    @Getter
    private static ShopTabController instance;
    private Cart cart;

    public ShopTabController() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());

        shopProductList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText(product.getClass().getSimpleName() +" | "+ product.getTitle());
                }
            }
        });

        shopCartList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText(product.getClass().getSimpleName() +" | "+ product.getTitle());
                }
            }
        });

        shopProductList.setVisible(false);
        shopCurrentProdField.setEditable(false);
        shopPriceField.setEditable(false);
    }

    public void updateShopList() {
        if(productTabController == null) {
            productTabController = ProductTabController.getInstance();
        }
        shopProductList.setItems(productTabController.productAdminList.getItems());
    }

    public void updateProductList() {

    }

    public void showShopProductList() {
        shopUserText.setText("User: "+ usersTabController.getCurrentUser().getName() + " " + usersTabController.getCurrentUser().getSurname());
        shopProductList.setVisible(true);
        shopShowButton.setDisable(true);
        shopShowButton.setVisible(false);
        updateShopList();
    }

    public void loadProductData() {
        Product product = shopProductList.getSelectionModel().getSelectedItem();
        shopCurrentProdField.setText(product.getTitle());
        shopPriceField.setText(String.valueOf(product.getPrice()));
    }

    public void calcTotalPrice() {
        Product product = shopProductList.getSelectionModel().getSelectedItem();
        float totalPrice = product.getPrice() * Float.parseFloat(shopAmountField.getText());
        shopTotalPriceField.setText(String.valueOf(totalPrice));
    }

    public void addToCart() {
        if(cart == null){
            createCart();
        }
        Product product = shopProductList.getSelectionModel().getSelectedItem();
        product.setQuantity(Integer.parseInt(shopAmountField.getText()));
        cart.addItemToCart(product);
        shopCartList.getItems().add(product);

    }

    private void createCart() {
        cart = new Cart();
        cart.setCustomer(usersTabController.getCurrentUser());
//        int currentCartSize = 0;
//        int i = 0;
//        for(var t = 0; i < usersTabController.userList.getItems().size(); t++) {
//            if(usersTabController.userList.getItems().get(t-1) instanceof Manager) {
//                int cartSize = ((Manager) usersTabController.userList.getItems().get(t)).getMyResponsibleCarts().size();
//                if (cartSize < currentCartSize) {
//                    currentCartSize = cartSize;
//                    i = t;
//                }
//            }
//        }
//        if(usersTabController.userList.getItems().get(i) instanceof Manager) {
//            ((Manager) usersTabController.userList.getItems().get(i)).getMyResponsibleCarts().add(cart);
//        }
    }
}
