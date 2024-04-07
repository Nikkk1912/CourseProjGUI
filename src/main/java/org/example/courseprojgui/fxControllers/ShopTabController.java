package org.example.courseprojgui.fxControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.courseprojgui.hibernate.HibernateShop;
import org.example.courseprojgui.model.BodyKit;
import org.example.courseprojgui.model.Product;
import org.example.courseprojgui.model.Spoiler;
import org.example.courseprojgui.model.Wheels;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopTabController implements Initializable {
    public Text shopUserText;
    public ListView<Product> shopProductList;
    public TextField shopCurrentProdField;
    public TextField shopPriceField;
    public TextField shopTotalPriceField;
    public Button shopAddButton;
    public ListView<Product> shopCartList;
    public Button shopCompleteButton;
    public Button shopRemoveButton;
    public Button shopClearButton;
    public TextField shopAmountInStockField;
    public TextArea shopDescriptionField;
    public Button commentsWindowButton;
    private UsersTabController usersTabController;
    @Getter
    private static ShopTabController instance;
    private HibernateShop hibernateShop;

    public ShopTabController() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MainController mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        hibernateShop = new HibernateShop(mainController.getEntityManagerFactory());

        shopCurrentProdField.setEditable(false);
        shopPriceField.setEditable(false);
        shopAmountInStockField.setEditable(false);
        shopDescriptionField.setEditable(false);
        shopTotalPriceField.setEditable(false);

        setCellFactories();

        shopProductList.getItems().setAll(hibernateShop.loadAvailableProducts());

    }

    private void setCellFactories() {
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


    }

    @FXML private void loadProductData() {
        if(shopProductList.getItems() ==null) {
            shopProductList.getItems().setAll(hibernateShop.loadAvailableProducts());
        }
        Product product = shopProductList.getSelectionModel().getSelectedItem();
        if(product != null) {
            shopCurrentProdField.setText(product.getTitle());
            shopPriceField.setText(String.valueOf(product.getPrice()));
            shopAmountInStockField.setText(String.valueOf(product.getQuantity()));
            if (product instanceof Spoiler) {
                shopDescriptionField.setText(((Spoiler) product).genText());
            } else if (product instanceof Wheels) {
                shopDescriptionField.setText(((Wheels) product).genText());
            } else if (product instanceof BodyKit) {
                shopDescriptionField.setText(((BodyKit) product).genText());
            }
            double totalPrice = Double.parseDouble(shopAmountInStockField.getText()) * Double.parseDouble(shopPriceField.getText());
            shopTotalPriceField.setText(String.valueOf(totalPrice));
        }

    }

    @FXML private void addToCart() {
        Product product = shopProductList.getSelectionModel().getSelectedItem();
        shopCartList.getItems().add(product);
        shopProductList.getItems().remove(product);

    }

    @FXML private void clearCart() {
        List<Product> cartItems = new ArrayList<>(shopCartList.getItems());
        for(Product p : cartItems) {
            shopProductList.getItems().add(p);
            shopCartList.getItems().remove(p);
        }
    }

    @FXML private void submitCart() {
        hibernateShop.createCart(shopCartList.getItems(), usersTabController.getCurrentUser());
        shopCartList.getItems().clear();
        shopProductList.getItems().setAll(hibernateShop.loadAvailableProducts());
    }

    @FXML private void removeFromCart(){
        Product product = shopCartList.getSelectionModel().getSelectedItem();
        shopProductList.getItems().add(product);
        shopCartList.getItems().remove(product);
    }

    public void updateProductList() {
        shopProductList.getItems().setAll(hibernateShop.loadAvailableProducts());
    }

    @FXML private void openCommentsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(UserCreationController.class.getResource("/org/example/courseprojgui/commentsWindow.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Comments");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
}
