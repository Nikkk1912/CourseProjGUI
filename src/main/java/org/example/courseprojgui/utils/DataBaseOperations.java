package org.example.courseprojgui.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseOperations {
    public static Connection connectToDb() throws ClassNotFoundException {
        Connection conn = null;
        Class.forName("com.mysql.jdbc.cj.Driver");
        String DB_URL = "jdbc:mysql://localhost/librarytheory";
        String USER = "root";
        String PASS = "";
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }
    }

