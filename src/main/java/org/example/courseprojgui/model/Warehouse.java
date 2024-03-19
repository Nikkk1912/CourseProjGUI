package org.example.courseprojgui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Warehouse {
    private List<Product> stock;
    private List<Manager> managers;
    private String address;

    public Warehouse(String address) {
        this.address = address;
        this.stock = new ArrayList<>();
        this.managers = new ArrayList<>();
    }
}
