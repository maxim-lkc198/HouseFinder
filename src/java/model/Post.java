/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private long id;
    private String title;
    private String description;
    private int addressProvinceId;
    private String addressDistrict;
    private String addressWard;
    private String addressStreet;
    private String addressDetail;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private BigDecimal price;
    private float area;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer floors;
    private String houseDirection;
    private String balconyDirection;
    private String furniture; 
    private String amenities; 
    private String safety;    
    private String availabilityInfo;
    private String electricityCost;
    private String waterCost;
    private String internetCost;
    private String legalStatus;
    private String listingTypeCode; // NORMAL, VIP
    private int displayDurationDays;
    private String status;
    private String hiddenBy;
    private String sourceType;
    private int resubmitCount;
    private long userId;
    private int categoryId;
    private LocalDateTime pushedAt;
    private LocalDateTime pushExpiryAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long approvedByAdminId;
    private LocalDateTime approvedAt;
    private LocalDateTime displayStartDate;
    private LocalDateTime displayExpiryDate;
    private String rejectionReason;

    // For holding related objects after joining
    private User author;
    private Category category;
    private Province province;
    private List<Image> images;

    public Post() {
    }
    
    public Post(long id, String title, String description, int addressProvinceId, String addressDistrict, String addressWard, String addressStreet, String addressDetail, String contactName, String contactPhone, String contactEmail, BigDecimal price, float area, Integer bedrooms, Integer bathrooms, Integer floors, String houseDirection, String balconyDirection, String furniture, String amenities, String safety, String availabilityInfo, String electricityCost, String waterCost, String internetCost, String legalStatus, String listingTypeCode, int displayDurationDays, String status, String hiddenBy, String sourceType, int resubmitCount, long userId, int categoryId, LocalDateTime pushedAt, LocalDateTime pushExpiryAt, LocalDateTime createdAt, LocalDateTime updatedAt, Long approvedByAdminId, LocalDateTime approvedAt, LocalDateTime displayStartDate, LocalDateTime displayExpiryDate, String rejectionReason, User author, Category category, Province province, List<Image> images) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.addressProvinceId = addressProvinceId;
        this.addressDistrict = addressDistrict;
        this.addressWard = addressWard;
        this.addressStreet = addressStreet;
        this.addressDetail = addressDetail;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.price = price;
        this.area = area;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.floors = floors;
        this.houseDirection = houseDirection;
        this.balconyDirection = balconyDirection;
        this.furniture = furniture;
        this.amenities = amenities;
        this.safety = safety;
        this.availabilityInfo = availabilityInfo;
        this.electricityCost = electricityCost;
        this.waterCost = waterCost;
        this.internetCost = internetCost;
        this.legalStatus = legalStatus;
        this.listingTypeCode = listingTypeCode;
        this.displayDurationDays = displayDurationDays;
        this.status = status;
        this.hiddenBy = hiddenBy;
        this.sourceType = sourceType;
        this.resubmitCount = resubmitCount;
        this.userId = userId;
        this.categoryId = categoryId;
        this.pushedAt = pushedAt;
        this.pushExpiryAt = pushExpiryAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.approvedByAdminId = approvedByAdminId;
        this.approvedAt = approvedAt;
        this.displayStartDate = displayStartDate;
        this.displayExpiryDate = displayExpiryDate;
        this.rejectionReason = rejectionReason;
        this.author = author;
        this.category = category;
        this.province = province;
        this.images = images;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAddressProvinceId() {
        return addressProvinceId;
    }

    public void setAddressProvinceId(int addressProvinceId) {
        this.addressProvinceId = addressProvinceId;
    }

    public String getAddressDistrict() {
        return addressDistrict;
    }

    public void setAddressDistrict(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }

    public String getAddressWard() {
        return addressWard;
    }

    public void setAddressWard(String addressWard) {
        this.addressWard = addressWard;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public Integer getFloors() {
        return floors;
    }

    public void setFloors(Integer floors) {
        this.floors = floors;
    }

    public String getHouseDirection() {
        return houseDirection;
    }

    public void setHouseDirection(String houseDirection) {
        this.houseDirection = houseDirection;
    }

    public String getBalconyDirection() {
        return balconyDirection;
    }

    public void setBalconyDirection(String balconyDirection) {
        this.balconyDirection = balconyDirection;
    }

    public String getFurniture() {
        return furniture;
    }

    public void setFurniture(String furniture) {
        this.furniture = furniture;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public String getSafety() {
        return safety;
    }

    public void setSafety(String safety) {
        this.safety = safety;
    }

    public String getAvailabilityInfo() {
        return availabilityInfo;
    }

    public void setAvailabilityInfo(String availabilityInfo) {
        this.availabilityInfo = availabilityInfo;
    }

    public String getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(String electricityCost) {
        this.electricityCost = electricityCost;
    }

    public String getWaterCost() {
        return waterCost;
    }

    public void setWaterCost(String waterCost) {
        this.waterCost = waterCost;
    }

    public String getInternetCost() {
        return internetCost;
    }

    public void setInternetCost(String internetCost) {
        this.internetCost = internetCost;
    }

    public String getLegalStatus() {
        return legalStatus;
    }

    public void setLegalStatus(String legalStatus) {
        this.legalStatus = legalStatus;
    }

    public String getListingTypeCode() {
        return listingTypeCode;
    }

    public void setListingTypeCode(String listingTypeCode) {
        this.listingTypeCode = listingTypeCode;
    }

    public int getDisplayDurationDays() {
        return displayDurationDays;
    }

    public void setDisplayDurationDays(int displayDurationDays) {
        this.displayDurationDays = displayDurationDays;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHiddenBy() {
        return hiddenBy;
    }

    public void setHiddenBy(String hiddenBy) {
        this.hiddenBy = hiddenBy;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public int getResubmitCount() {
        return resubmitCount;
    }

    public void setResubmitCount(int resubmitCount) {
        this.resubmitCount = resubmitCount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDateTime getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(LocalDateTime pushedAt) {
        this.pushedAt = pushedAt;
    }

    public LocalDateTime getPushExpiryAt() {
        return pushExpiryAt;
    }

    public void setPushExpiryAt(LocalDateTime pushExpiryAt) {
        this.pushExpiryAt = pushExpiryAt;
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

    public Long getApprovedByAdminId() {
        return approvedByAdminId;
    }

    public void setApprovedByAdminId(Long approvedByAdminId) {
        this.approvedByAdminId = approvedByAdminId;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public LocalDateTime getDisplayStartDate() {
        return displayStartDate;
    }

    public void setDisplayStartDate(LocalDateTime displayStartDate) {
        this.displayStartDate = displayStartDate;
    }

    public LocalDateTime getDisplayExpiryDate() {
        return displayExpiryDate;
    }

    public void setDisplayExpiryDate(LocalDateTime displayExpiryDate) {
        this.displayExpiryDate = displayExpiryDate;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}