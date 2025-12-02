package dbad.labs.dbad_lab1.data.daos;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.entities.PassportEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class PassportDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public PassportDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public PassportEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(PassportEntity.class, id);
        }
    }

    public List<PassportEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM PassportEntity p LEFT JOIN FETCH p.department", PassportEntity.class).list();
        }
    }

    public void save(PassportEntity passport) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(passport);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(PassportEntity passport) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(passport);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(PassportEntity passport) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(passport);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<PassportEntity> search(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM PassportEntity p " +
                    "LEFT JOIN FETCH p.department " +
                    "WHERE " +
                    "lower(p.passportNum) LIKE lower(:keyword) OR " +
                    "lower(p.department.departName) LIKE lower(:keyword)";

            Query<PassportEntity> query = session.createQuery(hql, PassportEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        }
    }
}