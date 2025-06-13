/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.AuthService;
import service.impl.AuthServiceImpl;

// Ánh xạ servlet này với các URL liên quan đến xác thực
@WebServlet(name = "AuthServlet", urlPatterns = {"/register", "/login", "/logout"})
public class AuthServlet extends HttpServlet {

    private final AuthService authService = new AuthServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/register":
                showRegisterPage(request, response);
                break;
            case "/login":
                // showLoginPage(request, response); // Sẽ được viết sau
                break;
            case "/logout":
                // processLogout(request, response); // Sẽ được viết sau
                break;
            default:
                // Handle default case or error
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/register":
                processRegister(request, response);
                break;
            case "/login":
                // processLogin(request, response); // Sẽ được viết sau
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }

    private void showRegisterPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/auth/register.jsp").forward(request, response);
    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // Đảm bảo đọc được tiếng Việt

        // Lấy tất cả tham số từ form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");

        // --- VALIDATION PHÍA SERVER ---
        // 1. Kiểm tra mật khẩu khớp nhau
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            showRegisterPage(request, response);
            return;
        }

        // 2. Kiểm tra độ mạnh mật khẩu (ví dụ đơn giản)
        if (password.length() < 8) {
            request.setAttribute("errorMessage", "Password must be at least 8 characters long.");
            showRegisterPage(request, response);
            return;
        }
        
        // (Thêm các validation khác nếu cần: định dạng email, username...)

        // Tạo đối tượng User
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // Truyền mật khẩu thô, Service/DAO sẽ hash
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        
        // Gọi Service để đăng ký
        try {
            boolean success = authService.registerUser(user);
            if (success) {
                // Đăng ký thành công, chuyển hướng về trang login với thông báo
                response.sendRedirect(request.getContextPath() + "/login?register=success");
            } else {
                // Lỗi không xác định từ service
                request.setAttribute("errorMessage", "An unexpected error occurred. Please try again.");
                showRegisterPage(request, response);
            }
        } catch (IllegalArgumentException e) {
            // Bắt lỗi nghiệp vụ từ Service (username/email đã tồn tại)
            request.setAttribute("errorMessage", e.getMessage());
            showRegisterPage(request, response);
        }
    }
}