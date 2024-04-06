package org.example.courseprojgui.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Customer extends User {
    private String cardNumber;
    private String shippingAddress;
    private String billingAddress;
    private LocalDate birthDate;
    @OneToMany(mappedBy = "commentOwner", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> myComments;

    public Customer(String name, String surname, String login, String password) {
        super(name, surname, login, password);
    }

    public Customer(String name, String surname, String login, String password, String cardNumber, String shippingAddress, String billingAddress, String birthDate) {
        super(name, surname, login, password);
        this.cardNumber = cardNumber;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        if(birthDate.equals("")) {
            this.birthDate = LocalDate.of(2000, 1, 1);
        } else {
            DateTimeFormatter[] formatters = {
                    DateTimeFormatter.ofPattern("dd MM yyyy"),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                    DateTimeFormatter.ofPattern("ddMMyyyy")
            };

            LocalDate parsedDate = null;
            for (DateTimeFormatter formatter : formatters) {
                try {
                    parsedDate = LocalDate.parse(birthDate, formatter);
                    break;
                } catch (DateTimeParseException e) {
                }
            }

            if (parsedDate == null) {
                this.birthDate = LocalDate.of(2000, 1, 1);
            } else {
                this.birthDate = parsedDate;
            }
        }

    }

    public void setBirthDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        this.birthDate = LocalDate.parse(birthDate, formatter);
    }
}
