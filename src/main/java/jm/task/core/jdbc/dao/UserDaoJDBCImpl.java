package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl  implements UserDao {
    private final Util util = new Util();

    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        try(Statement statement = util.getConnection().createStatement()) {//коректно ли использовать format?
        String table ="CREATE TABLE IF NOT EXISTS users (id NOT N BIGINT UNSIGNED  AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(80) NOT NULL, lastname VARCHAR(80) NOT NULL, age INT UNSIGNED);" ;
            statement.executeUpdate(table);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try(Statement statement = util.getConnection().createStatement()) {
            String table = "DROP TABLE IF EXISTS users;";
            statement.executeUpdate(table);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (Name, Lastname, Age) VALUES ( ?, ?, ?)" ;
        try(PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)) {
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
        try(Statement statement = util.getConnection().createStatement()) {
            String table = String.format( "DELETE FROM users WHERE id = %d;", id) ;
            statement.executeUpdate(table);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try(Statement statement = util.getConnection().createStatement()) {
            String table = "SELECT * FROM users;";
            ResultSet resultSet = statement.executeQuery(table);
            while (resultSet.next()){
                User user = new User(resultSet.getString("name"), resultSet.getString("lastname"),(byte) resultSet.getInt("age"));
                user.setId(resultSet.getLong("id"));
                usersList.add(user );

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        try(Statement statement = util.getConnection().createStatement()) {
            String table = "TRUNCATE TABLE users;" ;
            statement.executeUpdate(table);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
