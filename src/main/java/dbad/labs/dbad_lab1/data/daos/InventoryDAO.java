package dbad.labs.dbad_lab1.data.daos;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.entities.InventoryEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class InventoryDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public InventoryDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public InventoryEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(InventoryEntity.class, id);
        }
    }

    public List<InventoryEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
                return session.createQuery("FROM InventoryEntity i LEFT JOIN FETCH i.shop LEFT JOIN FETCH i.product", InventoryEntity.class).list();
        }
    }

    public void save(InventoryEntity inventory) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(inventory);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(InventoryEntity inventory) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(inventory);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(InventoryEntity inventory) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(inventory);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<InventoryEntity> search(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM InventoryEntity i " +
                    "LEFT JOIN FETCH i.shop " +
                    "LEFT JOIN FETCH i.product " +
                    "WHERE " +
                    "lower(i.product.prodName) LIKE lower(:keyword) OR " +
                    "lower(i.shop.shopName) LIKE lower(:keyword) OR " +
                    "str(i.quantity) LIKE :keyword OR " +
                    "str(i.price) LIKE :keyword";

            Query<InventoryEntity> query = session.createQuery(hql, InventoryEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        }
    }
}