package dbad.labs.dbad_lab1.data.daos;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.entities.EmployeeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class EmployeeDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public EmployeeDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public EmployeeEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(EmployeeEntity.class, id);
        }
    }

    public List<EmployeeEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM EmployeeEntity e LEFT JOIN FETCH e.shop LEFT JOIN FETCH e.passport", EmployeeEntity.class).list();
        }
    }

    public void save(EmployeeEntity employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void update(EmployeeEntity employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void delete(EmployeeEntity employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(employee);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<EmployeeEntity> search(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM EmployeeEntity e " +
                    "LEFT JOIN FETCH e.shop " +
                    "LEFT JOIN FETCH e.passport " +
                    "WHERE " +
                    "lower(e.firstName) LIKE lower(:keyword) OR " +
                    "lower(e.lastName) LIKE lower(:keyword) OR " +
                    "lower(e.middleName) LIKE lower(:keyword) OR " +
                    "lower(e.address) LIKE lower(:keyword) OR " +
                    "lower(e.passport.passportNum) LIKE lower(:keyword)";

            Query<EmployeeEntity> query = session.createQuery(hql, EmployeeEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        }
    }
}