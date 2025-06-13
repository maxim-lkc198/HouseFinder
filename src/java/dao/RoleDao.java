/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Role;
import util.DBContext;

public class RoleDao {

    /**
     * Gets a Role object from the database by its name.
     * @param roleName The name of the role (e.g., "ROLE_USER").
     * @return A Role object if found, otherwise null.
     */
    public Role getRoleByName(String roleName) {
        String sql = "SELECT id, name FROM roles WHERE name = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roleName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    return new Role(id, name);
                }
            }
        } catch (SQLException e) {
            System.err.println("RoleDao - getRoleByName Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}