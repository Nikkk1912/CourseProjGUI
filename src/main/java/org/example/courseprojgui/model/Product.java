package org.example.courseprojgui.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    protected String title;
    protected String description;
    protected int quantity;
    protected float price;
    @ManyToOne()
    private Warehouse warehouse;

    protected List<Comment> comments;
    private Cart cart;

    public Product(String title, String description, int quantity, float price) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
}
