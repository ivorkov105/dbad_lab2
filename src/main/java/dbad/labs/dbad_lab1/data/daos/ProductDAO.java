package dbad.labs.dbad_lab1.data.daos;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.entities.ProductEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class ProductDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public ProductDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public ProductEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(ProductEntity.class, id);
        }
    }

    public List<ProductEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM ProductEntity", ProductEntity.class).list();
        }
    }

    public void save(ProductEntity product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(ProductEntity product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(ProductEntity product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(product);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<ProductEntity> search(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM ProductEntity p WHERE " +
                    "lower(p.prodName) LIKE lower(:keyword) OR " +
                    "lower(p.unit) LIKE lower(:keyword)";

            Query<ProductEntity> query = session.createQuery(hql, ProductEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        }
    }
}