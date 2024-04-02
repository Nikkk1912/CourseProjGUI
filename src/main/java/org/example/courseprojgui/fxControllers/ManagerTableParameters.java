package org.example.courseprojgui.fxControllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ManagerTableParameters {
    SimpleIntegerProperty id = new SimpleIntegerProperty();
    SimpleStringProperty status = new SimpleStringProperty();
    SimpleStringProperty login = new SimpleStringProperty();
    SimpleStringProperty name = new SimpleStringProperty();
    SimpleStringProperty surname = new SimpleStringProperty();
    SimpleStringProperty shippingAddress = new SimpleStringProperty();
    SimpleStringProperty billingAddress = new SimpleStringProperty();
    SimpleStringProperty birthDate = new SimpleStringProperty();


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getLogin() {
        return login.get();
    }

    public SimpleStringProperty loginProperty() {
        return login;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getShippingAddress() {
        return shippingAddress.get();
    }

    public SimpleStringProperty shippingAddressProperty() {
        return shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress.get();
    }

    public SimpleStringProperty billingAddressProperty() {
        return billingAddress;
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public SimpleStringProperty birthDateProperty() {
        return birthDate;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress.set(shippingAddress);
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress.set(billingAddress);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }
}
