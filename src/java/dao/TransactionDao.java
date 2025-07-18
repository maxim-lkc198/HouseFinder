package dao;

import constant.TransactionType;
import model.Transaction;
import util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    public boolean createTransaction(Transaction transaction, Connection conn) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, transaction_type, amount, original_amount, description, status, reference_code, related_post_id, related_membership_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, transaction.getUserId());
            ps.setString(2, transaction.getTransactionType());
            ps.setBigDecimal(3, transaction.getAmount());
            ps.setBigDecimal(4, transaction.getOriginalAmount());
            ps.setString(5, transaction.getDescription());
            ps.setString(6, transaction.getStatus());
            ps.setString(7, transaction.getReferenceCode());
            if (transaction.getRelatedPostId() != null) {
                ps.setLong(8, transaction.getRelatedPostId());
            } else {
                ps.setNull(8, Types.BIGINT);
            }
            if (transaction.getRelatedMembershipId() != null) {
                ps.setLong(9, transaction.getRelatedMembershipId());
            } else {
                ps.setNull(9, Types.BIGINT);
            }
            return ps.executeUpdate() > 0;
        }
    }

    public boolean createTransaction(Transaction transaction) {
        try (Connection conn = DBContext.getConnection()) {
            return createTransaction(transaction, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Transaction> getTransactionsByUserId(long userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapRowToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    private Transaction mapRowToTransaction(ResultSet rs) throws SQLException {
        Transaction tx = new Transaction();
        tx.setId(rs.getLong("id"));
        tx.setUserId(rs.getLong("user_id"));
        tx.setTransactionType(rs.getString("transaction_type"));
        tx.setAmount(rs.getBigDecimal("amount"));
        tx.setOriginalAmount(rs.getBigDecimal("original_amount"));
        tx.setDescription(rs.getString("description"));
        tx.setStatus(rs.getString("status"));
        tx.setReferenceCode(rs.getString("reference_code"));
        long relatedPostId = rs.getLong("related_post_id");
        if (!rs.wasNull()) {
            tx.setRelatedPostId(relatedPostId);
        }
        long relatedMembershipId = rs.getLong("related_membership_id");
        if (!rs.wasNull()) {
            tx.setRelatedMembershipId(relatedMembershipId);
        }
        tx.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return tx;
    }
}