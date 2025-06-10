package service;

import dao.UserDAO;
import model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User loginUser(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return null; // Invalid input
        }
        return userDAO.getUserByUsernameAndPassword(username, password);
    }

    public boolean registerUser(String username, String password, String fullname) {
        if (username == null || username.trim().isEmpty() ||
            password == null || password.isEmpty() ||
            fullname == null || fullname.trim().isEmpty()) {
            return false; // Invalid input
        }
        if (userDAO.usernameExists(username)) {
            return false; // Username already exists
        }
        User newUser = new User(0, username, password, fullname); // id sẽ tự tăng
        return userDAO.createUser(newUser);
    }
}