package org.example.courseprojgui.fxControllers;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Getter
@Setter

public class MainController implements Initializable {

    @Getter
    @Setter
    private static MainController instance;
    public TabPane tabsPane;
    private EntityManagerFactory entityManagerFactory;

    @FXML private Tab ordersTab;
    @FXML private Tab productsTab;
    @FXML private Tab shopTab;
    @FXML private Tab usersTab;
    @FXML private Tab wareHousesTab;



    public MainController() {
        instance = this;
        entityManagerFactory = Persistence.createEntityManagerFactory("Shop");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        closeAllTabs();
        tabsPane.getSelectionModel().select(usersTab);
        try {
            AnchorPane anchor2 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/courseprojgui/usersTab.fxml")));
            usersTab.setContent(anchor2);
        } catch (IOException e) {
            System.out.println("File not found");
        }

    }

    public void openAllTabs(boolean isAdminNow) {

        shopTab.setDisable(false);
        ordersTab.setDisable(false);

        try {
            AnchorPane anchor3 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/courseprojgui/shopTab.fxml")));
            shopTab.setContent(anchor3);
        } catch (IOException e) {
            System.out.println("File not found");
        }

        try {
            AnchorPane anchor5 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/courseprojgui/ordersTab.fxml")));
            ordersTab.setContent(anchor5);
        } catch (IOException e) {
            System.out.println("File not found");
        }


        if(isAdminNow) {
            productsTab.setDisable(false);
            wareHousesTab.setDisable(false);

            try {
                AnchorPane anchor1 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/courseprojgui/productsTab.fxml")));
                productsTab.setContent(anchor1);
            } catch (IOException e) {
                System.out.println("File not found");
            }

            try {
                AnchorPane anchor4 = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/courseprojgui/wareHousesTab.fxml")));
                wareHousesTab.setContent(anchor4);
            } catch (IOException e) {
                System.out.println("File not found");
            }
        }


    }

    public void closeAllTabs() {
        shopTab.setContent(null);
        productsTab.setContent(null);
        wareHousesTab.setContent(null);
        shopTab.setDisable(true);
        productsTab.setDisable(true);
        wareHousesTab.setDisable(true);
        ordersTab.setDisable(true);
    }
}


