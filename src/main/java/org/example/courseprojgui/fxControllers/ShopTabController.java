package org.example.courseprojgui.fxControllers;

import javafx.event.ActionEvent;
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
    public ListView<Cart> shopCartList;
    public Button shopCompleteButton;
    public Button shopRemoveButton;
    public Button shopClearButton;
    public Button shopShowButton;
    private ProductTabController productTabController;
    private GenericHibernate genericHibernate;
    private MainController mainController;
    @Getter
    private static ShopTabController instance;

    public ShopTabController() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        mainController = MainController.getInstance();
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

        shopProductList.setVisible(false);
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
        shopProductList.setVisible(true);
        shopShowButton.setDisable(true);
        shopShowButton.setVisible(false);
        updateShopList();
    }
}
