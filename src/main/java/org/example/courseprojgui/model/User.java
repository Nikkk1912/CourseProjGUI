package org.example.courseprojgui.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    @ManyToOne
    private Shop shop;


    public User(String name, String surname, String login, String password) {

        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }


}
