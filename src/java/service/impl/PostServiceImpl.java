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
import model.Account;
import service.ImageUploadService;
import service.PostService;
import constant.PostStatus;
import dao.TransactionDao;
import dao.AccountDao;
import model.Transaction;
import service.PaymentService; // Thêm service thanh toán
import service.PostPricingService;


  public class PostServiceImpl implements PostService {
      private PostDao postDao;
      private ImageDao imageDao;
      private ImageUploadService imageUploadService;
      private PaymentService paymentService;
      private PostPricingService pricingService;
      private TransactionDao transactionDao;
      private AccountDao userDao;

      public PostServiceImpl() {
          postDao = new PostDao();
          imageDao = new ImageDao();
          imageUploadService = new ImageUploadServiceImpl();
          paymentService = new PaymentServiceImpl();
          pricingService = new PostPricingServiceImpl();
          transactionDao = new TransactionDao();
          userDao = new AccountDao();
      }

      @Override
      public long submitNewPost(Post post, List<Part> imageParts, String thumbnailIdentifier, Account currentUser) throws IOException {
          post.setSourceType("PAID_LISTING");
          post.setLegalStatus("NOT_SUBMITTED");

          BigDecimal cost = pricingService.calculateTotalCost(post.getListingTypeCode(), post.getDisplayDurationDays());
          boolean paymentSuccess = paymentService.chargeForListing(currentUser, cost, post.getId());

          if (paymentSuccess) {
              post.setUserId(currentUser.getId());
              post.setStatus(PostStatus.PENDING_APPROVAL);
              long newPostId = postDao.createPost(post);
              if (newPostId == -1) {
                  paymentService.refundListingFee(currentUser, cost, newPostId, "Hoàn tiền do lỗi hệ thống");
                  throw new IOException("Không thể tạo bản ghi bài đăng sau khi đã thanh toán.");
              }

              List<Image> uploadedImages = imageUploadService.uploadPostImages(imageParts, newPostId, thumbnailIdentifier);
              if (!uploadedImages.isEmpty()) {
                  imageDao.createImages(uploadedImages);
              }

              Transaction tx = new Transaction();
              tx.setUserId(currentUser.getId());
              tx.setTransactionType("LISTING_FEE_PAYMENT");
              tx.setAmount(cost.negate());
              tx.setOriginalAmount(cost);
              tx.setDescription("Thanh toán cho bài đăng ID: " + newPostId);
              tx.setStatus("COMPLETED");
              tx.setRelatedPostId(newPostId);
              transactionDao.createTransaction(tx);

              return newPostId;
          } else {
              post.setUserId(currentUser.getId());
              post.setStatus(PostStatus.DRAFT_AWAITING_PAYMENT);
              long draftPostId = postDao.createPost(post);
              return draftPostId > 0 ? -2 : -1;
          }
      }

      @Override
      public void payForDraftPost(long postId, Account user) {
          Post post = postDao.getPostById(postId);
          if (post.getStatus().equals(PostStatus.DRAFT_AWAITING_PAYMENT)) {
              BigDecimal cost = pricingService.calculateTotalCost(post.getListingTypeCode(), post.getDisplayDurationDays());
              boolean paymentSuccess = paymentService.chargeForListing(user, cost, postId);
              if (paymentSuccess) {
                  post.setStatus(PostStatus.PENDING_APPROVAL);
                  postDao.updatePostStatus(postId, PostStatus.PENDING_APPROVAL);

                  Transaction tx = new Transaction();
                  tx.setUserId(user.getId());
                  tx.setTransactionType("LISTING_FEE_PAYMENT");
                  tx.setAmount(cost.negate());
                  tx.setOriginalAmount(cost);
                  tx.setDescription("Thanh toán cho bài đăng nháp ID: " + postId);
                  tx.setStatus("COMPLETED");
                  tx.setRelatedPostId(postId);
                  transactionDao.createTransaction(tx);
              } else {
                  throw new IllegalStateException("Thanh toán thất bại do không đủ số dư.");
              }
          }
      }
  
    
    private BigDecimal calculateFee(Post post) {
        BigDecimal dailyRate = "VIP".equals(post.getListingTypeCode()) ? new BigDecimal("20000") : new BigDecimal("10000");
        return dailyRate.multiply(new BigDecimal(post.getDisplayDurationDays()));
    }
    
    // ... các phương thức khác ...
}