package dbad.labs.dbad_lab1.data.daos;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.entities.SaleEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class SaleDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public SaleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SaleEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(SaleEntity.class, id);
        }
    }

    public List<SaleEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM SaleEntity s LEFT JOIN FETCH s.shop LEFT JOIN FETCH s.product LEFT JOIN FETCH s.employee", SaleEntity.class).list();
        }
    }

    public void save(SaleEntity sale) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(sale);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(SaleEntity sale) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(sale);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(SaleEntity sale) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(sale);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<SaleEntity> search(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM SaleEntity s " +
                    "LEFT JOIN FETCH s.shop " +
                    "LEFT JOIN FETCH s.product " +
                    "LEFT JOIN FETCH s.employee " +
                    "WHERE " +
                    "lower(s.product.prodName) LIKE lower(:keyword) OR " +
                    "lower(s.employee.lastName) LIKE lower(:keyword) OR " +
                    "lower(s.shop.shopName) LIKE lower(:keyword) OR " +
                    "str(s.quantitySold) LIKE :keyword";

            Query<SaleEntity> query = session.createQuery(hql, SaleEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        }
    }
}