package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class Shop implements Serializable {
    public List<User> sysUsers;
    public List<Product> sysProducts;

    public Shop(List<User> sysUsers, List<Product> sysProducts) {
        this.sysUsers = sysUsers;
        this.sysProducts = sysProducts;
    }

    public Shop() {
        this.sysUsers = new ArrayList<>();
        this.sysProducts = new ArrayList<>();
    }
}
