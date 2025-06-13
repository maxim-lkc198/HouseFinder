/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Post;
import dao.PostDao; // Giả sử bạn có PostDao
import dao.CategoryDao;
import dao.ProvinceDao;
import model.Category;
import model.Province;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""}) 
public class HomeServlet extends HttpServlet {
    
    // Khởi tạo các DAO cần thiết. Trong ứng dụng thực tế, nên dùng cơ chế tốt hơn là new trực tiếp.
    private final PostDao postDao = new PostDao();
    private final ProvinceDao provinceDao = new ProvinceDao();
    private final CategoryDao categoryDao = new CategoryDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            System.out.println("HomeServlet: doGet() called."); // Dòng debug 1

        try {
            // Lấy một vài bài đăng mới nhất để hiển thị ở phần "Tin nổi bật"
            // Giả sử có một phương thức getRecentPosts trong PostDao
            // List<Post> recentPosts = postDao.getRecentPosts(8); // Lấy 8 bài mới nhất
            
            // Lấy danh sách Provinces và Categories cho search box
            List<Province> provinces = provinceDao.getAllProvinces();
            List<Category> categories = categoryDao.getAllCategories();
            System.out.println("HomeServlet: Data prepared. Forwarding to home.jsp..."); // Dòng debug 2

            // Đặt dữ liệu vào request để JSP có thể truy cập
            // request.setAttribute("recentPosts", recentPosts);
            request.setAttribute("provinces", provinces);
            request.setAttribute("categories", categories);

            // Forward đến trang home.jsp
            request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
            
         } catch (Exception e) {
            System.err.println("HomeServlet: CRITICAL ERROR - An exception occurred!");
            e.printStackTrace(); // In lỗi ra console log của server
            // Chuyển đến trang lỗi
            request.setAttribute("errorMessage", "An internal error occurred while loading the home page.");
            request.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}