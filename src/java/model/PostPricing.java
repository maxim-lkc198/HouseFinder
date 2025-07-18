package model;

public class PostPricing {
    private int id;
    private String postType;
    private int durationDays;
    private double pricePerDay;
    private double discountPercentage;

    public PostPricing() {}

    public PostPricing(int id, String postType, int durationDays, double pricePerDay, double discountPercentage) {
        this.id = id;
        this.postType = postType;
        this.durationDays = durationDays;
        this.pricePerDay = pricePerDay;
        this.discountPercentage = discountPercentage;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPostType() { return postType; }
    public void setPostType(String postType) { this.postType = postType; }
    public int getDurationDays() { return durationDays; }
    public void setDurationDays(int durationDays) { this.durationDays = durationDays; }
    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }
    public double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(double discountPercentage) { this.discountPercentage = discountPercentage; }

    @Override
    public String toString() {
        return "PostPricing{id=" + id + ", postType='" + postType + "', durationDays=" + durationDays + 
               ", pricePerDay=" + pricePerDay + ", discountPercentage=" + discountPercentage + "}";
    }
}