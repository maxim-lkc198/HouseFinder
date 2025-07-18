/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.District;
import util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DistrictDao {

    public List<District> getAllDistricts() {
        List<District> districts = new ArrayList<>();
        String sql = "SELECT * FROM districts ORDER BY name";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                districts.add(mapRowToDistrict(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return districts;
    }

    public List<District> getDistrictsByProvinceId(int provinceId) {
        List<District> districts = new ArrayList<>();
        String sql = "SELECT * FROM districts WHERE province_id = ? ORDER BY name";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, provinceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    districts.add(mapRowToDistrict(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return districts;
    }

    public District getDistrictById(int id) {
        String sql = "SELECT * FROM districts WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToDistrict(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertDistrict(District district, Connection conn) throws SQLException {
        String sql = "INSERT INTO districts (id, name, prefix, slug, province_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, district.getId());
            ps.setString(2, district.getName());
            ps.setString(3, district.getPrefix());
            ps.setString(4, district.getSlug());
            ps.setInt(5, district.getProvinceId());
            return ps.executeUpdate() > 0;
        }
    }

    public void clearAll(Connection conn) throws SQLException {
        String sql = "DELETE FROM districts";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    private District mapRowToDistrict(ResultSet rs) throws SQLException {
        return new District(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("prefix"),
            rs.getString("slug"),
            rs.getInt("province_id")
        );
    }
}
