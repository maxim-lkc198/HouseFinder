package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Role;
import model.User;
import util.DBContext;
import util.PasswordUtil;

public class UserDao {

    // --- Các phương thức tìm kiếm ---

    /**
     * Tìm một user theo ID.
     * @param userId ID của user.
     * @return đối tượng User, hoặc null nếu không tìm thấy.
     */
    public User getUserById(long userId) {
        String sql = "SELECT u.*, r.name as role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUserWithRole(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tìm một user theo username.
     * @param username Tên đăng nhập.
     * @return đối tượng User, hoặc null nếu không tìm thấy.
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT u.*, r.name as role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.username = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUserWithRole(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Tìm một user theo email.
     * @param email Email.
     * @return đối tượng User, hoặc null nếu không tìm thấy.
     */
    public User getUserByEmail(String email) {
        String sql = "SELECT u.*, r.name as role_name FROM users u JOIN roles r ON u.role_id = r.id WHERE u.email = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUserWithRole(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy tất cả các user (dùng cho Admin).
     * @return danh sách các đối tượng User.
     */
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT u.*, r.name as role_name FROM users u JOIN roles r ON u.role_id = r.id ORDER BY u.created_at DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                userList.add(mapRowToUserWithRole(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    // --- Các phương thức tạo và cập nhật ---

    /**
     * Tạo một user mới.
     * @param user Đối tượng User chứa thông tin. Mật khẩu sẽ được hash trước khi lưu.
     * @return true nếu tạo thành công, false nếu thất bại.
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, email, password, first_name, last_name, phone_number, dob, address, role_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, PasswordUtil.hashPassword(user.getPassword())); // Hash mật khẩu
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getPhoneNumber());
            ps.setDate(7, user.getDob());
            ps.setString(8, user.getAddress());
            ps.setInt(9, user.getRole().getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật thông tin profile của user.
     * @param user Đối tượng User chứa thông tin mới.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, phone_number = ?, dob = ?, address = ?, avatar_url = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhoneNumber());
            ps.setDate(4, user.getDob());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getAvatarUrl());
            ps.setLong(7, user.getId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Cập nhật thông tin KYC của user.
     * @param user Đối tượng User chứa thông tin KYC mới.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean updateUserVerificationInfo(User user) {
        String sql = "UPDATE users SET id_card_number = ?, id_card_issue_date = ?, id_card_issue_place = ?, " +
                     "id_card_front_url = ?, id_card_back_url = ?, verification_status = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getIdCardNumber());
            ps.setDate(2, user.getIdCardIssueDate());
            ps.setString(3, user.getIdCardIssuePlace());
            ps.setString(4, user.getIdCardFrontUrl());
            ps.setString(5, user.getIdCardBackUrl());
            ps.setString(6, user.getVerificationStatus());
            ps.setLong(7, user.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật mật khẩu mới cho user.
     * @param userId ID của user.
     * @param newHashedPassword Mật khẩu mới đã được hash.
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean updateUserPassword(long userId, String newHashedPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHashedPassword);
            ps.setLong(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật số dư tài khoản cho user.
     * @param userId ID của user.
     * @param amount Số tiền cần thay đổi (có thể âm để trừ tiền).
     * @return true nếu cập nhật thành công, false nếu thất bại.
     */
    public boolean updateUserBalance(long userId, java.math.BigDecimal amount) {
        // Nên dùng Stored Procedure để an toàn, nhưng đây là cách làm trực tiếp
        String sql = "UPDATE users SET account_balance = account_balance + ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, amount);
            ps.setLong(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --- Helper Method ---

    /**
     * Ánh xạ một dòng từ ResultSet sang đối tượng User, bao gồm cả Role.
     * @param rs ResultSet đang trỏ đến dòng cần map.
     * @return đối tượng User đã được điền thông tin.
     * @throws SQLException
     */
    private User mapRowToUserWithRole(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setDob(rs.getDate("dob"));
        user.setAddress(rs.getString("address"));
        user.setIdCardNumber(rs.getString("id_card_number"));
        user.setIdCardIssueDate(rs.getDate("id_card_issue_date"));
        user.setIdCardIssuePlace(rs.getString("id_card_issue_place"));
        user.setIdCardFrontUrl(rs.getString("id_card_front_url"));
        user.setIdCardBackUrl(rs.getString("id_card_back_url"));
        user.setVerificationStatus(rs.getString("verification_status"));
        user.setAvatarUrl(rs.getString("avatar_url"));
        user.setIsActive(rs.getBoolean("is_active"));
        user.setPasswordResetToken(rs.getString("password_reset_token"));
        Timestamp expiryTimestamp = rs.getTimestamp("password_reset_token_expiry");
        if (expiryTimestamp != null) {
            user.setPasswordResetTokenExpiry(expiryTimestamp.toLocalDateTime());
        }
        user.setAccountBalance(rs.getBigDecimal("account_balance"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        
        Role role = new Role(rs.getInt("role_id"), rs.getString("role_name"));
        user.setRole(role);
        
        return user;
    }
}