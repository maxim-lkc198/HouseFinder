/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.math.BigDecimal;
import model.User;

/**
 * Service interface for handling all financial transactions.
 */
public interface PaymentService {

    /**
     * Processes a simulated recharge request from a user.
     * Updates the user's balance and creates a transaction record.
     * @param user The user performing the recharge.
     * @param amount The amount to recharge.
     * @return true if the recharge was successful.
     */
    boolean processSimulatedRecharge(User user, BigDecimal amount);

    /**
     * Charges a user's account balance for a pay-per-listing post.
     * Checks for sufficient funds before deduction.
     * @param user The user who is paying.
     * @param fee The calculated fee for the listing.
     * @param postId The ID of the post this fee is for.
     * @return true if payment is successful, false if funds are insufficient.
     */
    boolean chargeForListing(User user, BigDecimal fee, long postId);

    /**
     * Refunds the listing fee to a user's account balance.
     * This is typically called when an admin rejects a paid post.
     * @param user The user receiving the refund.
     * @param amount The amount to be refunded.
     * @param postId The ID of the post associated with the refund.
     * @param reason A description for the refund transaction.
     * @return true if the refund was successful.
     */
    boolean refundListingFee(User user, BigDecimal amount, long postId, String reason);
    
    /**
     * Charges a user's account balance for a membership purchase or upgrade.
     * @param user The user who is paying.
     * @param fee The calculated fee for the membership.
     * @param description A description for the transaction (e.g., "Purchase Premium Plan").
     * @param userMembershipId The ID of the user_memberships record.
     * @return true if payment is successful.
     * @throws IllegalStateException if funds are insufficient.
     */
    boolean chargeForMembership(User user, BigDecimal fee, String description, long userMembershipId);
}