package org.example.courseprojgui.fxControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.courseprojgui.enums.KitType;
import org.example.courseprojgui.model.Product;

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


    @FXML
    public void initialize() {
        // Populate ComboBox with KitType enum values
        ObservableList<KitType> kitTypes = FXCollections.observableArrayList(KitType.values());
        productKitTypeComboBox.setItems(kitTypes);

        productBrandField.setDisable(true);
        productCompatibleCarsField.setDisable(true);
        productCountryManufacturerField.setDisable(true);
        productMaterialField.setDisable(true);
        productWeightField.setDisable(true);
        productColorField.setDisable(true);
        productKitTypeComboBox.setDisable(true);
        productWheelSizeField.setDisable(true);
    }

    public void disableFields() {
        if (productSpoilerRadio.isSelected()) {
            productBrandField.setDisable(true);
            productCompatibleCarsField.setDisable(true);
            productCountryManufacturerField.setDisable(true);
            productMaterialField.setDisable(false);
            productWeightField.setDisable(false);
            productColorField.setDisable(true);
            productKitTypeComboBox.setDisable(true);
            productWheelSizeField.setDisable(true);
        } else if (productBodyKitRadio.isSelected()) {
            productBrandField.setDisable(false);
            productCompatibleCarsField.setDisable(false);
            productCountryManufacturerField.setDisable(false);
            productMaterialField.setDisable(true);
            productWeightField.setDisable(true);
            productColorField.setDisable(true);
            productKitTypeComboBox.setDisable(false);
            productWheelSizeField.setDisable(true);
        } else if (productWheelsRadio.isSelected()) {
            productBrandField.setDisable(true);
            productCompatibleCarsField.setDisable(true);
            productCountryManufacturerField.setDisable(true);
            productMaterialField.setDisable(true);
            productWeightField.setDisable(false);
            productColorField.setDisable(false);
            productKitTypeComboBox.setDisable(true);
            productWheelSizeField.setDisable(false);
        } else {
            productBrandField.setDisable(true);
            productCompatibleCarsField.setDisable(true);
            productCountryManufacturerField.setDisable(true);
            productMaterialField.setDisable(true);
            productWeightField.setDisable(true);
            productColorField.setDisable(true);
            productKitTypeComboBox.setDisable(true);
            productWheelSizeField.setDisable(true);
        }

    }

    public void createRecord() {
    }

    public void updateRecord() {
    }

    public void deleteRecord() {
    }
}