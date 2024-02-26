package org.example.courseprojgui.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public abstract class User implements Serializable {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;

    public User() {

    }

    public User(String name, String surname, String login, String password) {

        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }


}
