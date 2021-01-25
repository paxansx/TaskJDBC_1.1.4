package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Array;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = new Util();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        Transaction transaction = null;
        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id  BIGINT UNSIGNED  AUTO_INCREMENT PRIMARY KEY," +
                    "name VARCHAR(80) NOT NULL, lastname VARCHAR(80) NOT NULL, age INT UNSIGNED);")
            .executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Transaction transaction = null;
        try(Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – "+ name +" добавлен в базу данных");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }


    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try(Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> arr = null;
        Transaction transaction = null;
        try (Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            arr = session.createQuery("from User").getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return arr;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try(Session session = util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }
}
