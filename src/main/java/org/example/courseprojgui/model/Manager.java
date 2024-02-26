package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Manager extends User {
    private boolean isAdmin;
    @Setter
    @Getter
    private ArrayList<Cart> myResponsibleCarts = new ArrayList<Cart>();

    public Manager(String login, String password, String name, String surname, boolean isAdmin) {
        super(name, surname, login, password);
        this.isAdmin = isAdmin;
        //this.myResponsibleCarts = myResponsibleCarts;
    }

    public Manager() {

    }

    public void printInfo() {
        System.out.println("Manager Info:");
        System.out.println("id: " + getId());
        System.out.println("name: " + getName());
        System.out.println("surname: " + getSurname());
        System.out.println("login: " + getLogin());
        System.out.println("password: " + getPassword());
        System.out.println("isAdmin: " + isAdmin+ "\n");
    }


}
