package dbad.labs.dbad_lab1.data.daos;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.entities.ShopEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class ShopDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public ShopDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ShopEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(ShopEntity.class, id);
        }
    }

    public List<ShopEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM ShopEntity", ShopEntity.class).list();
        }
    }

    public void save(ShopEntity shop) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(shop);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(ShopEntity shop) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(shop);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(ShopEntity shop) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(shop);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<ShopEntity> search(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM ShopEntity s WHERE " +
                    "lower(s.shopName) LIKE lower(:keyword) OR " +
                    "lower(s.address) LIKE lower(:keyword)";

            Query<ShopEntity> query = session.createQuery(hql, ShopEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        }
    }
}
