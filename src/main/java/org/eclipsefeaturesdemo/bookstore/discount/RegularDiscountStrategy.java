package org.eclipsefeaturesdemo.bookstore.discount;

public class RegularDiscountStrategy implements DiscountStrategy {

    @Override
    public double applyDiscount(double subtotal) {
        return subtotal;
    }
}
