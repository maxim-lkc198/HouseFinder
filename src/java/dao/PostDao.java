package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Post;
import util.DBContext;

public class PostDao {

    /**
     * Tạo một bài đăng mới và trả về ID của bài đăng đó.
     * @param post Đối tượng Post chứa thông tin cần lưu.
     * @return ID của bài đăng vừa được tạo, hoặc -1 nếu thất bại.
     */
    public long createPost(Post post) {
        String sql = "INSERT INTO posts (title, description, address_province_id, address_district, address_ward, " +
                     "address_street, address_detail, contact_name, contact_phone, contact_email, " +
                     "price, area, bedrooms, bathrooms, floors, house_direction, balcony_direction, " +
                     "furniture, amenities, safety, availability_info, electricity_cost, water_cost, " +
                     "internet_cost, legal_status, listing_type_code, display_duration_days, " +
                     "status, source_type, user_id, category_id) " +
                     "OUTPUT INSERTED.id " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            setPostParameters(ps, post);
            
            try (ResultSet generatedKeys = ps.executeQuery()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    /**
     * Lấy danh sách bài đăng chờ duyệt cho Admin (phân trang).
     * @param page Số trang hiện tại (bắt đầu từ 1).
     * @param pageSize Số lượng bài trên mỗi trang.
     * @return Danh sách các đối tượng Post.
     */
    public List<Post> getPendingPosts(int page, int pageSize) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.* FROM posts p WHERE p.status = 'PENDING_APPROVAL' OR p.status = 'PAID_PENDING_APPROVAL' " +
                     "ORDER BY p.created_at DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, (page - 1) * pageSize);
            ps.setInt(2, pageSize);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    posts.add(mapRowToPost(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    /**
     * Lấy một bài đăng theo ID, bao gồm thông tin người đăng và category.
     * @param postId ID của bài đăng.
     * @return đối tượng Post, hoặc null nếu không tìm thấy.
     */
    public Post getPostById(long postId) {
        String sql = "SELECT p.*, u.username as author_username, c.name as category_name, pr.name as province_name " +
                     "FROM posts p " +
                     "JOIN users u ON p.user_id = u.id " +
                     "JOIN categories c ON p.category_id = c.id " +
                     "JOIN provinces pr ON p.address_province_id = pr.id " +
                     "WHERE p.id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToPostWithDetails(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Cập nhật trạng thái của một bài đăng (dùng cho Admin duyệt).
     * @param postId ID của bài đăng.
     * @param newStatus Trạng thái mới.
     * @param adminId ID của Admin thực hiện hành động.
     * @return true nếu cập nhật thành công.
     */
    public boolean updatePostStatusByAdmin(long postId, String newStatus, long adminId, String rejectionReason) {
        String sql = "UPDATE posts SET status = ?, rejection_reason = ?, approved_by_admin_id = ?, approved_at = GETDATE() WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, newStatus);
            ps.setString(2, rejectionReason);
            ps.setLong(3, adminId);
            ps.setLong(4, postId);
            
            return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

    public boolean updatePostStatus(long postId, String newStatus) {
    String sql = "UPDATE posts SET status = ? WHERE id = ?";
    try (Connection conn = DBContext.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, newStatus);
        ps.setLong(2, postId);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    // --- Helper Methods ---

    // Phương thức helper để set các tham số cho PreparedStatement
    private void setPostParameters(PreparedStatement ps, Post post) throws SQLException {
        ps.setString(1, post.getTitle());
        ps.setString(2, post.getDescription());
        ps.setInt(3, post.getAddressProvinceId());
        ps.setString(4, post.getAddressDistrict());
        ps.setString(5, post.getAddressWard());
        ps.setString(6, post.getAddressStreet());
        ps.setString(7, post.getAddressDetail());
        ps.setString(8, post.getContactName());
        ps.setString(9, post.getContactPhone());
        ps.setString(10, post.getContactEmail());
        ps.setBigDecimal(11, post.getPrice());
        ps.setFloat(12, post.getArea());
        ps.setObject(13, post.getBedrooms());
        ps.setObject(14, post.getBathrooms());
        ps.setObject(15, post.getFloors());
        ps.setString(16, post.getHouseDirection());
        ps.setString(17, post.getBalconyDirection());
        ps.setString(18, post.getFurniture());
        ps.setString(19, post.getAmenities());
        ps.setString(20, post.getSafety());
        ps.setString(21, post.getAvailabilityInfo());
        ps.setString(22, post.getElectricityCost());
        ps.setString(23, post.getWaterCost());
        ps.setString(24, post.getInternetCost());
        ps.setString(25, post.getLegalStatus());
        ps.setString(26, post.getListingTypeCode());
        ps.setInt(27, post.getDisplayDurationDays());
        ps.setString(28, post.getStatus());
        ps.setString(29, post.getSourceType());
        ps.setLong(30, post.getUserId());
        ps.setInt(31, post.getCategoryId());
    }

    private Post mapRowToPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        // ... map tất cả các cột từ bảng posts ...
        post.setStatus(rs.getString("status"));
        post.setUserId(rs.getLong("user_id"));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return post;
    }
    
    private Post mapRowToPostWithDetails(ResultSet rs) throws SQLException {
        Post post = mapRowToPost(rs);
        // Map thêm các thông tin từ join
        // User author = new User();
        // author.setUsername(rs.getString("author_username"));
        // post.setAuthor(author);
        // ...
        return post;
    }
}