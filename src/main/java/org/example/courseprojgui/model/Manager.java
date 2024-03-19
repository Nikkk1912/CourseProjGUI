package org.example.courseprojgui.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Manager extends User {
    private boolean isAdmin;
    @ManyToOne
    private Warehouse warehouse;

    //private ArrayList<Cart> myResponsibleCarts = new ArrayList<Cart>();

    public Manager(String login, String password, String name, String surname, boolean isAdmin) {
        super(name, surname, login, password);
        this.isAdmin = isAdmin;
    }



}
