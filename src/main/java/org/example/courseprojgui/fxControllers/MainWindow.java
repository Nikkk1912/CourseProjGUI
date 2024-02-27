package org.example.courseprojgui.fxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.courseprojgui.enums.KitType;
import org.example.courseprojgui.model.BodyKit;
import org.example.courseprojgui.model.Product;
import org.example.courseprojgui.model.Spoiler;
import org.example.courseprojgui.model.Wheels;

public class MainWindow {
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

    public void turnOffAllFields() {
        productBrandField.setDisable(true);
        productCompatibleCarsField.setDisable(true);
        productCountryManufacturerField.setDisable(true);
        productMaterialField.setDisable(true);
        productWeightField.setDisable(true);
        productColorField.setDisable(true);
        productKitTypeComboBox.setDisable(true);
        productWheelSizeField.setDisable(true);
    }


    @FXML
    public void initialize() {
        ObservableList<KitType> kitTypes = FXCollections.observableArrayList(KitType.values());
        productKitTypeComboBox.setItems(kitTypes);
        turnOffAllFields();

    }

    public void disableFields() {
        if (productSpoilerRadio.isSelected()) {
            neededFieldSpoiler();
        } else if (productBodyKitRadio.isSelected()) {
            neededFieldBodyKit();
        } else if (productWheelsRadio.isSelected()) {
            neededFieldWheels();
        } else {
            turnOffAllFields();
        }
    }
    public void neededFieldSpoiler() {
        turnOffAllFields();
        productMaterialField.setDisable(false);
        productWeightField.setDisable(false);
        productBrandField.clear();
        productCompatibleCarsField.clear();
        productCountryManufacturerField.clear();
        productColorField.clear();
        productWeightField.clear();
        productWheelSizeField.clear();
        productKitTypeComboBox.getSelectionModel().clearSelection();
    }
    public void neededFieldBodyKit() {
        turnOffAllFields();
        productBrandField.setDisable(false);
        productCompatibleCarsField.setDisable(false);
        productCountryManufacturerField.setDisable(false);
        productKitTypeComboBox.setDisable(false);
        productMaterialField.clear();
        productWeightField.clear();
        productWeightField.clear();
        productColorField.clear();
        productWheelSizeField.clear();
    }
    public void neededFieldWheels() {
        turnOffAllFields();
        productWeightField.setDisable(false);
        productColorField.setDisable(false);
        productWheelSizeField.setDisable(false);
        productBrandField.clear();
        productCompatibleCarsField.clear();
        productCountryManufacturerField.clear();
        productKitTypeComboBox.getSelectionModel().clearSelection();
        productMaterialField.clear();
        productWeightField.clear();
    }


    public void createRecord() {
        if (productSpoilerRadio.isSelected()) {
            Spoiler spoiler = new Spoiler(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productPriceField.getText()),
                    productMaterialField.getText(),
                    Float.parseFloat(productWeightField.getText())
            );
            productAdminList.getItems().add(spoiler);
        } else if(productBodyKitRadio.isSelected()) {
            BodyKit bodyKit = new BodyKit(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productPriceField.getText()),
                    productBrandField.getText(),
                    productCompatibleCarsField.getText(),
                    productCountryManufacturerField.getText(),
                    productKitTypeComboBox.getValue()
            );
            productAdminList.getItems().add(bodyKit);
        } else if(productWheelsRadio.isSelected()) {
            Wheels wheels = new Wheels(
                    productTitleField.getText(),
                    productDescriptionField.getText(),
                    Integer.parseInt(productQuantityField.getText()),
                    Float.parseFloat(productPriceField.getText()),
                    Integer.parseInt(productWheelSizeField.getText()),
                    productColorField.getText(),
                    Float.parseFloat(productWeightField.getText())
            );
            productAdminList.getItems().add(wheels);
        }
    }
    public void updateRecord() {
        Product product = productAdminList.getSelectionModel().getSelectedItem();
        if (product instanceof Spoiler) {
            Spoiler spoiler = (Spoiler) product;
            product.setTitle(productTitleField.getText());
            //productDescriptionField.setText(plant.getDescription());
            spoiler.setDescription(productDescriptionField.getText());
        }
    }
    public void deleteRecord() {
        Product product = productAdminList.getSelectionModel().getSelectedItem();
        productAdminList.getItems().remove(product);
    }
    public void loadProductData() {
        Product product = productAdminList.getSelectionModel().getSelectedItem();

        if (product instanceof Spoiler) {
            productSpoilerRadio.setSelected(true);
            neededFieldSpoiler();
            Spoiler spoiler = (Spoiler) product;
            productTitleField.setText(spoiler.getTitle());
            productDescriptionField.setText(spoiler.getDescription());
            productPriceField.setText(String.valueOf(spoiler.getPrice()));
            productQuantityField.setText(String.valueOf(spoiler.getQuantity()));
            productMaterialField.setText(spoiler.getMaterial());
            productWeightField.setText(String.valueOf(spoiler.getWeight()));

        } else if (product instanceof BodyKit) {
            productBodyKitRadio.setSelected(true);
            neededFieldBodyKit();
            BodyKit bodyKit = (BodyKit) product;
            productTitleField.setText(bodyKit.getTitle());
            productDescriptionField.setText(bodyKit.getDescription());
            productPriceField.setText(String.valueOf(bodyKit.getPrice()));
            productQuantityField.setText(String.valueOf(bodyKit.getQuantity()));
            productBrandField.setText(bodyKit.getBrand());
            productCompatibleCarsField.setText(bodyKit.getCompatibleCars());
            productCountryManufacturerField.setText(bodyKit.getCountryManufacturer());
            productKitTypeComboBox.setValue(bodyKit.getKitType());

        } else if (product instanceof Wheels) {
            productWheelsRadio.setSelected(true);
            neededFieldWheels();
            Wheels wheels = (Wheels) product;
            productTitleField.setText(wheels.getTitle());
            productDescriptionField.setText(wheels.getDescription());
            productPriceField.setText(String.valueOf(wheels.getPrice()));
            productQuantityField.setText(String.valueOf(wheels.getQuantity()));
            productWheelSizeField.setText(String.valueOf(wheels.getWheelSize()));
            productColorField.setText(wheels.getColor());
            productWeightField.setText(String.valueOf(wheels.getWeight()));
        }
    }


    public void clearAllFields() {
        productSpoilerRadio.setSelected(false);
        productWheelsRadio.setSelected(false);
        productBodyKitRadio.setSelected(false);
        disableFields();
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
        productKitTypeComboBox.getSelectionModel().clearSelection();
    }
}