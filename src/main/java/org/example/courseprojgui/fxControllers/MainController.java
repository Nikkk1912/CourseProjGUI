package org.example.courseprojgui.fxControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private static MainController instance;

    @FXML Tab stopTab;
    @FXML Tab usersTab;
    @FXML Tab productsTab;
    @FXML Tab wareHousesTab;

    public MainController() {
        instance = this;
    }

    public static MainController getInstance() {
        return instance;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        closeAllTabs();
        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane anch2 = loader.load(getClass().getResource("/org/example/courseprojgui/usersTab.fxml"));
            usersTab.setContent(anch2);
        } catch (IOException e) {
            System.out.println("File not found");
        }

    }

    public void openAllTabs() {
        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane anch1 = loader.load(getClass().getResource("/org/example/courseprojgui/productsTab.fxml"));
            productsTab.setContent(anch1);
        } catch (IOException e) {
            System.out.println("File not found");
        }
        loader = new FXMLLoader();
        try {
            AnchorPane anch3 = loader.load(getClass().getResource("/org/example/courseprojgui/shopTab.fxml"));
            stopTab.setContent(anch3);
        } catch (IOException e) {
            System.out.println("File not found");
        }
        loader = new FXMLLoader();
        try {
            AnchorPane anch4 = loader.load(getClass().getResource("/org/example/courseprojgui/wareHousesTab.fxml"));
            wareHousesTab.setContent(anch4);
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public void closeAllTabs() {
        stopTab.setContent(null);
        productsTab.setContent(null);
        wareHousesTab.setContent(null);
    }
}


