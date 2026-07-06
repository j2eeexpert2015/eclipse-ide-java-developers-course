package org.eclipsefeaturesdemo.bookstore.discount;

public class PremiumDiscountStrategy implements DiscountStrategy {

    private static final double DISCOUNT_RATE = 0.10;

    @Override
    public double applyDiscount(double subtotal) {
        double discountRate = DISCOUNT_RATE;
        double discountAmount = subtotal * discountRate;
        double finalPrice = subtotal - discountAmount;
        return finalPrice;
    }
}
