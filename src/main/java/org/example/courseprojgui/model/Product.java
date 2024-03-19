package org.example.courseprojgui.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
    @OneToMany(mappedBy = "whichProductCommented", cascade = CascadeType.ALL )
    @LazyCollection(LazyCollectionOption.FALSE)
    protected List<Comment> comments;
    @ManyToOne
    private Cart cart;
    @ManyToOne
    private Shop shop;

    public Product(String title, String description, int quantity, float price) {
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
    }
}
