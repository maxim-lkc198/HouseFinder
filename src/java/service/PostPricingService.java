package service;

import model.PostPricing;
import java.math.BigDecimal;
import java.util.List;

public interface PostPricingService {
    List<PostPricing> getAllPricing();
    BigDecimal calculateTotalCost(String postType, int durationDays);
    PostPricing getPricing(String postType, int durationDays);
}