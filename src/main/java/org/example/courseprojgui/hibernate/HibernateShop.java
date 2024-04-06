package org.example.courseprojgui.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.example.courseprojgui.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class HibernateShop extends GenericHibernate{
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
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();

            User buyer = entityManager.find(User.class, user.getId());

            List<Manager> managers = getAllRecords(Manager.class);
            Optional<Manager> minManager = managers.stream().min(Comparator.comparingInt(manager -> manager.getMyResponsibleCarts().size()));
            Manager managerWhoManageCart = minManager.orElse(null);

            Cart cart = new Cart(buyer, managerWhoManageCart, new ArrayList<>());
            for (Product p : products) {
                Product product = entityManager.find(Product.class, p.getId());

                product.setCart(cart);
                product.setSold(true);
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
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();


            var cart = entityManager.find(Cart.class, id);
            Customer customer = entityManager.find(Customer.class, cart.getCustomer().getId());


            customer.getMyPurchases().remove(cart);

            cart.getItemsToBuy().clear();

            entityManager.merge(customer);
            Manager manager = getEntityById(Manager.class, cart.getManager().getId());
            manager.getMyResponsibleCarts().remove(cart);
            entityManager.merge(manager);

            entityManager.remove(cart);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public List<Product> loadAvailableProducts(){
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
}
