package constant;

public enum TransactionType {
    SIMULATED_RECHARGE,
    LISTING_FEE_PAYMENT,
    LISTING_FEE_REFUND,
    MEMBERSHIP_PURCHASE;

    @Override
    public String toString() {
        return name();
    }

    public static TransactionType fromString(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid transaction type: " + value);
        }
    }
}