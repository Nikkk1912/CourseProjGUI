package org.example.courseprojgui.fxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import lombok.Getter;
import org.example.courseprojgui.enums.KitType;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.hibernate.HibernateShop;
import org.example.courseprojgui.model.*;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ProductTabController implements Initializable {

    public AnchorPane productsRoot;
    @Getter
    public ListView<Product> productAdminList;
    public TextField productTitleField;
    public TextArea productDescriptionField;
    public TextField productQuantityField;
    public TextField productPriceField;
    public RadioButton productBodyKitRadio;
    public RadioButton productWheelsRadio;
    public RadioButton productSpoilerRadio;
    public TextField productBrandField;
    public TextField productCompatibleCarsField;
    public TextField productCountryManufacturerField;
    public ComboBox<KitType> productKitTypeComboBox;
    public TextField productMaterialField;
    public TextField productWeightField;
    public TextField productColorField;
    public TextField productWheelSizeField;
    public ComboBox productWarehouseComboBox;
    public CheckBox productSoldCheck;
    private MainController mainController;
    private GenericHibernate genericHibernate;
    private HibernateShop hibernateShop;
    @Getter
    private static ProductTabController instance;
    private static ShopTabController shopTabController;

    public ProductTabController() {
        instance = this;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainController = MainController.getInstance();
        shopTabController = ShopTabController.getInstance();
        genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
        hibernateShop = new HibernateShop(mainController.getEntityManagerFactory());

        ObservableList<KitType> kitTypes = FXCollections.observableArrayList(KitType.values());
        productKitTypeComboBox.setItems(kitTypes);
        turnOffAllFields();
        loadWarehousesForComboBox();
        productSoldCheck.setDisable(true);


        productAdminList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText("Id: " + product.getId() + " | " + product.getTitle() + " : " + product.getQuantity());
                }
            }
        });

        productAdminList.getItems().setAll(genericHibernate.getAllRecords(Product.class));
    }


    @FXML private void neededFieldsForProducts() {
        turnOffAllFields();
        if (productSpoilerRadio.isSelected()) {

            productTitleField.setDisable(false);
            productDescriptionField.setDisable(false);
            productQuantityField.setDisable(false);
            productPriceField.setDisable(false);
            productWarehouseComboBox.setDisable(false);
            productMaterialField.setDisable(false);
            productWeightField.setDisable(false);
            productBrandField.clear();
            productCompatibleCarsField.clear();
            productCountryManufacturerField.clear();
            productColorField.clear();
            productWeightField.clear();
            productWheelSizeField.clear();
            productKitTypeComboBox.getSelectionModel().clearSelection();
            productWarehouseComboBox.getSelectionModel().clearSelection();
            productSoldCheck.setSelected(false);
        } else if (productBodyKitRadio.isSelected()) {

            productTitleField.setDisable(false);
            productDescriptionField.setDisable(false);
            productQuantityField.setDisable(false);
            productPriceField.setDisable(false);
            productWarehouseComboBox.setDisable(false);
            productBrandField.setDisable(false);
            productCompatibleCarsField.setDisable(false);
            productCountryManufacturerField.setDisable(false);
            productKitTypeComboBox.setDisable(false);
            productMaterialField.clear();
            productWeightField.clear();
            productWeightField.clear();
            productColorField.clear();
            productWheelSizeField.clear();
            productWarehouseComboBox.getSelectionModel().clearSelection();
            productSoldCheck.setSelected(false);
        } else if (productWheelsRadio.isSelected()) {

            productTitleField.setDisable(false);
            productDescriptionField.setDisable(false);
            productQuantityField.setDisable(false);
            productPriceField.setDisable(false);
            productWarehouseComboBox.setDisable(false);
            productWeightField.setDisable(false);
            productColorField.setDisable(false);
            productWheelSizeField.setDisable(false);
            productBrandField.clear();
            productCompatibleCarsField.clear();
            productCountryManufacturerField.clear();
            productKitTypeComboBox.getSelectionModel().clearSelection();
            productWarehouseComboBox.getSelectionModel().clearSelection();
            productMaterialField.clear();
            productWeightField.clear();
            productSoldCheck.setSelected(false);
        }
    }

    @FXML private void createRecord() {
        if (!allFieldsFilled()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System message");
            alert.setHeaderText("Error with creation or editing product");
            alert.setContentText("Not all fields filled");
            alert.showAndWait();
            return;
        }

        if (productSpoilerRadio.isSelected()) {
            Spoiler spoiler = new Spoiler(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productPriceField.getText()),
                    productMaterialField.getText(),
                    Float.parseFloat(productWeightField.getText()),
                    (Warehouse) productWarehouseComboBox.getSelectionModel().getSelectedItem()
            );
            genericHibernate.create(spoiler);
            productAdminList.getItems().add(spoiler);
        } else if (productBodyKitRadio.isSelected()) {
            BodyKit bodyKit = new BodyKit(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productPriceField.getText()),
                    productBrandField.getText(),
                    productCompatibleCarsField.getText(),
                    productCountryManufacturerField.getText(),
                    productKitTypeComboBox.getValue(),
                    (Warehouse) productWarehouseComboBox.getSelectionModel().getSelectedItem()
            );
            genericHibernate.create(bodyKit);
            productAdminList.getItems().add(bodyKit);
        } else if (productWheelsRadio.isSelected()) {
            Wheels wheels = new Wheels(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productPriceField.getText()),
                    Integer.parseInt(productWheelSizeField.getText()),
                    productColorField.getText(),
                    Float.parseFloat(productWeightField.getText()),
                    (Warehouse) productWarehouseComboBox.getSelectionModel().getSelectedItem()
            );
            genericHibernate.create(wheels);
            productAdminList.getItems().add(wheels);
        }
        productAdminList.getItems().sort(Comparator.comparing(Product::getTitle));
    }

    @FXML private void updateRecord() {
        if (!allFieldsFilled()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System message");
            alert.setHeaderText("Error with creation or editing product");
            alert.setContentText("Not all fields filled");
            alert.showAndWait();
            return;
        }

        Product product = productAdminList.getSelectionModel().getSelectedItem();

        if (product instanceof Spoiler) {
            Spoiler spoiler = (Spoiler) product;
            spoiler.setTitle(productTitleField.getText());
            spoiler.setDescription(productDescriptionField.getText());
            spoiler.setPrice(Float.parseFloat(productPriceField.getText()));
            spoiler.setQuantity(Integer.parseInt(productQuantityField.getText()));
            spoiler.setMaterial(productMaterialField.getText());
            spoiler.setWeight(Float.parseFloat(productWeightField.getText()));
            spoiler.setWarehouse((Warehouse) productWarehouseComboBox.getSelectionModel().getSelectedItem());
            genericHibernate.update(spoiler);
        } else if (product instanceof BodyKit) {
            BodyKit bodyKit = (BodyKit) product;
            bodyKit.setTitle(productTitleField.getText());
            bodyKit.setDescription(productDescriptionField.getText());
            bodyKit.setPrice(Float.parseFloat(productPriceField.getText()));
            bodyKit.setQuantity(Integer.parseInt(productQuantityField.getText()));
            bodyKit.setCompatibleCars(productCompatibleCarsField.getText());
            bodyKit.setCountryManufacturer(productCountryManufacturerField.getText());
            bodyKit.setBrand(productBrandField.getText());
            bodyKit.setKitType(productKitTypeComboBox.getValue());
            bodyKit.setWarehouse((Warehouse) productWarehouseComboBox.getSelectionModel().getSelectedItem());
            genericHibernate.update(bodyKit);
        } else if (product instanceof Wheels) {
            Wheels wheels = (Wheels) product;
            wheels.setTitle(productTitleField.getText());
            wheels.setDescription(productDescriptionField.getText());
            wheels.setPrice(Float.parseFloat(productPriceField.getText()));
            wheels.setQuantity(Integer.parseInt(productQuantityField.getText()));
            wheels.setWheelSize(Integer.parseInt(productWheelSizeField.getText()));
            wheels.setColor(productColorField.getText());
            wheels.setWeight(Float.parseFloat(productWeightField.getText()));
            wheels.setWarehouse((Warehouse) productWarehouseComboBox.getSelectionModel().getSelectedItem());
            genericHibernate.update(wheels);
        }
        productAdminList.getItems().setAll(genericHibernate.getAllRecords(Product.class));
    }

    @FXML private void deleteRecord() {

        Product product = productAdminList.getSelectionModel().getSelectedItem();
        productAdminList.getItems().remove(product);
        genericHibernate.delete(product);
        clearAllFields();

    }

    @FXML private void loadProductData() {
        Product product = productAdminList.getSelectionModel().getSelectedItem();
        if(product != null) {
            product = genericHibernate.getEntityById(product.getClass(), product.getId());
        }


        if (product instanceof Spoiler) {
            productSpoilerRadio.setSelected(true);
            neededFieldsForProducts();
            Spoiler spoiler = (Spoiler) product;
            productTitleField.setText(spoiler.getTitle());
            productDescriptionField.setText(spoiler.getDescription());
            productPriceField.setText(String.valueOf(spoiler.getPrice()));
            productQuantityField.setText(String.valueOf(spoiler.getQuantity()));
            productMaterialField.setText(spoiler.getMaterial());
            productWeightField.setText(String.valueOf(spoiler.getWeight()));
            productWarehouseComboBox.setValue(spoiler.getWarehouse());

        } else if (product instanceof BodyKit) {
            productBodyKitRadio.setSelected(true);
            neededFieldsForProducts();
            BodyKit bodyKit = (BodyKit) product;
            productTitleField.setText(bodyKit.getTitle());
            productDescriptionField.setText(bodyKit.getDescription());
            productPriceField.setText(String.valueOf(bodyKit.getPrice()));
            productQuantityField.setText(String.valueOf(bodyKit.getQuantity()));
            productBrandField.setText(bodyKit.getBrand());
            productCompatibleCarsField.setText(bodyKit.getCompatibleCars());
            productCountryManufacturerField.setText(bodyKit.getCountryManufacturer());
            productKitTypeComboBox.setValue(bodyKit.getKitType());
            productWarehouseComboBox.setValue(bodyKit.getWarehouse());

        } else if (product instanceof Wheels) {
            productWheelsRadio.setSelected(true);
            neededFieldsForProducts();
            Wheels wheels = (Wheels) product;
            productTitleField.setText(wheels.getTitle());
            productDescriptionField.setText(wheels.getDescription());
            productPriceField.setText(String.valueOf(wheels.getPrice()));
            productQuantityField.setText(String.valueOf(wheels.getQuantity()));
            productWheelSizeField.setText(String.valueOf(wheels.getWheelSize()));
            productColorField.setText(wheels.getColor());
            productWeightField.setText(String.valueOf(wheels.getWeight()));
            productWarehouseComboBox.setValue(wheels.getWarehouse());
        }
        if(product.getCart() != null){
            productSoldCheck.setSelected(true);
        }
    }

    @FXML private void clearAllFields() {
        productSpoilerRadio.setSelected(false);
        productWheelsRadio.setSelected(false);
        productBodyKitRadio.setSelected(false);
        turnOffAllFields();
        productTitleField.clear();
        productDescriptionField.clear();
        productQuantityField.clear();
        productPriceField.clear();
        productBrandField.clear();
        productCompatibleCarsField.clear();
        productCountryManufacturerField.clear();
        productColorField.clear();
        productWeightField.clear();
        productWheelSizeField.clear();
        productMaterialField.clear();
        productWarehouseComboBox.setValue(null);
        productWarehouseComboBox.setPromptText("Warehouse");
        productKitTypeComboBox.setValue(null);
        productKitTypeComboBox.setPromptText("Kit type");
        productSoldCheck.setSelected(false);
    }

    private void turnOffAllFields() {
        productTitleField.setDisable(true);
        productDescriptionField.setDisable(true);
        productQuantityField.setDisable(true);
        productPriceField.setDisable(true);
        productBrandField.setDisable(true);
        productCompatibleCarsField.setDisable(true);
        productCountryManufacturerField.setDisable(true);
        productMaterialField.setDisable(true);
        productWeightField.setDisable(true);
        productColorField.setDisable(true);
        productKitTypeComboBox.setDisable(true);
        productWheelSizeField.setDisable(true);
        productWarehouseComboBox.setDisable(true);
        productSoldCheck.setDisable(true);
    }

    private boolean allFieldsFilled() {
        if (productSpoilerRadio.isSelected()) {
            return !productTitleField.getText().isEmpty() &&
                    !productDescriptionField.getText().isEmpty() &&
                    !productQuantityField.getText().isEmpty() &&
                    !productPriceField.getText().isEmpty() &&
                    !productMaterialField.getText().isEmpty() &&
                    !productWeightField.getText().isEmpty() &&
                    productWarehouseComboBox.getValue() != null;
        } else if (productBodyKitRadio.isSelected()) {
            return !productTitleField.getText().isEmpty() &&
                    !productDescriptionField.getText().isEmpty() &&
                    !productQuantityField.getText().isEmpty() &&
                    !productPriceField.getText().isEmpty() &&
                    !productBrandField.getText().isEmpty() &&
                    !productCompatibleCarsField.getText().isEmpty() &&
                    !productCountryManufacturerField.getText().isEmpty() &&
                    productKitTypeComboBox.getValue() != null &&
                    productWarehouseComboBox.getValue() != null;
        } else if (productWheelsRadio.isSelected()) {
            return !productTitleField.getText().isEmpty() &&
                    !productDescriptionField.getText().isEmpty() &&
                    !productQuantityField.getText().isEmpty() &&
                    !productPriceField.getText().isEmpty() &&
                    !productWheelSizeField.getText().isEmpty() &&
                    !productColorField.getText().isEmpty() &&
                    !productWeightField.getText().isEmpty() &&
                    productWarehouseComboBox.getValue() != null;
        } else {

            return false;
        }
    }

    @FXML private void loadWarehousesForComboBox() {
        ObservableList<Warehouse> warehouses = FXCollections.observableArrayList(genericHibernate.getAllRecords(Warehouse.class));
        productWarehouseComboBox.setConverter(new StringConverter<Warehouse>() {
            @Override
            public String toString(Warehouse warehouse) {
                if (warehouse != null) {
                    return "id: " + warehouse.getId() + " | " + warehouse.getAddress();
                } else {
                    return null;
                }
            }

            @Override
            public Warehouse fromString(String s) {
                return null;
            }
        });

        productWarehouseComboBox.setItems(warehouses);
    }
}