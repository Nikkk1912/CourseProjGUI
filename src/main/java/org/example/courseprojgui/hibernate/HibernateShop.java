package org.example.courseprojgui.hibernate;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.example.courseprojgui.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HibernateShop extends GenericHibernate {
    public HibernateShop(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }


    public User getUserByCredentials(String login, String password) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.and(cb.like(root.get("login"), login), cb.like(root.get("password"), password)));
            Query q;

            q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public void createCart(List<Product> products, User user) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();

            User buyer = entityManager.find(User.class, user.getId());

            List<Manager> managers = getAllRecords(Manager.class);
            Optional<Manager> minManager = managers.stream().min(Comparator.comparingInt(manager -> manager.getMyResponsibleCarts().size()));
            Manager managerWhoManageCart = minManager.orElse(null);
            System.out.println(managerWhoManageCart);

            Cart cart = new Cart(buyer, managerWhoManageCart, new ArrayList<>());
            for (Product p : products) {
                Product product = entityManager.find(Product.class, p.getId());

                product.setCart(cart);
                cart.getItemsToBuy().add(product);
            }
            buyer.getMyPurchases().add(cart);

            entityManager.merge(buyer);
            entityManager.merge(managerWhoManageCart);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }


    public void deleteCart(int id) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            Cart cart = entityManager.find(Cart.class, id);
            if (cart == null) {
                throw new IllegalArgumentException("Cart with id " + id + " not found");
            }

            User user = cart.getCustomer();
            if (user != null) {
                user.getMyPurchases().remove(cart);
                entityManager.merge(user);
            }

            Manager manager = cart.getManager();
            if (manager != null) {
                manager.getMyResponsibleCarts().remove(cart);
                entityManager.merge(manager);
            }

            for (Product product : cart.getItemsToBuy()) {
                product.setCart(null);
                entityManager.merge(product);
            }
            cart.getItemsToBuy().clear();

            entityManager.remove(cart);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Product> loadAvailableProducts() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> query = cb.createQuery(Product.class);
            Root<Product> root = query.from(Product.class);
            query.select(root).where(cb.isNull(root.get("cart")));
            Query q;

            q = em.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) em.close();
        }
    }

    public Cart getEntityByCartId(int cartId) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);

            query.select(root).where(cb.equal(root.get("id"), cartId));

            Query q = entityManager.createQuery(query);
            return (Cart) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Cart> getCartsByCustomerId(int customerId) {
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Cart> query = cb.createQuery(Cart.class);
            Root<Cart> root = query.from(Cart.class);

            query.select(root).where(cb.equal(root.get("customer").get("id"), customerId));

            Query q = entityManager.createQuery(query);
            return q.getResultList();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteComment(int id) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            var comment = em.find(Comment.class, id);


                for (Comment reply : comment.getReplies()) {
                    deleteComment(reply.getId());
                }

                if(comment.getWhichProductCommented() != null) {
                    Product product = em.find(Product.class, comment.getWhichProductCommented().getId());
                    product.getComments().remove(comment);
                    em.merge(product);
                }

                User user = getUserByCredentials(comment.getCommentOwner().getLogin(), comment.getCommentOwner().getPassword());
                user.getComments().remove(comment);

                comment.setCommentOwner(null);
                comment.setParentComment(null);
                comment.getReplies().clear();

                em.merge(user);
                em.remove(comment);

                em.getTransaction().commit();
            }
         catch (Exception e) {
             e.printStackTrace();
        } finally {
            if (em != null) em.close();
        }
    }
}


