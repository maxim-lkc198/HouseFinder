/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Ward;
import util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WardDao {

    public List<Ward> getAllWards() {
        List<Ward> wards = new ArrayList<>();
        String sql = "SELECT * FROM wards ORDER BY name";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                wards.add(mapRowToWard(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wards;
    }

    public List<Ward> getWardsByDistrictId(int districtId) {
        List<Ward> wards = new ArrayList<>();
        String sql = "SELECT * FROM wards WHERE district_id = ? ORDER BY name";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, districtId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    wards.add(mapRowToWard(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wards;
    }

    public Ward getWardById(int id) {
        String sql = "SELECT * FROM wards WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToWard(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertWard(Ward ward, Connection conn) throws SQLException {
        String sql = "INSERT INTO wards (id, name, prefix, slug, district_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ward.getId());
            ps.setString(2, ward.getName());
            ps.setString(3, ward.getPrefix());
            ps.setString(4, ward.getSlug());
            ps.setInt(5, ward.getDistrictId());
            return ps.executeUpdate() > 0;
        }
    }

    public void clearAll(Connection conn) throws SQLException {
        String sql = "DELETE FROM wards";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    private Ward mapRowToWard(ResultSet rs) throws SQLException {
        return new Ward(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("prefix"),
            rs.getString("slug"),
            rs.getInt("district_id")
        );
    }
}
