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
import model.Image;
import util.DBContext;

public class ImageDao {

    /**
     * Lưu một danh sách các ảnh cho một bài đăng (sử dụng batch update).
     * @param images Danh sách các đối tượng Image.
     * @return true nếu tất cả được lưu thành công.
     */
    public boolean createImages(List<Image> images) {
        if (images == null || images.isEmpty()) {
            return true; // Không có gì để làm
        }
        String sql = "INSERT INTO images (post_id, image_url, cloudinary_public_id, is_thumbnail) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            for (Image image : images) {
                ps.setLong(1, image.getPostId());
                ps.setString(2, image.getImageUrl());
                ps.setString(3, image.getCloudinaryPublicId());
                ps.setBoolean(4, image.isIsThumbnail());
                ps.addBatch();
            }
            
            int[] results = ps.executeBatch();
            
            for (int result : results) {
                if (result == PreparedStatement.EXECUTE_FAILED) {
                    // Cần xử lý rollback transaction ở tầng Service nếu có lỗi
                    return false; 
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy tất cả các ảnh của một bài đăng.
     * @param postId ID của bài đăng.
     * @return Danh sách các đối tượng Image.
     */
    public List<Image> getImagesByPostId(long postId) {
        List<Image> images = new ArrayList<>();
        String sql = "SELECT * FROM images WHERE post_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, postId);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    Image img = new Image();
                    img.setId(rs.getLong("id"));
                    img.setPostId(rs.getLong("post_id"));
                    img.setImageUrl(rs.getString("image_url"));
                    img.setCloudinaryPublicId(rs.getString("cloudinary_public_id"));
                    img.setIsThumbnail(rs.getBoolean("is_thumbnail"));
                    images.add(img);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }
    
    /**
     * Xóa tất cả các ảnh liên quan đến một bài đăng.
     * @param postId ID của bài đăng.
     * @return true nếu xóa thành công.
     */
    public boolean deleteImagesByPostId(long postId) {
        String sql = "DELETE FROM images WHERE post_id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, postId);
            ps.executeUpdate();
            return true; // Giả sử thành công nếu không có exception
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}