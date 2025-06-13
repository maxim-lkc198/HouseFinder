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

public class AuthServiceImpl implements AuthService {

    private final UserDao userDao = new UserDao();
    private final RoleDao roleDao = new RoleDao();

    @Override
    public boolean registerUser(User user) throws IllegalArgumentException {
        // 1. Kiểm tra username hoặc email đã tồn tại chưa
        if (userDao.getUserByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username '" + user.getUsername() + "' already exists.");
        }
        if (userDao.getUserByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email '" + user.getEmail() + "' is already registered.");
        }

        // 2. Lấy vai trò "ROLE_USER" từ CSDL
        Role userRole = roleDao.getRoleByName(RoleName.USER);
        if (userRole == null) {
            // Đây là lỗi hệ thống nghiêm trọng, nên ném ra một lỗi rõ ràng
            throw new RuntimeException("CRITICAL: 'ROLE_USER' not found in the database.");
        }
        
        // 3. Gán vai trò vào đối tượng user
        user.setRole(userRole);
        
        // 4. Gọi DAO để tạo user. DAO sẽ lo việc hash mật khẩu.
        return userDao.createUser(user);
    }
}