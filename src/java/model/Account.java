package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Account {
    private long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dob;
    private String address;
    private String idCardNumber;
    private LocalDate idCardIssueDate;
    private String idCardIssuePlace;
    private String idCardFrontUrl;
    private String idCardBackUrl;
    private String verificationStatus;
    private String avatarUrl;
    private boolean isActive;
    private String passwordResetToken;
    private LocalDateTime passwordResetTokenExpiry;
    private BigDecimal accountBalance;
    private int roleId;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getIdCardNumber() { return idCardNumber; }
    public void setIdCardNumber(String idCardNumber) { this.idCardNumber = idCardNumber; }
    public LocalDate getIdCardIssueDate() { return idCardIssueDate; }
    public void setIdCardIssueDate(LocalDate idCardIssueDate) { this.idCardIssueDate = idCardIssueDate; }
    public String getIdCardIssuePlace() { return idCardIssuePlace; }
    public void setIdCardIssuePlace(String idCardIssuePlace) { this.idCardIssuePlace = idCardIssuePlace; }
    public String getIdCardFrontUrl() { return idCardFrontUrl; }
    public void setIdCardFrontUrl(String idCardFrontUrl) { this.idCardFrontUrl = idCardFrontUrl; }
    public String getIdCardBackUrl() { return idCardBackUrl; }
    public void setIdCardBackUrl(String idCardBackUrl) { this.idCardBackUrl = idCardBackUrl; }
    public String getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(String verificationStatus) { this.verificationStatus = verificationStatus; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public boolean isActive() {
        System.out.println("Checking isActive for account ID " + id + ": " + isActive);
        return isActive;
    }
    public void setActive(boolean isActive) { this.isActive = isActive; }
    public String getPasswordResetToken() { return passwordResetToken; }
    public void setPasswordResetToken(String passwordResetToken) { this.passwordResetToken = passwordResetToken; }
    public LocalDateTime getPasswordResetTokenExpiry() { return passwordResetTokenExpiry; }
    public void setPasswordResetTokenExpiry(LocalDateTime passwordResetTokenExpiry) { this.passwordResetTokenExpiry = passwordResetTokenExpiry; }
    public BigDecimal getAccountBalance() { return accountBalance; }
    public void setAccountBalance(BigDecimal accountBalance) { this.accountBalance = accountBalance; }
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}