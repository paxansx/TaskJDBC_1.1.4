package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;


public class Main {
    public static void main(String[] args) {
        UserService user1 = new UserServiceImpl();
        user1.createUsersTable();
        user1.saveUser("jon1", "smit", (byte) 25);
        user1.saveUser("jon22", "smit", (byte) 24);
        user1.saveUser("jon31", "smit", (byte) 26);
        user1.saveUser("jon42", "smit", (byte) 28);
//        System.out.println(user1.getAllUsers());
//        user1.cleanUsersTable();
//        user1.dropUsersTable();

    }
}
