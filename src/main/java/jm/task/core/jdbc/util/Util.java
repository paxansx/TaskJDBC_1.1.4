package jm.task.core.jdbc.util;



import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.criterion.Property;
import org.hibernate.service.ServiceRegistry;

import javax.swing.plaf.IconUIResource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/peoples";
    //?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            return connection;

        } catch (SQLException throwables) {
            System.out.println("Не подключились");
            throwables.printStackTrace();
        }
        return null;
    }

    private static SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
//            if (sessionFactory != null) {
                try {
                    Configuration configuration = new Configuration();
                    Properties settings = new Properties();
                    settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                    settings.put(Environment.URL, "jdbc:mysql://localhost:3306/peoples");
                    settings.put(Environment.USER, "root");
                    settings.put(Environment.PASS, "root");
                    settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                    settings.put(Environment.SHOW_SQL, "true");
                    settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                    //settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                    configuration.setProperties(settings);
                    configuration.addAnnotatedClass(User.class);
                    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                            .applySettings(configuration.getProperties()).build();
                    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                } catch (Exception e) {
                    e.printStackTrace();
//                }
            }
        return sessionFactory;
    }
}