package dao;

import model.Account;
import model.Role;
import util.DBContext;
import util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    public Account getAccountById(long accountId) {
        String sql = "SELECT a.*, r.name as role_name FROM accounts a JOIN roles r ON a.role_id = r.id WHERE a.id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccountWithRole(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in getAccountById for ID: " + accountId + ": " + e.getMessage());
        }
        System.err.println("Account not found for ID: " + accountId);
        return null;
    }

    public Account getAccountByUsername(String username) {
        String sql = "SELECT a.*, r.name as role_name FROM accounts a JOIN roles r ON a.role_id = r.id WHERE a.username = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccountWithRole(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in getAccountByUsername for username: " + username + ": " + e.getMessage());
        }
        System.err.println("Account not found for username: " + username);
        return null;
    }

    public Account getAccountByEmail(String email) {
        String sql = "SELECT a.*, r.name as role_name FROM accounts a JOIN roles r ON a.role_id = r.id WHERE a.email = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccountWithRole(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in getAccountByEmail for email: " + email + ": " + e.getMessage());
        }
        System.err.println("Account not found for email: " + email);
        return null;
    }

    public List<Account> getAllAccounts() {
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT a.*, r.name as role_name FROM accounts a JOIN roles r ON a.role_id = r.id ORDER BY a.created_at DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                accountList.add(mapRowToAccountWithRole(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in getAllAccounts: " + e.getMessage());
        }
        return accountList;
    }

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO accounts (username, email, password, first_name, last_name, phone_number, dob, address, role_id, is_active, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), GETDATE())";
        try (Connection conn = DBContext.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                String hashedPassword = PasswordUtil.hashPassword(account.getPassword());
                ps.setString(1, account.getUsername());
                ps.setString(2, account.getEmail());
                ps.setString(3, hashedPassword);
                ps.setString(4, account.getFirstName());
                ps.setString(5, account.getLastName());
                ps.setString(6, account.getPhoneNumber());
                ps.setObject(7, account.getDob() != null ? java.sql.Date.valueOf(account.getDob()) : null);
                ps.setString(8, account.getAddress());
                ps.setInt(9, account.getRoleId());
                ps.setBoolean(10, true);
                boolean success = ps.executeUpdate() > 0;
                if (success) {
                    System.out.println("Account created successfully: " + account.getUsername() + ", hashedPassword: " + hashedPassword);
                    conn.commit();
                } else {
                    System.err.println("Failed to create account: " + account.getUsername());
                    conn.rollback();
                }
                return success;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                System.err.println("SQLException in createAccount for username: " + account.getUsername() + ": " + e.getMessage());
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in createAccount connection: " + e.getMessage());
            return false;
        }
    }

    public boolean updateAccountProfile(Account account) {
        String sql = "UPDATE accounts SET first_name = ?, last_name = ?, phone_number = ?, dob = ?, address = ?, avatar_url = ?, updated_at = GETDATE() WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getFirstName());
            ps.setString(2, account.getLastName());
            ps.setString(3, account.getPhoneNumber());
            ps.setObject(4, account.getDob() != null ? java.sql.Date.valueOf(account.getDob()) : null);
            ps.setString(5, account.getAddress());
            ps.setString(6, account.getAvatarUrl());
            ps.setLong(7, account.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in updateAccountProfile: " + e.getMessage());
            return false;
        }
    }

    public boolean updateAccountVerificationInfo(Account account) {
        String sql = "UPDATE accounts SET id_card_number = ?, id_card_issue_date = ?, id_card_issue_place = ?, " +
                     "id_card_front_url = ?, id_card_back_url = ?, verification_status = ?, updated_at = GETDATE() WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.getIdCardNumber());
            ps.setObject(2, account.getIdCardIssueDate() != null ? java.sql.Date.valueOf(account.getIdCardIssueDate()) : null);
            ps.setString(3, account.getIdCardIssuePlace());
            ps.setString(4, account.getIdCardFrontUrl());
            ps.setString(5, account.getIdCardBackUrl());
            ps.setString(6, account.getVerificationStatus());
            ps.setLong(7, account.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in updateAccountVerificationInfo: " + e.getMessage());
            return false;
        }
    }

    public boolean updateAccountPassword(long accountId, String newHashedPassword) {
        String sql = "UPDATE accounts SET password = ?, updated_at = GETDATE() WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHashedPassword);
            ps.setLong(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in updateAccountPassword: " + e.getMessage());
            return false;
        }
    }

    public boolean updateAccountBalance(long accountId, java.math.BigDecimal amount, Connection conn) throws SQLException {
        String sql = "UPDATE accounts SET account_balance = account_balance + ?, updated_at = GETDATE() WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBigDecimal(1, amount);
            ps.setLong(2, accountId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean savePasswordResetToken(long accountId, String token, LocalDateTime expiryDate) {
        String sql = "UPDATE accounts SET password_reset_token = ?, password_reset_token_expiry = ?, updated_at = GETDATE() WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setTimestamp(2, expiryDate != null ? Timestamp.valueOf(expiryDate) : null);
            ps.setLong(3, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in savePasswordResetToken: " + e.getMessage());
            return false;
        }
    }

    public Account getAccountByPasswordResetToken(String token) {
        String sql = "SELECT a.*, r.name as role_name FROM accounts a JOIN roles r ON a.role_id = r.id WHERE a.password_reset_token = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToAccountWithRole(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in getAccountByPasswordResetToken: " + e.getMessage());
        }
        System.err.println("Account not found for password reset token: " + token);
        return null;
    }

    public boolean updatePasswordAndInvalidateToken(long accountId, String newHashedPassword) {
        String sql = "UPDATE accounts SET password = ?, password_reset_token = NULL, password_reset_token_expiry = NULL, updated_at = GETDATE() WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHashedPassword);
            ps.setLong(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("SQLException in updatePasswordAndInvalidateToken: " + e.getMessage());
            return false;
        }
    }

    private Account mapRowToAccountWithRole(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setId(rs.getLong("id"));
        account.setUsername(rs.getString("username"));
        account.setEmail(rs.getString("email"));
        account.setPassword(rs.getString("password"));
        account.setFirstName(rs.getString("first_name"));
        account.setLastName(rs.getString("last_name"));
        account.setPhoneNumber(rs.getString("phone_number"));
        account.setDob(rs.getDate("dob") != null ? rs.getDate("dob").toLocalDate() : null);
        account.setAddress(rs.getString("address"));
        account.setIdCardNumber(rs.getString("id_card_number"));
        account.setIdCardIssueDate(rs.getDate("id_card_issue_date") != null ? rs.getDate("id_card_issue_date").toLocalDate() : null);
        account.setIdCardIssuePlace(rs.getString("id_card_issue_place"));
        account.setIdCardFrontUrl(rs.getString("id_card_front_url"));
        account.setIdCardBackUrl(rs.getString("id_card_back_url"));
        account.setVerificationStatus(rs.getString("verification_status"));
        account.setAvatarUrl(rs.getString("avatar_url"));
        account.setActive(rs.getBoolean("is_active"));
        account.setPasswordResetToken(rs.getString("password_reset_token"));
        account.setPasswordResetTokenExpiry(rs.getTimestamp("password_reset_token_expiry") != null ? rs.getTimestamp("password_reset_token_expiry").toLocalDateTime() : null);
        account.setAccountBalance(rs.getBigDecimal("account_balance"));
        account.setRoleId(rs.getInt("role_id"));
        
        String roleName = rs.getString("role_name");
        account.setRole(new Role(rs.getInt("role_id"), roleName != null ? roleName : "UNKNOWN"));
        
        System.out.println("Mapped account: ID=" + account.getId() + ", username=" + account.getUsername() + ", isActive=" + account.isActive() + ", role=" + (account.getRole() != null ? account.getRole().getName() : "null"));
        return account;
    }
}