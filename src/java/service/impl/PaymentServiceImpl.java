package service.impl;

import constant.TransactionType;
import dao.AccountDao;
import dao.TransactionDao;
import model.Account;
import model.Transaction;
import service.PaymentService;
import util.DBContext;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class PaymentServiceImpl implements PaymentService {
    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public PaymentServiceImpl() {
        accountDao = new AccountDao();
        transactionDao = new TransactionDao();
    }

    @Override
    public boolean processSimulatedRecharge(Account account, BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Số tiền nạp phải lớn hơn 0.");
        }

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            boolean balanceUpdated = accountDao.updateAccountBalance(account.getId(), amount, conn);

            if (balanceUpdated) {
                Transaction tx = new Transaction();
                tx.setUserId(account.getId());
                tx.setTransactionType(TransactionType.SIMULATED_RECHARGE.toString());
                tx.setAmount(amount);
                tx.setOriginalAmount(amount);
                tx.setDescription("Nạp tiền mô phỏng");
                tx.setStatus("COMPLETED");

                boolean transactionCreated = transactionDao.createTransaction(tx, conn);
                if (transactionCreated) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean chargeForListing(Account account, BigDecimal fee, long postId) {
        if (account.getAccountBalance().compareTo(fee) < 0) {
            return false;
        }

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            boolean balanceUpdated = accountDao.updateAccountBalance(account.getId(), fee.negate(), conn);

            if (balanceUpdated) {
                Transaction tx = new Transaction();
                tx.setUserId(account.getId());
                tx.setTransactionType(TransactionType.LISTING_FEE_PAYMENT.toString());
                tx.setAmount(fee.negate());
                tx.setOriginalAmount(fee);
                tx.setDescription("Thanh toán cho bài đăng ID: " + postId);
                tx.setStatus("COMPLETED");
                tx.setRelatedPostId(postId);

                boolean transactionCreated = transactionDao.createTransaction(tx, conn);
                if (transactionCreated) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean refundListingFee(Account account, BigDecimal amount, long postId, String reason) {
        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            boolean balanceUpdated = accountDao.updateAccountBalance(account.getId(), amount, conn);

            if (balanceUpdated) {
                Transaction tx = new Transaction();
                tx.setUserId(account.getId());
                tx.setTransactionType(TransactionType.LISTING_FEE_REFUND.toString());
                tx.setAmount(amount);
                tx.setOriginalAmount(amount);
                tx.setDescription(reason + " cho bài đăng ID: " + postId);
                tx.setStatus("COMPLETED");
                tx.setRelatedPostId(postId);

                boolean transactionCreated = transactionDao.createTransaction(tx, conn);
                if (transactionCreated) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean chargeForMembership(Account account, BigDecimal fee, String description, long userMembershipId) {
        if (account.getAccountBalance().compareTo(fee) < 0) {
            throw new IllegalStateException("Số dư không đủ để mua gói hội viên.");
        }

        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            boolean balanceUpdated = accountDao.updateAccountBalance(account.getId(), fee.negate(), conn);

            if (balanceUpdated) {
                Transaction tx = new Transaction();
                tx.setUserId(account.getId());
                tx.setTransactionType(TransactionType.MEMBERSHIP_PURCHASE.toString());
                tx.setAmount(fee.negate());
                tx.setOriginalAmount(fee);
                tx.setDescription(description);
                tx.setStatus("COMPLETED");
                tx.setRelatedMembershipId(userMembershipId);

                boolean transactionCreated = transactionDao.createTransaction(tx, conn);
                if (transactionCreated) {
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            } else {
                conn.rollback();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}