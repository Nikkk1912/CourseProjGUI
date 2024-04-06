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
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.model.*;

import java.net.URL;
import java.util.*;

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
    private List<Product> generalListOfProduct = new ArrayList<>();

    public ShopTabController() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());

        shopCurrentProdField.setEditable(false);
        shopPriceField.setEditable(false);
        shopAmountInStockField.setEditable(false);
        shopDescriptionField.setEditable(false);
        shopTotalPriceField.setEditable(false);

        setCellFactories();

        updateGeneralShopList();
        shopProductList.getItems().setAll(generalListOfProduct);

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

        shopAmountField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double amount = Double.parseDouble(newValue);
                double availableStock = Double.parseDouble(shopAmountInStockField.getText());

                if(amount > availableStock) {
                    shopTotalPriceField.setText("Not enough product in stock");
                } else {
                    double price = Double.parseDouble(shopPriceField.getText());
                    double totalPrice = amount * price;
                    shopTotalPriceField.setText(String.valueOf(totalPrice));
                }
            } catch (NumberFormatException e) {
                shopTotalPriceField.setText("");
            }
        });
    }

    private void updateGeneralShopList() {
        generalListOfProduct = genericHibernate.getAllRecords(Product.class);
    }



    @FXML private void loadProductData() {
        //shopUserText.setText("User: "+ usersTabController.getCurrentUser().getName() + " " + usersTabController.getCurrentUser().getSurname());
        Product product = shopProductList.getSelectionModel().getSelectedItem();
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

    }


    public void addToCart() {
        if(cart == null){
            createCart();
        }


        Product selectedProduct = shopProductList.getSelectionModel().getSelectedItem();
        Product productToAdd = new Product(selectedProduct);

        int amountInStock = selectedProduct.getQuantity();
        int amountToSet = Integer.parseInt(shopAmountField.getText());

        if (amountToSet < amountInStock) {
            productToAdd.setQuantity(amountToSet);

            int amountLeft = amountInStock - amountToSet;
            selectedProduct.setQuantity(amountLeft);
        } else if (amountToSet == amountInStock) {
            productToAdd.setQuantity(amountToSet);
            shopProductList.getItems().remove(selectedProduct);
        } else if (amountToSet > amountInStock) {
            return;
        }


        cart.addItemToCart(productToAdd);
        shopCartList.getItems().setAll(cart.getItemsToBuy());

    }

    private Manager findManagerWithLowestCarts() {
        List<Manager> managers = genericHibernate.getAllRecords(Manager.class);
        Optional<Manager> minManager = managers.stream().min(Comparator.comparingInt(manager -> manager.getMyResponsibleCarts().size()));
        return minManager.orElse(null);
    }

    private void createCart() {
        Manager whoWillManage = findManagerWithLowestCarts();
        User whoWillOwn = usersTabController.getCurrentUser();

        cart = new Cart(whoWillOwn, whoWillManage);
        //genericHibernate.create(cart);

    }

    @FXML private void clearCart() {
        updateGeneralShopList();
        shopProductList.getItems().setAll(generalListOfProduct);
        shopCartList.getItems().clear();
    }

    @FXML private void submitCart() {
        genericHibernate.create(cart);
        cart = null;
        for(Product p : shopProductList.getItems()) {
            genericHibernate.update(p);
        }
        for (Product p : shopCartList.getItems()) {
            genericHibernate.create(p);
        }
        shopProductList.getItems().setAll(genericHibernate.getAllRecords(Product.class));
        shopCartList.getItems().clear();

    }
}
