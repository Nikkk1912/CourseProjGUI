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
import org.example.courseprojgui.model.*;

import java.net.URL;
import java.util.Objects;
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
    public TextField shopAmountInStockField;
    public TextField shopPriceField1;
    public TextField shopDescriptionField;
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
                    setText(product.getClass().getSimpleName() +" | "+ product.getTitle() + " | " + product.getQuantity());
                }
            }
        });

        shopCurrentProdField.setEditable(false);
        shopPriceField.setEditable(false);
        shopAmountInStockField.setEditable(false);
        shopDescriptionField.setEditable(false);

        shopProductList.getItems().setAll(genericHibernate.getAllRecords(Product.class));
    }

    public void updateShopList() {
        if(productTabController == null) {
            productTabController = ProductTabController.getInstance();
        }
        shopProductList.setItems(productTabController.productAdminList.getItems());
    }

    public void updateProductList() {

    }


    public void calcTotalPrice() {
        Product product = shopProductList.getSelectionModel().getSelectedItem();
        float totalPrice;
        if (!Objects.equals(shopAmountField.getText(), "")) {
            totalPrice = product.getPrice() * Float.parseFloat(shopAmountField.getText());
        } else {
            return;
        }
        shopTotalPriceField.setText(String.valueOf(totalPrice));
    }

}
