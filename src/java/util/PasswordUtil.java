package util;

// Import lớp BCrypt từ package bạn vừa tạo
import util.BCrypt;

/**
 * Utility class to hash and check passwords using the integrated BCrypt class.
 * This class acts as a wrapper around BCrypt to provide a clean interface
 * for the rest of the application.
 */
public class PasswordUtil {

    // Private constructor to prevent instantiation of this utility class.
    private PasswordUtil() {}

    /**
     * Hashes a plain text password using BCrypt.
     *
     * @param plainPassword The password to be hashed. It should not be null.
     * @return A salted and hashed password string in BCrypt format.
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null) {
            throw new IllegalArgumentException("Password cannot be null.");
        }
        // BCrypt.gensalt(12) generates a random salt. 12 is the log_rounds,
        // a good strength factor. The salt is stored within the resulting hash string itself.
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    /**
     * Checks if a plain text password matches a stored hashed password.
     *
     * @param plainPassword The password entered by the user during login.
     * @param hashedPassword The hashed password retrieved from the database.
     * @return true if the passwords match, false otherwise.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null || hashedPassword.isEmpty()) {
            return false;
        }

        try {
            // BCrypt.checkpw automatically extracts the salt from the hashedPassword
            // and uses it to hash the plainPassword for comparison.
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (IllegalArgumentException e) {
            // This exception is thrown by jBCrypt if the hashedPassword is not in a valid format.
            // It's a good practice to log this error for security monitoring.
            System.err.println("PasswordUtil: Security warning - checkPassword was called with an invalid hash format.");
            e.printStackTrace(); // For debugging purposes
            return false;
        }
    }

    // A main method for quick testing
    public static void main(String[] args) {
        String myPassword = "password123";
        
        // Hashing
        String hashedPassword = hashPassword(myPassword);
        System.out.println("Original Password: " + myPassword);
        System.out.println("Hashed Password:   " + hashedPassword);
        System.out.println("Hash length: " + hashedPassword.length()); // Should be 60 characters

        // Checking
        boolean isCorrect = checkPassword("password123", hashedPassword);
        System.out.println("Is 'password123' correct? " + isCorrect); // Should be true

        boolean isIncorrect = checkPassword("wrongpassword", hashedPassword);
        System.out.println("Is 'wrongpassword' correct? " + isIncorrect); // Should be false
    }
}