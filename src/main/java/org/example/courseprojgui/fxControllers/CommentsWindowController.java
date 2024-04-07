package org.example.courseprojgui.fxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.courseprojgui.HelloApplication;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.hibernate.HibernateShop;
import org.example.courseprojgui.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommentsWindowController implements Initializable {

    private MainController mainController;
    private GenericHibernate genericHibernate;
    private HibernateShop hibernateShop;
    private UsersTabController usersTabController;
    private User currentUser;
    public ListView<Product> productListView;
    public TreeView<Comment> commentsTreeList;
    @Getter
    private static CommentsWindowController instance;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
        hibernateShop = new HibernateShop(mainController.getEntityManagerFactory());

        productListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);
                if (empty || product == null) {
                    setText(null);
                } else {
                    setFont(Font.font(16));
                    setText("Id: " + product.getId() + " | " + product.getTitle() + " : " + product.getClass().getSimpleName());
                }
            }
        });

        commentsTreeList.setCellFactory(param -> new TreeCell<>() {
            @Override
            protected void updateItem(Comment comment, boolean empty) {
                super.updateItem(comment, empty);
                if (empty || comment == null) {
                    setText(null);
                } else {
                    setText("Id: " + comment.getId() + " | " + comment.getCommentTitle() + " : " + comment.getClass().getSimpleName());
                }
            }
        });

        productListView.getItems().clear();
        productListView.getItems().addAll(genericHibernate.getAllRecords(Product.class));
    }

    public CommentsWindowController() {
        instance = this;
    }

    public void previewProduct() {
        Product product = genericHibernate.getEntityById(Product.class, productListView.getSelectionModel().getSelectedItem().getId());
        if (product instanceof Spoiler spoiler) {
            generateAlert(Alert.AlertType.INFORMATION, spoiler.getTitle(), spoiler.genText());
        } else if (product instanceof BodyKit bodyKit) {
            generateAlert(Alert.AlertType.INFORMATION, bodyKit.getTitle(), bodyKit.genText());
        } else if (product instanceof Wheels wheels) {
            generateAlert(Alert.AlertType.INFORMATION, wheels.getTitle(), wheels.genText());
        }
    }

    public void addReview() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("commentCreationWindow.fxml"));
        Parent parent = fxmlLoader.load();
        CommentCreationWindowController commentForm = fxmlLoader.getController();
        commentForm.setData(usersTabController.getCurrentUser(), productListView.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Create comment");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void loadComments() {
        Product product = genericHibernate.getEntityById(Product.class, productListView.getSelectionModel().getSelectedItem().getId());
        commentsTreeList.setRoot(new TreeItem<>());
        commentsTreeList.setShowRoot(false);
        commentsTreeList.getRoot().setExpanded(true);
        product.getComments().forEach(comment -> addTreeItem(comment, commentsTreeList.getRoot()));

    }

    private void addTreeItem(Comment comment, TreeItem<Comment> parentComment) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parentComment.getChildren().add(treeItem);
        comment.getReplies().forEach(sub -> addTreeItem(sub, treeItem));
    }

    private static void generateAlert(Alert.AlertType alertType, String header, String text){
        Alert alert = new Alert(alertType);
        alert.setTitle("System message");
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void updateComment() throws IOException {
        currentUser = usersTabController.getCurrentUser();
        if(currentUser == commentsTreeList.getSelectionModel().getSelectedItem().getValue().getCommentOwner() || currentUser instanceof Manager) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("commentCreationWindow.fxml"));
            Parent parent = fxmlLoader.load();
            CommentCreationWindowController commentForm = fxmlLoader.getController();
            commentForm.setData(commentsTreeList.getSelectionModel().getSelectedItem().getValue());
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update comment");
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

    public void reply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("commentCreationWindow.fxml"));
        Parent parent = fxmlLoader.load();
        CommentCreationWindowController commentForm = fxmlLoader.getController();
        commentForm.setData(commentsTreeList.getSelectionModel().getSelectedItem().getValue(), usersTabController.getCurrentUser(), productListView.getSelectionModel().getSelectedItem());
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Reply to a comment");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void delete() {
        currentUser = usersTabController.getCurrentUser();
        if (currentUser == commentsTreeList.getSelectionModel().getSelectedItem().getValue().getCommentOwner() || currentUser instanceof Manager) {
           hibernateShop.deleteComment(commentsTreeList.getSelectionModel().getSelectedItem().getValue().getId());
           loadComments();
        }
    }

    public void previewComment() {
        Comment comment = genericHibernate.getEntityById(Comment.class, commentsTreeList.getSelectionModel().getSelectedItem().getValue().getId());
        generateAlert(Alert.AlertType.INFORMATION,"Title: " + comment.getCommentTitle(), comment.genText());
    }
}
