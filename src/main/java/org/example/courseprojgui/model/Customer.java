package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public Customer(String name, String surname, String login, String password, String cardNumber, String shippingAddress, String billingAddress, String birthDate) {
        super(name, surname, login, password);
        this.cardNumber = cardNumber;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        this.birthDate = LocalDate.parse(birthDate, formatter);
    }

    public Customer() {

    }

    public void setBirthDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        this.birthDate = LocalDate.parse(birthDate, formatter);
    }
}
