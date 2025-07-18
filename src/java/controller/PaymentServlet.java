package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Account;
import service.PaymentService;
import service.PostService;
import service.impl.PaymentServiceImpl;
import service.impl.PostServiceImpl;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private PaymentService paymentService;
    private PostService postService;

    @Override
    public void init() throws ServletException {
        paymentService = new PaymentServiceImpl();
        postService = new PostServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("insufficient-balance".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/jsp/payment/recharge.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account currentAccount = (Account) session.getAttribute("loggedInUser");
        String action = request.getParameter("action");

        if ("recharge".equals(action)) {
            try {
                BigDecimal amount = new BigDecimal(request.getParameter("amount"));
                boolean success = paymentService.processSimulatedRecharge(currentAccount, amount);
                if (success) {
                    session.setAttribute("successMessage", "Nạp tiền thành công!");
                    response.sendRedirect(request.getContextPath() + "/my-account");
                } else {
                    request.setAttribute("errorMessage", "Nạp tiền thất bại!");
                    request.getRequestDispatcher("/WEB-INF/jsp/payment/recharge.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Số tiền không hợp lệ!");
                request.getRequestDispatcher("/WEB-INF/jsp/payment/recharge.jsp").forward(request, response);
            }
        } else if ("pay-draft".equals(action)) {
            try {
                long postId = Long.parseLong(request.getParameter("postId"));
                postService.payForDraftPost(postId, currentAccount);
                session.setAttribute("successMessage", "Thanh toán bài đăng nháp thành công!");
                response.sendRedirect(request.getContextPath() + "/my-posts");
            } catch (IllegalStateException e) {
                request.setAttribute("errorMessage", e.getMessage());
                request.getRequestDispatcher("/WEB-INF/jsp/payment/recharge.jsp").forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}