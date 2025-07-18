/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Province;
import util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDao {

    public List<Province> getAllProvinces() {
        List<Province> provinces = new ArrayList<>();
        String sql = "SELECT * FROM provinces ORDER BY name";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                provinces.add(mapRowToProvince(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinces;
    }

    public Province getProvinceById(int id) {
        String sql = "SELECT * FROM provinces WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToProvince(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertProvince(Province province, Connection conn) throws SQLException {
        String sql = "INSERT INTO provinces (id, name, prefix, slug) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, province.getId());
            ps.setString(2, province.getName());
            ps.setString(3, province.getPrefix());
            ps.setString(4, province.getSlug());
            return ps.executeUpdate() > 0;
        }
    }

    public void clearAll(Connection conn) throws SQLException {
        String sql = "DELETE FROM provinces";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.executeUpdate();
        }
    }

    private Province mapRowToProvince(ResultSet rs) throws SQLException {
        return new Province(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("prefix"),
            rs.getString("slug")
        );
    }
}