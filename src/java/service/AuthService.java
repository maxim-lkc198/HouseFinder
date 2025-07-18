/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.Account;

public interface AuthService {

    /**
     * Registers a new user.
     * @param account
     * @return true if successful.
     * @throws IllegalArgumentException if username/email already exists.
     */
    boolean registerUser(Account account) throws IllegalArgumentException;

    /**
     * Authenticates a user.
     * @param username The username or email entered.
     * @param password The plain text password entered.
     * @return The authenticated User object if credentials are correct and user is active, otherwise null.
     */
    Account loginUser(String username, String password);

    /**
     * Generates a password reset token and sends a reset link to the user's email.
     * @param email The user's email address.
     */
    void generatePasswordResetToken(String email);
    
    /**
     * Validates a password reset token.
     * @param token The token from the reset link.
     * @return The User object associated with the token if it's valid and not expired, otherwise null.
     */
    Account validatePasswordResetToken(String token);
    
    /**
     * Resets the user's password using a valid token.
     * @param token The valid token.
     * @param newPassword The new plain text password.
     * @return true if the password was successfully reset.
     */
    boolean resetPassword(String token, String newPassword);
}