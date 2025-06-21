package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Category;
import model.Image;
import model.Post;
import model.Province;
import model.User;
import service.ImageUploadService;
import service.PostService;
import service.impl.ImageUploadServiceImpl;
import service.impl.PostServiceImpl;
import dao.CategoryDao;
import dao.ProvinceDao;
import com.google.gson.Gson;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import util.CloudinaryUtil;

@WebServlet(name = "PostServlet", urlPatterns = {"/post", "/post-detail", "/create-post", "/upload-images", "/delete-image/*"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 1024 * 1024 * 5,       // 5 MB
    maxRequestSize = 1024 * 1024 * 50    // 50 MB
)
public class PostServlet extends HttpServlet {

    private final PostService postService = new PostServiceImpl();
    private final ProvinceDao provinceDao = new ProvinceDao();
    private final CategoryDao categoryDao = new CategoryDao();
    private final ImageUploadService imageUploadService = new ImageUploadServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("loggedInUser");
        
        String action = request.getServletPath();
        
        switch(action) {
            case "/create-post":
                showCreatePostForm(request, response);
                break;
            case "/posts":
                // showPostList(request, response); // Sẽ làm ở cụm công việc khác
                break;
            case "/post-detail":
                // showPostDetail(request, response); // Sẽ làm ở cụm công việc khác
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
            case "/create-post":
                processPostSubmission(request, response);
                break;
            case "/upload-images":
                handleImageUpload(request, response);
                break;
            case "/delete-image/*":
                handleImageDelete(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showCreatePostForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Province> provinces = provinceDao.getAllProvinces();
        List<Category> categories = categoryDao.getAllCategories();
        
        request.setAttribute("provinces", provinces);
        request.setAttribute("categories", categories);
        
        request.getRequestDispatcher("/WEB-INF/jsp/post/createEditPost.jsp").forward(request, response);
    }
    
    private void processPostSubmission(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("loggedInUser");

        try {
            Post post = new Post();
            post.setTitle(request.getParameter("postTitle"));
            post.setDescription(request.getParameter("postDescription"));
            post.setAddressProvinceId(Integer.parseInt(request.getParameter("province")));
            post.setAddressDistrict(request.getParameter("district"));
            post.setAddressWard(request.getParameter("ward"));
            post.setAddressStreet(request.getParameter("street"));
            post.setAddressDetail(request.getParameter("addressDetail"));
            post.setContactName(request.getParameter("contactName"));
            post.setContactPhone(request.getParameter("contactPhone"));
            post.setContactEmail(request.getParameter("contactEmail"));
            post.setPrice(new BigDecimal(request.getParameter("price")));
            post.setArea(Float.parseFloat(request.getParameter("area")));
            post.setCategoryId(Integer.parseInt(request.getParameter("category")));
            post.setBedrooms(tryParseInt(request.getParameter("bedrooms")));
            post.setBathrooms(tryParseInt(request.getParameter("bathrooms")));
            post.setFloors(tryParseInt(request.getParameter("floors")));
            post.setHouseDirection(request.getParameter("houseDirection"));
            post.setBalconyDirection(request.getParameter("balconyDirection"));
            post.setAvailabilityInfo(request.getParameter("availabilityInfo"));
            post.setElectricityCost(request.getParameter("electricityCost"));
            post.setWaterCost(request.getParameter("waterCost"));
            post.setInternetCost(request.getParameter("internetCost"));
            post.setLegalStatus(request.getParameter("legalStatus"));
            post.setFurniture(String.join(",", request.getParameterValues("furniture") != null ? request.getParameterValues("furniture") : new String[0]));
            post.setAmenities(String.join(",", request.getParameterValues("amenities") != null ? request.getParameterValues("amenities") : new String[0]));
            post.setSafety(String.join(",", request.getParameterValues("safety") != null ? request.getParameterValues("safety") : new String[0]));
            post.setListingTypeCode(request.getParameter("listingType"));
            post.setDisplayDurationDays(Integer.parseInt(request.getParameter("displayDuration")));

            String thumbnailIdentifier = request.getParameter("thumbnailIdentifier");
            List<Part> imageParts = (List<Part>) session.getAttribute("tempImages"); // Lấy ảnh tạm từ session

            long resultPostId = postService.submitNewPost(post, imageParts != null ? imageParts : new ArrayList<>(), thumbnailIdentifier, currentUser);
            
            if (resultPostId > 0) {
                session.setAttribute("successMessage", "Thanh toán thành công! Bài đăng của bạn đã được gửi và đang chờ duyệt!");
                response.sendRedirect(request.getContextPath() + "/my-posts");
            } else if (resultPostId == -2) {
                session.setAttribute("draftPostId", resultPostId); // Lưu ID bài nháp
                response.sendRedirect(request.getContextPath() + "/payment?postId=" + (-resultPostId)); // Chuyển đến trang thanh toán
            } else {
                throw new Exception("Không thể tạo bài đăng do lỗi không xác định.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình đăng tin: " + e.getMessage());
            showCreatePostForm(request, response);
        }
    }

    private void handleImageUpload(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    // Lấy danh sách các Part chứa ảnh từ request
    List<Part> imageParts = request.getParts().stream()
        .filter(part -> "images".equals(part.getName()) && part.getSize() > 0)
        .collect(Collectors.toList());
    
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    try {
        // Gọi ImageUploadService để upload ảnh
        ImageUploadService imageUploadService = new ImageUploadServiceImpl();
        List<Image> uploadedImages = imageUploadService.uploadPostImages(imageParts, 0, null); // postId = 0 vì chưa tạo post

        // Tạo dữ liệu JSON trả về
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", true);
        responseData.put("images", uploadedImages.stream()
            .map(img -> {
                Map<String, String> imgData = new HashMap<>();
                imgData.put("imageUrl", img.getImageUrl());
                imgData.put("cloudinaryPublicId", img.getCloudinaryPublicId());
                return imgData;
            })
            .collect(Collectors.toList())
        );

        // Ghi JSON vào response
        out.write(new Gson().toJson(responseData));
        } catch (Exception e) {
            e.printStackTrace();
            // Trả về JSON lỗi nếu upload thất bại
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Upload ảnh thất bại: " + e.getMessage());
            out.write(new Gson().toJson(errorResponse));
        } finally {
            out.flush();
        }
    }

    private void handleImageDelete(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    // Lấy publicId từ URL
    String pathInfo = request.getPathInfo(); // /delete-image/{id}
    String publicId = pathInfo != null && pathInfo.length() > 1 ? pathInfo.substring(1) : null;

    response.setContentType("application/json");
    PrintWriter out = response.getWriter();

    if (publicId == null || publicId.isEmpty()) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Thiếu publicId");
        out.write(new Gson().toJson(errorResponse));
        out.flush();
        return;
    }

    try {
        // Gọi CloudinaryUtil để xóa ảnh
        CloudinaryUtil.deleteImage(publicId);

        // Trả về JSON thành công
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("success", true);
        responseData.put("message", "Xóa ảnh thành công");
        out.write(new Gson().toJson(responseData));
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Xóa ảnh thất bại: " + e.getMessage());
            out.write(new Gson().toJson(errorResponse));
        } finally {
            out.flush();
        }
    }

    private Integer tryParseInt(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}