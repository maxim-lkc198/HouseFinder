package service.impl;

import constant.RoleName;
import dao.AccountDao;
import dao.RoleDao;
import model.Account;
import model.Role;
import service.AuthService;
import service.EmailService;
import service.impl.EmailServiceImpl;
import util.PasswordUtil;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {
    private final AccountDao accountDao;
    private final RoleDao roleDao;
    private final EmailService emailService;

    public AuthServiceImpl() {
        accountDao = new AccountDao();
        roleDao = new RoleDao();
        emailService = new EmailServiceImpl();
    }

    @Override
    public boolean registerUser(Account account) throws IllegalArgumentException {
        if (account == null || account.getUsername() == null || account.getEmail() == null || account.getPassword() == null) {
            System.err.println("Invalid account data: " + (account == null ? "null" : account.getUsername()));
            throw new IllegalArgumentException("Account object or its required fields cannot be null.");
        }

        if (accountDao.getAccountByUsername(account.getUsername()) != null) {
            System.err.println("Username already exists: " + account.getUsername());
            throw new IllegalArgumentException("Tên đăng nhập '" + account.getUsername() + "' đã được sử dụng.");
        }
        if (accountDao.getAccountByEmail(account.getEmail()) != null) {
            System.err.println("Email already exists: " + account.getEmail());
            throw new IllegalArgumentException("Email '" + account.getEmail() + "' đã được đăng ký.");
        }

        Role userRole = roleDao.getRoleByName(RoleName.USER);
        if (userRole == null) {
            System.err.println("ROLE_USER not found in database");
            throw new RuntimeException("CRITICAL CONFIGURATION ERROR: 'ROLE_USER' not found in the database.");
        }

        account.setRole(userRole);
        account.setRoleId(userRole.getId());

        boolean success = accountDao.createAccount(account);
        if (success) {
            System.out.println("User registered successfully: " + account.getUsername());
        } else {
            System.err.println("Failed to register user: " + account.getUsername());
        }
        return success;
    }

    @Override
    public Account loginUser(String usernameOrEmail, String password) {
        System.out.println("Attempting login for: " + usernameOrEmail);
        Account account = accountDao.getAccountByUsername(usernameOrEmail);
        if (account == null) {
            account = accountDao.getAccountByEmail(usernameOrEmail);
        }

        if (account != null) {
            System.out.println("Account found: " + account.getUsername() + ", isActive: " + account.isActive());
            if (account.isActive()) {
                boolean passwordMatch = PasswordUtil.checkPassword(password, account.getPassword());
                System.out.println("Password verification for " + usernameOrEmail + ": " + passwordMatch);
                if (passwordMatch) {
                    System.out.println("Login successful for: " + account.getUsername());
                    return account;
                } else {
                    System.err.println("Password mismatch for " + usernameOrEmail);
                }
            } else {
                System.err.println("Account is not active: " + account.getUsername());
            }
        } else {
            System.err.println("No account found for username/email: " + usernameOrEmail);
        }
        return null;
    }

    @Override
    public void generatePasswordResetToken(String email) {
        Account account = accountDao.getAccountByEmail(email);
        if (account != null && account.isActive()) {
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

            if (accountDao.savePasswordResetToken(account.getId(), token, expiryDate)) {
                String resetLink = "http://localhost:9999/HouseFinder/reset-password?token=" + token;
                String subject = "Password Reset Request for HouseFinder";
                String body = "<h1>Password Reset Request</h1>"
                            + "<p>Hi " + account.getFirstName() + ",</p>"
                            + "<p>You requested to reset your password. Please click the link below to set a new password. This link is valid for 1 hour.</p>"
                            + "<a href=\"" + resetLink + "\">Reset Your Password</a>"
                            + "<p>If you did not request this, please ignore this email.</p>";
                emailService.sendEmail(email, subject, body);
                System.out.println("Password reset token generated for: " + email);
            } else {
                System.err.println("Failed to save password reset token for: " + email);
            }
        } else {
            System.err.println("No active account found for email: " + email);
        }
    }

    @Override
    public Account validatePasswordResetToken(String token) {
        Account account = accountDao.getAccountByPasswordResetToken(token);
        if (account != null && account.getPasswordResetTokenExpiry() != null && 
            account.getPasswordResetTokenExpiry().isAfter(LocalDateTime.now())) {
            System.out.println("Valid password reset token for: " + account.getUsername());
            return account;
        }
        System.err.println("Invalid or expired password reset token: " + token);
        return null;
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Account account = validatePasswordResetToken(token);
        if (account != null) {
            String newHashedPassword = PasswordUtil.hashPassword(newPassword);
            boolean success = accountDao.updatePasswordAndInvalidateToken(account.getId(), newHashedPassword);
            if (success) {
                System.out.println("Password reset successfully for: " + account.getUsername());
            } else {
                System.err.println("Failed to reset password for: " + account.getUsername());
            }
            return success;
        }
        System.err.println("Password reset failed for token: " + token);
        return false;
    }
}