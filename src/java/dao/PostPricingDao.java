package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.PostPricing;
import util.DBContext;

public class PostPricingDao {
    public List<PostPricing> getAllPricing() {
        List<PostPricing> pricingList = new ArrayList<>();
        String query = "SELECT id, post_type AS postType, duration_days AS durationDays, price_per_day AS pricePerDay, discount_percentage AS discountPercentage FROM post_pricing";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                PostPricing pricing = new PostPricing();
                pricing.setId(resultSet.getInt("id"));
                pricing.setPostType(resultSet.getString("postType"));
                pricing.setDurationDays(resultSet.getInt("durationDays"));
                pricing.setPricePerDay(resultSet.getBigDecimal("pricePerDay").doubleValue());
                pricing.setDiscountPercentage(resultSet.getBigDecimal("discountPercentage").doubleValue());
                pricingList.add(pricing);
            }
            System.out.println("PostPricingDao: Fetched pricing data: " + pricingList);
        } catch (SQLException e) {
            System.err.println("SQLException in getAllPricing: " + e.getMessage());
            e.printStackTrace();
        }
        return pricingList;
    }

    public PostPricing getPricingByTypeAndDuration(String postType, int durationDays) {
        PostPricing pricing = null;
        String query = "SELECT id, post_type AS postType, duration_days AS durationDays, price_per_day AS pricePerDay, discount_percentage AS discountPercentage FROM post_pricing WHERE post_type = ? AND duration_days = ?";
        try (Connection connection = DBContext.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, postType);
            statement.setInt(2, durationDays);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    pricing = new PostPricing();
                    pricing.setId(resultSet.getInt("id"));
                    pricing.setPostType(resultSet.getString("postType"));
                    pricing.setDurationDays(resultSet.getInt("durationDays"));
                    pricing.setPricePerDay(resultSet.getBigDecimal("pricePerDay").doubleValue());
                    pricing.setDiscountPercentage(resultSet.getBigDecimal("discountPercentage").doubleValue());
                }
            }
        } catch (SQLException e) {
            System.err.println("SQLException in getPricingByTypeAndDuration: " + e.getMessage());
            e.printStackTrace();
        }
        return pricing;
    }
}