package service;

import java.math.BigDecimal;
import model.Account;

public interface PaymentService {
    boolean processSimulatedRecharge(Account account, BigDecimal amount);
    boolean chargeForListing(Account account, BigDecimal fee, long postId);
    boolean refundListingFee(Account account, BigDecimal amount, long postId, String reason);
    boolean chargeForMembership(Account account, BigDecimal fee, String description, long userMembershipId);
}