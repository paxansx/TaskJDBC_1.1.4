package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl  implements UserDao {
    private final String nameTable = "users";
    private final Util util = new Util();

    public UserDaoJDBCImpl() {

    }

    public String getNameTable() {
        return nameTable;
    }

    public void createUsersTable() {
        try(Connection connection = util.getConnection()) {//коректно ли использовать format?
        String table =String.format("CREATE TABLE IF NOT EXISTS %s (id BIGINT UNSIGNED  AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(80) NOT NULL, last_name VARCHAR(80) NOT NULL, age INT UNSIGNED);", nameTable) ;
            Statement statement =  connection.createStatement();
            statement.executeUpdate(table);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try(Connection connection = util.getConnection()) {
            String table = "DROP TABLE IF EXISTS %s;";
            String form = String.format(table,nameTable);
            Statement statement = connection.createStatement();
            statement.executeUpdate(form);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = String.format("INSERT INTO %s (Name, Last_name, Age) VALUES ( ?, ?, ?)", nameTable) ;

        try(Connection connection = util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setLong(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – "+ name +" добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void removeUserById(long id) {
        try(Connection connection = util.getConnection()) {
            String table = String.format( "DELETE FROM %S WHERE id = %d;", nameTable, id) ;

            Statement statement = connection.createStatement();
            statement.executeUpdate(table);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try(Connection connection = util.getConnection()) {
            String table = "SELECT * FROM users;";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(table);
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"),(byte) resultSet.getInt("age"));
                user.setId(resultSet.getLong("id"));
                usersList.add(user);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try(Connection connection = util.getConnection()) {
            String table = String.format("TRUNCATE TABLE %S;", nameTable) ;
            Statement statement = connection.createStatement();
            statement.executeUpdate(table);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
