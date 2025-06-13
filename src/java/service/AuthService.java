/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.User;

/**
 * Interface for Authentication related business logic.
 */
public interface AuthService {

    /**
     * Registers a new user in the system.
     * @param user The User object containing registration details (username, email, plain password, etc.).
     * @return true if registration is successful, false otherwise (e.g., username/email exists).
     * @throws IllegalArgumentException if the provided user data is invalid.
     */
    boolean registerUser(User user) throws IllegalArgumentException;

    // Các phương thức khác như login, forgot password sẽ được thêm sau.
}
