package org.example.courseprojgui.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Spoiler extends Product{
    private String material;
    private float weight;

    public Spoiler(String title, String description, int quantity, float price, String material, float weight) {
        super(title, description, quantity, price);
        this.material = material;
        this.weight = weight;
    }
}
