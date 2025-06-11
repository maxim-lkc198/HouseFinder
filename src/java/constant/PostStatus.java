/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package constant;

public final class PostStatus {
    public static final String DRAFT_AWAITING_PAYMENT = "DRAFT_AWAITING_PAYMENT";
    public static final String PAID_PENDING_APPROVAL = "PAID_PENDING_APPROVAL";
    public static final String APPROVED_VISIBLE = "APPROVED_VISIBLE";
    public static final String REJECTED_CAN_RESUBMIT = "REJECTED_CAN_RESUBMIT";
    public static final String HIDDEN = "HIDDEN";
    public static final String EXPIRED_DISPLAY = "EXPIRED_DISPLAY";
    public static final String CANCELLED_MAX_RESUBMIT = "CANCELLED_MAX_RESUBMIT";
    
    private PostStatus() {} // Private constructor to prevent instantiation
}
