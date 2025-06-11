package model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

public class User {
    private long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Date dob;
    private String address;
    private String idCardNumber;
    private Date idCardIssueDate;
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

    // Constructors
    public User() {}

    public User(long id, String username, String email, String password, String firstName, String lastName, String phoneNumber, Date dob, String address, String idCardNumber, Date idCardIssueDate, String idCardIssuePlace, String idCardFrontUrl, String idCardBackUrl, String verificationStatus, String avatarUrl, boolean isActive, String passwordResetToken, LocalDateTime passwordResetTokenExpiry, BigDecimal accountBalance, int roleId, Role role, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.address = address;
        this.idCardNumber = idCardNumber;
        this.idCardIssueDate = idCardIssueDate;
        this.idCardIssuePlace = idCardIssuePlace;
        this.idCardFrontUrl = idCardFrontUrl;
        this.idCardBackUrl = idCardBackUrl;
        this.verificationStatus = verificationStatus;
        this.avatarUrl = avatarUrl;
        this.isActive = isActive;
        this.passwordResetToken = passwordResetToken;
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
        this.accountBalance = accountBalance;
        this.roleId = roleId;
        this.role = role;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public Date getIdCardIssueDate() {
        return idCardIssueDate;
    }

    public void setIdCardIssueDate(Date idCardIssueDate) {
        this.idCardIssueDate = idCardIssueDate;
    }

    public String getIdCardIssuePlace() {
        return idCardIssuePlace;
    }

    public void setIdCardIssuePlace(String idCardIssuePlace) {
        this.idCardIssuePlace = idCardIssuePlace;
    }

    public String getIdCardFrontUrl() {
        return idCardFrontUrl;
    }

    public void setIdCardFrontUrl(String idCardFrontUrl) {
        this.idCardFrontUrl = idCardFrontUrl;
    }

    public String getIdCardBackUrl() {
        return idCardBackUrl;
    }

    public void setIdCardBackUrl(String idCardBackUrl) {
        this.idCardBackUrl = idCardBackUrl;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public LocalDateTime getPasswordResetTokenExpiry() {
        return passwordResetTokenExpiry;
    }

    public void setPasswordResetTokenExpiry(LocalDateTime passwordResetTokenExpiry) {
        this.passwordResetTokenExpiry = passwordResetTokenExpiry;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

  
}