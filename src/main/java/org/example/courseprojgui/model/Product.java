package org.example.courseprojgui.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="products")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="product_type",
        discriminatorType = DiscriminatorType.STRING)
public class Product implements Serializable {

    @Id
    @Column(name = "id")
    protected int id;
    @Column(name = "title")
    protected String title;
    @Column(name = "description")
    protected String description;
    @Column(name = "quantity")
    protected int quantity;
    @Column(name = "price")
    protected float price;

    public Product(String title, String description, int quantity, float price) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
}
