package dbad.labs.dbad_lab1.data.daos;

import com.google.inject.Inject;
import dbad.labs.dbad_lab1.data.entities.DepartmentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class DepartmentDAO {

    private final SessionFactory sessionFactory;

    @Inject
    public DepartmentDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public DepartmentEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(DepartmentEntity.class, id);
        }
    }

    public List<DepartmentEntity> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM DepartmentEntity", DepartmentEntity.class).list();
        }
    }

    public void save(DepartmentEntity department) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(department);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void update(DepartmentEntity department) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(department);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void delete(DepartmentEntity department) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(department);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<DepartmentEntity> search(String keyword) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM DepartmentEntity d WHERE lower(d.departName) LIKE lower(:keyword)";
            Query<DepartmentEntity> query = session.createQuery(hql, DepartmentEntity.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.list();
        }
    }
}