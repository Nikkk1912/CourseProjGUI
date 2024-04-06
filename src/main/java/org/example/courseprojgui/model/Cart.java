package org.example.courseprojgui.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<Product> itemsToBuy;
    @ManyToOne
    private User customer;
    @ManyToOne
    private Manager manager;
//    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
//    @LazyCollection(LazyCollectionOption.FALSE)
//    private List<Comment> chat;

    public Cart(User customer, Manager manager) {
        this.customer = customer;
        this.manager = manager;
        this.itemsToBuy = new ArrayList<>();
    }

    public void addItemToCart(Product product) {
        itemsToBuy.add(product);
    }
}
