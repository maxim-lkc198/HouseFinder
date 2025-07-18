package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private long id;
    private long userId;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal originalAmount;
    private String description;
    private String status;
    private String referenceCode;
    private Long relatedPostId;
    private Long relatedMembershipId;
    private LocalDateTime createdAt;

    public Transaction() {}

    public Transaction(long id, long userId, String transactionType, BigDecimal amount, BigDecimal originalAmount, String description, String status, String referenceCode, Long relatedPostId, Long relatedMembershipId, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.originalAmount = originalAmount;
        this.description = description;
        this.status = status;
        this.referenceCode = referenceCode;
        this.relatedPostId = relatedPostId;
        this.relatedMembershipId = relatedMembershipId;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Long getRelatedPostId() {
        return relatedPostId;
    }

    public void setRelatedPostId(Long relatedPostId) {
        this.relatedPostId = relatedPostId;
    }

    public Long getRelatedMembershipId() {
        return relatedMembershipId;
    }

    public void setRelatedMembershipId(Long relatedMembershipId) {
        this.relatedMembershipId = relatedMembershipId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}