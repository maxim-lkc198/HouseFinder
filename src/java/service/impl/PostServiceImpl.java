/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import dao.ImageDao;
import dao.PostDao;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import jakarta.servlet.http.Part;
import model.Image;
import model.Post;
import model.User;
import service.ImageUploadService;
import service.PostService;
import constant.PostStatus;
import service.PaymentService; // Thêm service thanh toán

public class PostServiceImpl implements PostService {

    private final PostDao postDao = new PostDao();
    private final ImageDao imageDao = new ImageDao();
    private final ImageUploadService imageUploadService = new ImageUploadServiceImpl();
    private final PaymentService paymentService = new PaymentServiceImpl(); // Khởi tạo PaymentService

    @Override
    public long submitNewPost(Post post, List<Part> imageParts, String thumbnailIdentifier, User currentUser)
            throws IOException, IllegalStateException {
        
        // Luôn coi là PAID_LISTING trong Iteration 1
        post.setSourceType("PAID_LISTING");
        post.setStatus(PostStatus.DRAFT_AWAITING_PAYMENT); // Tạo bài đăng với trạng thái nháp

        // Tạo bài đăng và lấy postId
        long newPostId = postDao.createPost(post);
        if (newPostId == -1) {
            throw new IOException("Không thể tạo bản ghi bài đăng.");
        }

        // Tính toán chi phí
        BigDecimal calculatedFee = calculateFee(post);

        // Gọi PaymentService để xử lý thanh toán
        boolean paymentSuccess = paymentService.chargeForListing(currentUser, calculatedFee, newPostId);

        if (paymentSuccess) {
            // Thanh toán thành công, cập nhật trạng thái bài đăng
            post.setStatus(PostStatus.PENDING_APPROVAL);
            postDao.updatePostStatus(newPostId, PostStatus.PENDING_APPROVAL);

            // Upload và lưu ảnh
            List<Image> uploadedImages = imageUploadService.uploadPostImages(imageParts, newPostId, thumbnailIdentifier);
            if (!uploadedImages.isEmpty()) {
                imageDao.createImages(uploadedImages);
            }

            return newPostId;
        } else {
            // Thanh toán thất bại, hoàn tiền nếu cần
            paymentService.refundListingFee(currentUser, calculatedFee, newPostId, "Hoàn tiền do lỗi hệ thống");
            return -2; // Trả về mã đặc biệt cho biết đã lưu nháp
        }
    }

    
    @Override
    public void payForDraftPost(long postId, User user) {
    Post post = postDao.getPostById(postId);
    if (post.getStatus().equals(PostStatus.DRAFT_AWAITING_PAYMENT)) {
        BigDecimal fee = calculateFee(post);
    boolean paymentSuccess = paymentService.chargeForListing(user, fee, postId);
    if (paymentSuccess) {
            post.setStatus(PostStatus.PENDING_APPROVAL);
            postDao.updatePostStatus(postId, PostStatus.PENDING_APPROVAL);
        }
    }
}
    
    private BigDecimal calculateFee(Post post) {
        BigDecimal dailyRate = "VIP".equals(post.getListingTypeCode()) ? new BigDecimal("20000") : new BigDecimal("10000");
        return dailyRate.multiply(new BigDecimal(post.getDisplayDurationDays()));
    }
    
    // ... các phương thức khác ...
}