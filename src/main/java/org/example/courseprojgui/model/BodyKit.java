package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;
import org.example.courseprojgui.enums.KitType;
@Setter
@Getter

public class BodyKit extends Product {
    private String brand;
    private String compatibleCars;
    private String countryManufacturer;
    private KitType kitType;

    public BodyKit(String title, String description, int quantity, float price, String brand, String compatibleCars, String countryManufacturer, KitType kitType) {
        super(title, description, quantity, price);
        this.brand = brand;
        this.compatibleCars = compatibleCars;
        this.countryManufacturer = countryManufacturer;
        this.kitType = kitType;
    }
}