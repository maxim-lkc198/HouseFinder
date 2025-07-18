package dao;

import model.Post;
import util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostDao {

    public long createPost(Post post) {
        String sql = "INSERT INTO posts (title, description, address_province_id, address_district_id, address_ward_id, " +
                     "address_street, address_detail, contact_name, contact_phone, contact_email, " +
                     "price, area, bedrooms, bathrooms, floors, house_direction, balcony_direction, " +
                     "furniture, amenities, safety, availability_info, electricity_cost, water_cost, " +
                     "internet_cost, legal_status, listing_type_code, display_duration_days, " +
                     "pricing_id, status, source_type, user_id, category_id) " +
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

    public List<Post> getPendingPosts(int page, int pageSize) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.* FROM posts p WHERE p.status = 'PENDING_APPROVAL' " +
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

    public Post getPostById(long postId) {
        String sql = "SELECT p.*, a.username as author_username, c.name as category_name, pr.name as province_name, " +
                     "d.name as district_name, w.name as ward_name " +
                     "FROM posts p " +
                     "JOIN accounts a ON p.user_id = a.id " +
                     "JOIN categories c ON p.category_id = c.id " +
                     "JOIN provinces pr ON p.address_province_id = pr.id " +
                     "JOIN districts d ON p.address_district_id = d.id " +
                     "JOIN wards w ON p.address_ward_id = w.id " +
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

    private void setPostParameters(PreparedStatement ps, Post post) throws SQLException {
        ps.setString(1, post.getTitle());
        ps.setString(2, post.getDescription());
        ps.setInt(3, post.getAddressProvinceId());
        ps.setInt(4, post.getAddressDistrictId());
        ps.setInt(5, post.getAddressWardId());
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
        ps.setInt(28, post.getPricingId());
        ps.setString(29, post.getStatus());
        ps.setString(30, post.getSourceType());
        ps.setLong(31, post.getUserId());
        ps.setInt(32, post.getCategoryId());
    }

    private Post mapRowToPost(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong("id"));
        post.setTitle(rs.getString("title"));
        post.setDescription(rs.getString("description"));
        post.setAddressProvinceId(rs.getInt("address_province_id"));
        post.setAddressDistrictId(rs.getInt("address_district_id"));
        post.setAddressWardId(rs.getInt("address_ward_id"));
        post.setAddressStreet(rs.getString("address_street"));
        post.setAddressDetail(rs.getString("address_detail"));
        post.setContactName(rs.getString("contact_name"));
        post.setContactPhone(rs.getString("contact_phone"));
        post.setContactEmail(rs.getString("contact_email"));
        post.setPrice(rs.getBigDecimal("price"));
        post.setArea(rs.getFloat("area"));
        post.setBedrooms(rs.getObject("bedrooms") != null ? rs.getInt("bedrooms") : null);
        post.setBathrooms(rs.getObject("bathrooms") != null ? rs.getInt("bathrooms") : null);
        post.setFloors(rs.getObject("floors") != null ? rs.getInt("floors") : null);
        post.setHouseDirection(rs.getString("house_direction"));
        post.setBalconyDirection(rs.getString("balcony_direction"));
        post.setFurniture(rs.getString("furniture"));
        post.setAmenities(rs.getString("amenities"));
        post.setSafety(rs.getString("safety"));
        post.setAvailabilityInfo(rs.getString("availability_info"));
        post.setElectricityCost(rs.getString("electricity_cost"));
        post.setWaterCost(rs.getString("water_cost"));
        post.setInternetCost(rs.getString("internet_cost"));
        post.setLegalStatus(rs.getString("legal_status"));
        post.setListingTypeCode(rs.getString("listing_type_code"));
        post.setDisplayDurationDays(rs.getInt("display_duration_days"));
        post.setPricingId(rs.getInt("pricing_id"));
        post.setStatus(rs.getString("status"));
        post.setSourceType(rs.getString("source_type"));
        post.setUserId(rs.getLong("user_id"));
        post.setCategoryId(rs.getInt("category_id"));
        post.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return post;
    }

    private Post mapRowToPostWithDetails(ResultSet rs) throws SQLException {
        Post post = mapRowToPost(rs);
        post.setUserId(rs.getLong("user_id"));
        post.setCategoryId(rs.getInt("category_id"));
        post.setAddressProvinceId(rs.getInt("address_province_id"));
        post.setAddressDistrictId(rs.getInt("address_district_id"));
        post.setAddressWardId(rs.getInt("address_ward_id"));
        return post;
    }
}