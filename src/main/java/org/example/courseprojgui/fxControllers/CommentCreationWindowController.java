package org.example.courseprojgui.fxControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.courseprojgui.hibernate.GenericHibernate;
import org.example.courseprojgui.model.Cart;
import org.example.courseprojgui.model.Comment;
import org.example.courseprojgui.model.Product;
import org.example.courseprojgui.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CommentCreationWindowController implements Initializable {
    public Slider ratingSlider;
    public TextArea reviewField;
    public TextField commentTitleField;
    public Button submitButton;
    private MainController mainController;
    private UsersTabController usersTabController;
    private GenericHibernate genericHibernate;
    private Comment comment;
    private Product product;
    private User currentUser;
    private Cart currentChat;
    private boolean isUpdate = false;
    private CommentsWindowController commentsWindowController;
    private OrdersTabController ordersTabController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commentsWindowController = CommentsWindowController.getInstance();
        ordersTabController = OrdersTabController.getInstance();
    }

    public void setData(User user, Product product) {
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        this.currentUser = user;
        this.product = product;
        this.genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
    }

    public void setData(Comment comment, User user, Product product) {
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        this.comment = comment;
        this.product = product;
        this.currentUser = user;
        this.genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
    }
    public void setData(Comment comment, User user, Cart cart) {
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        this.comment = comment;
        this.currentChat = cart;
        this.currentUser = user;
        this.genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
    }


    public void setData(Comment comment) {
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        this.genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
        this.comment = comment;
        this.isUpdate = true;

        Comment commentToUpdate = genericHibernate.getEntityById(Comment.class, comment.getId());
        ratingSlider.setVisible(false);
        commentTitleField.setText(commentToUpdate.getCommentTitle());
        reviewField.setText(commentToUpdate.getCommentBody());
    }

    public void setData(User user, Cart chat){
        mainController = MainController.getInstance();
        usersTabController = UsersTabController.getInstance();
        this.genericHibernate = new GenericHibernate(mainController.getEntityManagerFactory());
        this.currentChat = chat;
        this.currentUser = user;

    }

    public void saveComment() throws IOException {
        if(product != null && comment == null && !isUpdate) {
            Comment commentToSave = new Comment(commentTitleField.getText(), reviewField.getText(), product, currentUser, (float) ratingSlider.getValue());
            Product productToUpdate = genericHibernate.getEntityById(Product.class, product.getId());
            productToUpdate.getComments().add(commentToSave);
            genericHibernate.update(productToUpdate);
        }
        else if (currentChat != null && currentUser != null) {
            Comment commentToSave = new Comment(commentTitleField.getText(), reviewField.getText(), currentUser, currentChat, (float) ratingSlider.getValue());
            Cart cartToUpdate = genericHibernate.getEntityById(Cart.class, currentChat.getId());
            cartToUpdate.getChat().add(commentToSave);
            genericHibernate.update(cartToUpdate);
        }
        else if (comment != null && !isUpdate && currentChat !=null) {
            Comment parentComment = genericHibernate.getEntityById(Comment.class, comment.getId());
            Comment replyToCreate = new Comment(commentTitleField.getText(), reviewField.getText(), currentUser, parentComment, (float) ratingSlider.getValue());
            parentComment.getReplies().add(replyToCreate);
            genericHibernate.update(parentComment);
        }
        else if (comment != null && !isUpdate) {
            Comment parentComment = genericHibernate.getEntityById(Comment.class, comment.getId());
            Comment replyToCreate = new Comment(commentTitleField.getText(), reviewField.getText(), currentUser, parentComment, (float) ratingSlider.getValue());
            parentComment.getReplies().add(replyToCreate);
            genericHibernate.update(parentComment);
        }
        else if (comment != null) {
            Comment commentToUpdate = genericHibernate.getEntityById(Comment.class, comment.getId());
            commentToUpdate.setCommentTitle(commentTitleField.getText());
            commentToUpdate.setCommentBody(reviewField.getText());
            genericHibernate.update(commentToUpdate);
        }

        if(commentsWindowController != null) {
            commentsWindowController.loadComments();
        } else if (ordersTabController != null) {
            ordersTabController.loadComments();
        }
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();
    }
}
