package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter

public class Product implements Serializable {
    protected int id;
    protected String title;
    protected String description;
    protected int quantity;
    protected float price;

    public Product() {
    }
    public Product(String title, String description, int quantity, float price) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
}
