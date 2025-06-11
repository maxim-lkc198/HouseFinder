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
import model.Province;
import util.DBContext;

public class ProvinceDao {
    
    public List<Province> getAllProvinces() {
        List<Province> provinces = new ArrayList<>();
        String sql = "SELECT id, name FROM provinces ORDER BY name ASC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Province province = new Province();
                province.setId(rs.getInt("id"));
                province.setName(rs.getString("name"));
                provinces.add(province);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return provinces;
    }
}