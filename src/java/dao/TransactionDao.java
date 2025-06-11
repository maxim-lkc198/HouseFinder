/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import util.DBContext;

public class TransactionDao {

    /**
     * Tạo một giao dịch mới.
     * @param transaction Đối tượng Transaction cần lưu.
     * @return true nếu thành công, false nếu thất bại.
     */
    public boolean createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions (user_id, transaction_type, amount, description, status, reference_code, related_post_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, transaction.getUserId());
            ps.setString(2, transaction.getTransactionType());
            ps.setBigDecimal(3, transaction.getAmount());
            ps.setString(4, transaction.getDescription());
            ps.setString(5, transaction.getStatus());
            ps.setString(6, transaction.getReferenceCode());
            
            if (transaction.getRelatedPostId() != null) {
                ps.setLong(7, transaction.getRelatedPostId());
            } else {
                ps.setNull(7, java.sql.Types.BIGINT);
            }
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy lịch sử giao dịch của một user.
     * @param userId ID của user.
     * @return Danh sách các đối tượng Transaction.
     */
    public List<Transaction> getTransactionsByUserId(long userId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
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
        tx.setDescription(rs.getString("description"));
        tx.setStatus(rs.getString("status"));
        tx.setReferenceCode(rs.getString("reference_code"));
        tx.setRelatedPostId(rs.getLong("related_post_id"));
        if(rs.wasNull()){
            tx.setRelatedPostId(null);
        }
        tx.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return tx;
    }
}