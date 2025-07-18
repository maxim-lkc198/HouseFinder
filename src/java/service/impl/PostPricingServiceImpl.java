package service.impl;

import dao.PostPricingDao;
import model.PostPricing;
import service.PostPricingService;
import java.math.BigDecimal;
import java.util.List;

public class PostPricingServiceImpl implements PostPricingService {
    private final PostPricingDao pricingDao = new PostPricingDao();

    @Override
    public List<PostPricing> getAllPricing() {
        List<PostPricing> pricingList = pricingDao.getAllPricing();
        System.out.println("PostPricingServiceImpl: Retrieved pricing list: " + pricingList);
        return pricingList;
    }

    @Override
    public BigDecimal calculateTotalCost(String postType, int durationDays) {
        PostPricing pricing = pricingDao.getPricingByTypeAndDuration(postType, durationDays);
        if (pricing == null) {
            System.err.println("PostPricingServiceImpl: No pricing found for postType=" + postType + ", durationDays=" + durationDays);
            return BigDecimal.ZERO;
        }
        BigDecimal pricePerDay = BigDecimal.valueOf(pricing.getPricePerDay());
        BigDecimal discount = BigDecimal.valueOf(pricing.getDiscountPercentage()).divide(BigDecimal.valueOf(100));
        BigDecimal totalCost = pricePerDay.multiply(BigDecimal.valueOf(durationDays));
        totalCost = totalCost.multiply(BigDecimal.ONE.subtract(discount));
        System.out.println("PostPricingServiceImpl: Calculated total cost: " + totalCost + " for postType=" + postType + ", durationDays=" + durationDays);
        return totalCost;
    }

    @Override
    public PostPricing getPricing(String postType, int durationDays) {
        PostPricing pricing = pricingDao.getPricingByTypeAndDuration(postType, durationDays);
        if (pricing == null) {
            System.err.println("PostPricingServiceImpl: No pricing found for postType=" + postType + ", durationDays=" + durationDays);
        }
        return pricing;
    }
}