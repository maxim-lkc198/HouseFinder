/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import constant.PostStatus;
import dao.ImageDao;
import dao.PostDao;
import dao.UserDao; // Cần để lấy thông tin user nếu cần
import java.io.IOException;
import java.util.List;
import jakarta.servlet.http.Part;
import model.Image;
import model.Post;
import model.User;
import service.ImageUploadService;
import service.PostService;

public class PostServiceImpl implements PostService {

    private final PostDao postDao = new PostDao();
    private final ImageDao imageDao = new ImageDao();
    private final ImageUploadService imageUploadService = new ImageUploadServiceImpl();
    // private final UserDao userDao = new UserDao(); // Nếu cần lấy user
    // private final MembershipService membershipService = new MembershipServiceImpl(); // Sẽ dùng ở I2

    private static final int MAX_ACTIVE_POSTS_ITERATION_1 = 2; 

    @Override
    public long submitNewPost(Post post, List<Part> imageParts, String thumbnailInputName, User currentUser) 
            throws IOException, IllegalStateException {
        
        // Bước 1: Kiểm tra nghiệp vụ (VD: giới hạn bài đăng)
        // int activePosts = postDao.countActivePostsForUser(currentUser.getId()); // Cần thêm hàm này vào DAO
        // if (activePosts >= MAX_ACTIVE_POSTS_ITERATION_1) {
        //     throw new IllegalStateException("Bạn đã đạt đến giới hạn số bài đăng active.");
        // }
        // Tạm thời bỏ qua check này trong I1 để tập trung vào luồng chính
        
        // Bước 2: Tạo bài đăng trước để lấy Post ID
        // Logic cho Iteration 1: Luôn là PAID_LISTING và PENDING_APPROVAL
        post.setUserId(currentUser.getId());
        post.setStatus(PostStatus.PENDING_APPROVAL); 
        // Trong I1, giả sử mọi tin đăng đều là mua lẻ để test luồng.
        // Logic chọn benefit/trả phí sẽ được thêm ở I2/I3.
        post.setSourceType("PAID_LISTING"); // Giả định cho I1
        
        long newPostId = postDao.createPost(post);
        if (newPostId == -1) {
            throw new IOException("Failed to create post record in database.");
        }

        // Bước 3: Upload ảnh và lấy URL
        List<Image> uploadedImages = imageUploadService.uploadPostImages(imageParts, newPostId, thumbnailInputName);

        // Bước 4: Lưu thông tin ảnh vào CSDL
        if (!uploadedImages.isEmpty()) {
            imageDao.createImages(uploadedImages);
        }

        return newPostId;
    }
}