package controller;

import com.google.gson.Gson;
import model.Account;
import model.Category;
import model.District;
import model.Post;
import model.Province;
import model.Ward;
import model.Image;
import model.PostPricing;
import service.AddressService;
import service.CategoryService;
import service.ImageUploadService;
import service.PostPricingService;
import service.PostService;
import service.impl.AddressServiceImpl;
import service.impl.CategoryServiceImpl;
import service.impl.ImageUploadServiceImpl;
import service.impl.PostPricingServiceImpl;
import service.impl.PostServiceImpl;
import util.CloudinaryUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "PostServlet", urlPatterns = {"/post", "/post-detail", "/create-post", "/api/districts", "/api/wards", "/api/image-upload", "/api/image-delete/*"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 1024 * 1024 * 5,
    maxRequestSize = 1024 * 1024 * 50
)
public class PostServlet extends HttpServlet {
    private PostService postService;
    private CategoryService categoryService;
    private AddressService addressService;
    private ImageUploadService imageUploadService;
    private PostPricingService postPricingService;

    @Override
    public void init() throws ServletException {
        postService = new PostServiceImpl();
        categoryService = new CategoryServiceImpl();
        addressService = new AddressServiceImpl();
        imageUploadService = new ImageUploadServiceImpl();
        postPricingService = new PostPricingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        HttpSession session = request.getSession();
        Account currentAccount = (Account) session.getAttribute("loggedInUser");
        String action = request.getServletPath();

        switch (action) {
            case "/create-post":
                showCreatePostForm(request, response);
                break;
            case "/api/districts":
                getDistrictsByProvinceId(request, response);
                break;
            case "/api/wards":
                getWardsByDistrictId(request, response);
                break;
            case "/api/image-delete":
                handleImageDelete(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();

        switch (action) {
            case "/create-post":
                processPostSubmission(request, response);
                break;
            case "/api/image-upload":
                handleImageUpload(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showCreatePostForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Province> provinces = addressService.getProvinces();
        List<Category> categories = categoryService.getAllCategories();
        List<PostPricing> pricingOptions = postPricingService.getAllPricing();

        System.out.println("PostServlet: Fetched provinces: " + provinces.size());
        System.out.println("PostServlet: Fetched categories: " + categories.size());
        System.out.println("PostServlet: Fetched pricingOptions: " + pricingOptions.size());

        request.setAttribute("provinces", provinces);
        request.setAttribute("categories", categories);
        request.setAttribute("pricingOptions", pricingOptions);

        request.getRequestDispatcher("/WEB-INF/jsp/post/createEditPost.jsp").forward(request, response);
    }

    private void getDistrictsByProvinceId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int provinceId = Integer.parseInt(request.getParameter("provinceId"));
            List<District> districts = addressService.getDistrictsByProvinceId(provinceId);

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(districts));
            out.flush();
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\": \"Invalid provinceId\"}");
        }
    }

    private void getWardsByDistrictId(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int districtId = Integer.parseInt(request.getParameter("districtId"));
            List<Ward> wards = addressService.getWardsByDistrictId(districtId);

            response.setContentType("application/json; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(new Gson().toJson(wards));
            out.flush();
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("{\"error\": \"Invalid districtId\"}");
        }
    }

