package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Spoiler extends Product{
    private String material;
    private float weight;

    public Spoiler(String title, String description, int quantity, float price, String material, float weight) {
        super(title, description, quantity, price);
        this.material = material;
        this.weight = weight;
    }
}
