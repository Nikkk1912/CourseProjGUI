package org.example.courseprojgui.hibernate;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.example.courseprojgui.model.User;
import org.example.courseprojgui.model.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class GenericHibernate {
    private EntityManagerFactory entityManagerFactory;

    public GenericHibernate(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public <T> void create(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        }catch (Exception e) {

        } finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    public <T> void update(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(entity);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    public <T> List<T> getAllRecords(Class<T> className) {
        List<T> listOfRecords = new ArrayList<>();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(className));
            Query q = entityManager.createQuery(query);
            listOfRecords = q.getResultList();
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
        return listOfRecords;
    }

    public <T> void delete(T entity) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (!entityManager.contains(entity)) {
                entity = entityManager.merge(entity);
            }
            entityManager.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public <T> void delete(Class<T> entityClass, int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            T object = entityManager.find(entityClass, id);
            entityManager.remove(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public <T> T getEntityById(Class<T> entityClass, int id) {
        EntityManager em = null;
        T result = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            result = em.find(entityClass, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public <T> List<T> getEntityByWarehouseId(Class<T> entityClass, int warehouseId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<T> listOfRecords = new ArrayList<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = cb.createQuery(entityClass);
            Root<T> root = query.from(entityClass);


            //query.select(root).where(cb.equal(root.get("warehouse"), warehouseId));
            Join<T, Warehouse> warehouseJoin = root.join("warehouse");
            query.select(root).where(cb.equal(warehouseJoin.get("id"), warehouseId));

            Query q = entityManager.createQuery(query);
            listOfRecords = q.getResultList();
            return listOfRecords;
        } catch (NoResultException e) {
            return null;
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
}