    private void processPostSubmission(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account currentAccount = (Account) session.getAttribute("loggedInUser");

        try {
            Post post = new Post();
            post.setTitle(request.getParameter("postTitle"));
            post.setDescription(request.getParameter("postDescription"));
            post.setAddressProvinceId(Integer.parseInt(request.getParameter("province")));
            post.setAddressDistrictId(Integer.parseInt(request.getParameter("district")));
            post.setAddressWardId(Integer.parseInt(request.getParameter("ward")));
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
            post.setLegalStatus("NOT_SUBMITTED");
            post.setListingTypeCode(request.getParameter("listingType"));
            post.setDisplayDurationDays(Integer.parseInt(request.getParameter("displayDuration")));
            post.setUserId(currentAccount.getId());

            PostPricing pricing = postPricingService.getPricing(post.getListingTypeCode(), post.getDisplayDurationDays());
            if (pricing != null) {
                post.setPricingId(pricing.getId());
            } else {
                throw new IllegalArgumentException("Không tìm thấy giá cho loại bài đăng và thời gian này.");
            }

            List<Part> imageParts = request.getParts().stream()
                .filter(part -> "images".equals(part.getName()) && part.getSize() > 0)
                .collect(Collectors.toList());
            String thumbnailIdentifier = request.getParameter("thumbnailIdentifier");

            System.out.println("PostServlet: Submitting post with title: " + post.getTitle() + ", images: " + imageParts.size());

            long resultPostId = postService.submitNewPost(post, imageParts, thumbnailIdentifier, currentAccount);

            if (resultPostId > 0) {
                session.setAttribute("successMessage", "Thanh toán thành công! Bài đăng của bạn đã được gửi và đang chờ duyệt!");
                response.sendRedirect(request.getContextPath() + "/my-posts");
            } else if (resultPostId == -2) {
                response.sendRedirect(request.getContextPath() + "/payment/insufficient-balance?postId=" + (-resultPostId));
            } else {
                throw new Exception("Không thể tạo bài đăng do lỗi không xác định.");
            }
        } catch (Exception e) {
            System.err.println("PostServlet: Error in processPostSubmission: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã có lỗi xảy ra trong quá trình đăng tin: " + e.getMessage());
            showCreatePostForm(request, response);
        }
    }

    private void handleImageUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            List<Part> imageParts = request.getParts().stream()
                .filter(part -> "images".equals(part.getName()) && part.getSize() > 0)
                .collect(Collectors.toList());

            System.out.println("PostServlet: Received " + imageParts.size() + " image parts for upload");

            if (imageParts.isEmpty()) {
                out.print(new Gson().toJson(new HashMap<String, Object>() {{
                    put("success", false);
                    put("message", "Không có ảnh nào được gửi lên.");
                }}));
                out.flush();
                return;
            }

            List<Image> uploadedImages = imageUploadService.uploadPostImages(imageParts, 0, "thumbnailIdentifier");

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", true);
            responseData.put("message", "Images uploaded successfully");
            responseData.put("images", uploadedImages.stream()
                .map(img -> {
                    Map<String, String> imgData = new HashMap<>();
                    imgData.put("imageUrl", img.getImageUrl());
                    imgData.put("cloudinaryPublicId", img.getCloudinaryPublicId());
                    return imgData;
                })
                .collect(Collectors.toList())
            );

            System.out.println("PostServlet: Image upload successful, images: " + uploadedImages.size());
            out.print(new Gson().toJson(responseData));
        } catch (Exception e) {
            System.err.println("PostServlet: Error uploading images: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "Upload ảnh thất bại: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(new Gson().toJson(errorResponse));
        } finally {
            out.flush();
        }
    }

    private void handleImageDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String publicId = pathInfo != null && pathInfo.length() > 1 ? pathInfo.substring(1) : null;

        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (publicId == null || publicId.trim().isEmpty()) {
            out.print(new Gson().toJson(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "Thiếu publicId");
            }}));
            out.flush();
            return;
        }

        try {
            boolean success = CloudinaryUtil.deleteImage(publicId);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("success", success);
            responseData.put("message", success ? "Xóa ảnh thành công" : "Xóa ảnh thất bại");
            System.out.println("PostServlet: Image delete " + (success ? "successful" : "failed") + " for publicId: " + publicId);
            out.print(new Gson().toJson(responseData));
        } catch (Exception e) {
            System.err.println("PostServlet: Error deleting image: " + e.getMessage());
            e.printStackTrace();
            out.print(new Gson().toJson(new HashMap<String, Object>() {{
                put("success", false);
                put("message", "Xóa ảnh thất bại: " + e.getMessage());
            }}));
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