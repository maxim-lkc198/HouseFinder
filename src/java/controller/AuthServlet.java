package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import service.AuthService;
import service.impl.AuthServiceImpl;
import util.PasswordUtil;
import dao.AccountDao;
import constant.RoleName;

import java.io.IOException;

@WebServlet(name = "AuthServlet", urlPatterns = {"/register", "/login", "/logout", "/forgot-password", "/reset-password"})
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
                showLoginPage(request, response);
                break;
            case "/logout":
                processLogout(request, response);
                break;
            case "/forgot-password":
                showForgotPasswordPage(request, response);
                break;
            case "/reset-password":
                showResetPasswordPage(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();

        switch (action) {
            case "/register":
                processRegister(request, response);
                break;
            case "/login":
                processLogin(request, response);
                break;
            case "/forgot-password":
                processForgotPassword(request, response);
                break;
            case "/reset-password":
                processResetPassword(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showRegisterPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/auth/register_step1_email.jsp").forward(request, response);
    }

    private void processRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subAction = request.getParameter("action");
        if ("checkEmail".equals(subAction)) {
            processCheckEmail(request, response);
        } else if ("processRegistration".equals(subAction)) {
            processFinalRegistration(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/register");
        }
    }

    private void processCheckEmail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email == null || !email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("errorMessage", "Email không hợp lệ.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register_step1_email.jsp").forward(request, response);
            return;
        }
        AccountDao accountDao = new AccountDao();
        if (accountDao.getAccountByEmail(email) != null) {
            request.setAttribute("errorMessage", "Email này đã được đăng ký. Vui lòng sử dụng email khác hoặc đăng nhập.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register_step1_email.jsp").forward(request, response);
            return;
        }
        request.setAttribute("email", email);
        request.getRequestDispatcher("/WEB-INF/jsp/auth/register_step2_details.jsp").forward(request, response);
    }

    private void processFinalRegistration(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");

        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register_step2_details.jsp").forward(request, response);
            return;
        }
        if (password.length() < 8) {
            request.setAttribute("errorMessage", "Mật khẩu phải có ít nhất 8 ký tự.");
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register_step2_details.jsp").forward(request, response);
            return;
        }

        Account account = new Account();
        account.setEmail(email);
        account.setUsername(username);
        account.setPassword(password);
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setPhoneNumber(phoneNumber);

        try {
            boolean success = authService.registerUser(account);
            if (success) {
                request.getSession().setAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                request.setAttribute("errorMessage", "Đã có lỗi không mong muốn xảy ra.");
                request.setAttribute("email", email);
                request.getRequestDispatcher("/WEB-INF/jsp/auth/register_step2_details.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/register_step2_details.jsp").forward(request, response);
        }
    }

    private void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("successMessage") != null) {
            request.setAttribute("successMessage", session.getAttribute("successMessage"));
            session.removeAttribute("successMessage");
        }
        request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
    }

    private void processLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String usernameOrEmail = request.getParameter("usernameOrEmail");
        String password = request.getParameter("password");
        Account account = authService.loginUser(usernameOrEmail, password);

        if (account != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", account);

            String roleName = account.getRole() != null ? account.getRole().getName() : RoleName.USER;
            if (RoleName.ADMIN.equals(roleName)) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            } else {
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } else {
            request.setAttribute("errorMessage", "Tên đăng nhập hoặc mật khẩu không đúng, hoặc tài khoản đã bị khóa.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/login.jsp").forward(request, response);
        }
    }

    private void processLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + "/login?logout=true");
    }

    private void showForgotPasswordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/auth/forgotPassword.jsp").forward(request, response);
    }

    private void processForgotPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        authService.generatePasswordResetToken(email);
        request.setAttribute("message", "Nếu email của bạn tồn tại, một hướng dẫn đặt lại mật khẩu đã được gửi.");
        request.getRequestDispatcher("/WEB-INF/jsp/auth/forgotPassword.jsp").forward(request, response);
    }

    private void showResetPasswordPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        Account account = authService.validatePasswordResetToken(token);

        if (account != null) {
            request.setAttribute("token", token);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/resetPassword.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Đường link không hợp lệ hoặc đã hết hạn.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/forgotPassword.jsp").forward(request, response);
        }
    }

    private void processResetPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp.");
            request.setAttribute("token", token);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/resetPassword.jsp").forward(request, response);
            return;
        }
        if (newPassword.length() < 8) {
            request.setAttribute("errorMessage", "Mật khẩu mới phải có ít nhất 8 ký tự.");
            request.setAttribute("token", token);
            request.getRequestDispatcher("/WEB-INF/jsp/auth/resetPassword.jsp").forward(request, response);
            return;
        }

        boolean success = authService.resetPassword(token, newPassword);

        if (success) {
            request.getSession().setAttribute("successMessage", "Mật khẩu đã được đặt lại thành công. Vui lòng đăng nhập.");
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            request.setAttribute("errorMessage", "Đường link không hợp lệ hoặc đã hết hạn. Vui lòng thử lại.");
            request.getRequestDispatcher("/WEB-INF/jsp/auth/forgotPassword.jsp").forward(request, response);
        }
    }
}