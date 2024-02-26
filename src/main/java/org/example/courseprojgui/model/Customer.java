package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class Customer extends User {
    private String cardNumber;
    private String shippingAddress;
    private String billingAddress;
    private LocalDate birthDate;

    public Customer(String name, String surname, String login, String password) {
        super(name, surname, login, password);
    }

    public Customer(String name, String surname, String login, String password, String cardNumber, String shippingAddress, String billingAddress, LocalDate birthDate) {
        super(name, surname, login, password);
        this.cardNumber = cardNumber;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.birthDate = birthDate;
    }

    public Customer() {

    }

    public void printInfo() {
        System.out.println("Customer Info:");
        System.out.println("id: " + getId());
        System.out.println("name: " + getName());
        System.out.println("surname: " + getSurname());
        System.out.println("login: " + getLogin());
        System.out.println("password: " + getPassword());
        System.out.println("cardNumber: " + cardNumber);
        System.out.println("shippingAddress: " + shippingAddress);
        System.out.println("billingAddress: " + billingAddress);
        System.out.println("birthDate: " + birthDate + "\n");
    }
}
