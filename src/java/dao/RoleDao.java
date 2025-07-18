package dao;

import model.Role;
import util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleDao {

    public Role getRoleByName(String roleName) {
        String sql = "SELECT * FROM roles WHERE name = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, roleName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Role(
                        rs.getInt("id"),
                        rs.getString("name")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in getRoleByName for role: " + roleName + ": " + e.getMessage());
        }
        System.err.println("Role not found: " + roleName);
        return null;
    }
}