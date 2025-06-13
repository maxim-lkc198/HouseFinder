/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import constant.RoleName;
import dao.RoleDao;
import dao.UserDao;
import model.Role;
import model.User;
import service.AuthService;
import service.EmailService; 
import util.PasswordUtil;
import java.time.LocalDateTime;
import java.util.UUID;

public class AuthServiceImpl implements AuthService {

    private final UserDao userDao = new UserDao();
    private final RoleDao roleDao = new RoleDao();
    private final EmailService emailService = new EmailServiceImpl(); 

    /**
     * Registers a new user with the default 'ROLE_USER'.
     *
     * @param user The User object containing data from the registration form.
     *             It must have username, email, and a plain text password set.
     * @return true if the user was successfully created.
     * @throws IllegalArgumentException if the username or email already exists in the database.
     * @throws RuntimeException if the default 'ROLE_USER' cannot be found in the database,
     *                          which indicates a critical configuration error.
     */
    @Override
    public boolean registerUser(User user) throws IllegalArgumentException {
        // 1. Validate input (dù đã làm ở Servlet, Service nên tự bảo vệ mình)
        if (user == null || user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User object or its required fields cannot be null.");
        }

        // 2. Check for uniqueness of username and email
        if (userDao.getUserByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Tên đăng nhập '" + user.getUsername() + "' đã được sử dụng.");
        }
        if (userDao.getUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' đã được đăng ký.");
        }

        // 3. Get the default role for new users
        Role userRole = roleDao.getRoleByName(RoleName.USER);
        if (userRole == null) {
            // This is a system-level error, not a user error.
            // The application cannot function correctly without this role.
            throw new RuntimeException("CRITICAL CONFIGURATION ERROR: 'ROLE_USER' not found in the database.");
        }

        // 4. Assign the role to the user object
        // The UserDao expects a Role object to get the role_id from.
        user.setRole(userRole);
        user.setRoleId(userRole.getId()); // Đảm bảo roleId cũng được set nếu DAO cần

        // 5. Call the DAO to create the user.
        // The DAO is responsible for hashing the password before saving.
        return userDao.createUser(user);
    }

    @Override
    public User loginUser(String usernameOrEmail, String password) {
        // Có thể login bằng username hoặc email
        User user = userDao.getUserByUsername(usernameOrEmail);
        if (user == null) {
            user = userDao.getUserByEmail(usernameOrEmail);
        }

        if (user != null && user.isIsActive()) {
            // Kiểm tra mật khẩu
            if (PasswordUtil.checkPassword(password, user.getPassword())) {
                return user;
            }
        }
        return null; // Trả về null nếu không tìm thấy user, user bị ban, hoặc sai mật khẩu
    }

    @Override
    public void generatePasswordResetToken(String email) {
        User user = userDao.getUserByEmail(email);
        if (user != null && user.isIsActive()) {
            String token = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1); // Token hết hạn sau 1 giờ

            if (userDao.savePasswordResetToken(user.getId(), token, expiryDate)) {
                // Gửi email chứa link reset
                String resetLink = "http://localhost:8080/HouseFinder/reset-password?token=" + token; // Cần cấu hình base URL
                String subject = "Password Reset Request for FindHouse";
                String body = "<h1>Password Reset Request</h1>"
                            + "<p>Hi " + user.getFirstName() + ",</p>"
                            + "<p>You requested to reset your password. Please click the link below to set a new password. This link is valid for 1 hour.</p>"
                            + "<a href=\"" + resetLink + "\">Reset Your Password</a>"
                            + "<p>If you did not request this, please ignore this email.</p>";
                emailService.sendEmail(email, subject, body);
            }
        }
        // Nếu không tìm thấy email, chúng ta không làm gì cả để tránh việc lộ thông tin (email enumeration).
    }
    
    @Override
    public User validatePasswordResetToken(String token) {
        User user = userDao.getUserByPasswordResetToken(token);
        if (user != null && user.getPasswordResetTokenExpiry().isAfter(LocalDateTime.now())) {
            // Token hợp lệ và chưa hết hạn
            return user;
        }
        return null;
    }
    
    @Override
    public boolean resetPassword(String token, String newPassword) {
        User user = validatePasswordResetToken(token); // Tái sử dụng hàm validate
        if (user != null) {
            String newHashedPassword = PasswordUtil.hashPassword(newPassword);
            // Vô hiệu hóa token và cập nhật mật khẩu mới
            return userDao.updatePasswordAndInvalidateToken(user.getId(), newHashedPassword);
        }
        return false;
    }
}