/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.impl;

import constant.TransactionType; 
import dao.TransactionDao;
import dao.UserDao;
import java.math.BigDecimal;
import model.Transaction;
import model.User;
import service.PaymentService;

public class PaymentServiceImpl implements PaymentService {

    private final UserDao userDao = new UserDao();
    private final TransactionDao transactionDao = new TransactionDao();

    @Override
    public boolean processSimulatedRecharge(User user, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Recharge amount must be positive.");
        }

        // Cập nhật số dư của User
        boolean balanceUpdated = userDao.updateUserBalance(user.getId(), amount);

        if (balanceUpdated) {
            // Tạo một bản ghi giao dịch
            Transaction tx = new Transaction();
            tx.setUserId(user.getId());
            tx.setTransactionType(TransactionType.SIMULATED_RECHARGE);
            tx.setAmount(amount);
            tx.setDescription("User simulated recharge");
            tx.setStatus("COMPLETED"); // Giao dịch mô phỏng luôn thành công
            return transactionDao.createTransaction(tx);
        }
        return false;
    }

    @Override
    public boolean chargeForListing(User user, BigDecimal fee, long postId) {
        // Kiểm tra số dư trước khi trừ
        if (user.getAccountBalance().compareTo(fee) < 0) {
            // Không đủ tiền
            return false;
        }

        // Trừ tiền (số tiền là âm)
        boolean balanceUpdated = userDao.updateUserBalance(user.getId(), fee.negate());
        
        if (balanceUpdated) {
            // Ghi lại giao dịch thanh toán
            Transaction tx = new Transaction();
            tx.setUserId(user.getId());
            tx.setTransactionType(TransactionType.LISTING_FEE_PAYMENT);
            tx.setAmount(fee.negate()); // Ghi lại số tiền bị trừ
            tx.setDescription("Payment for Post ID: " + postId);
            tx.setStatus("COMPLETED");
            tx.setRelatedPostId(postId);
            return transactionDao.createTransaction(tx);
        }
        return false;
    }

    @Override
    public boolean refundListingFee(User user, BigDecimal amount, long postId, String reason) {
        // Hoàn tiền (số tiền là dương)
        boolean balanceUpdated = userDao.updateUserBalance(user.getId(), amount);
        
        if (balanceUpdated) {
            // Ghi lại giao dịch hoàn tiền
            Transaction tx = new Transaction();
            tx.setUserId(user.getId());
            tx.setTransactionType(TransactionType.LISTING_FEE_REFUND);
            tx.setAmount(amount);
            tx.setDescription(reason + " for Post ID: " + postId);
            tx.setStatus("COMPLETED");
            tx.setRelatedPostId(postId);
            return transactionDao.createTransaction(tx);
        }
        return false;
    }
    
    @Override
    public boolean chargeForMembership(User user, BigDecimal fee, String description, long userMembershipId) {
        if (user.getAccountBalance().compareTo(fee) < 0) {
            throw new IllegalStateException("Insufficient balance to purchase membership.");
        }
        
        boolean balanceUpdated = userDao.updateUserBalance(user.getId(), fee.negate());
        
        if(balanceUpdated) {
            Transaction tx = new Transaction();
            tx.setUserId(user.getId());
            // Loại giao dịch có thể là PURCHASE hoặc UPGRADE tùy logic
            tx.setTransactionType(TransactionType.MEMBERSHIP_PURCHASE); 
            tx.setAmount(fee.negate());
            tx.setDescription(description);
            tx.setStatus("COMPLETED");
            tx.setRelatedMembershipId(userMembershipId); // Cần thêm trường này vào Transaction model/DAO
            return transactionDao.createTransaction(tx);
        }
        return false;
    }
    
        
}
