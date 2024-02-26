package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Wheels extends Product{
    private int wheelSize;
    private String color;
    private float weight;

    public Wheels(String title, String description, int quantity, float price, int wheelSize, String color, float weight) {
        super(title, description, quantity, price);
        this.wheelSize = wheelSize;
        this.color = color;
        this.weight = weight;
    }
}
