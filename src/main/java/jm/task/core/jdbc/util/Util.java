package jm.task.core.jdbc.util;



import javax.swing.plaf.IconUIResource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/peoples";
    //?verifyServerCertificate=false&useSSL=true&serverTimezone=UTC
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";
    public Connection getConnection(){
        try{
            Connection connection = DriverManager.getConnection(URL,LOGIN,PASSWORD);
            return connection;

        } catch (SQLException throwables) {
            System.out.println("Не подключились");
            throwables.printStackTrace();
        }
        return null;
    }
}
