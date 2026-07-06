package org.eclipsefeaturesdemo.bookstore.service;

import org.eclipsefeaturesdemo.bookstore.Book;
import org.eclipsefeaturesdemo.bookstore.discount.DiscountStrategy;

public class PricingService {

    private final DiscountStrategy discountStrategy;

    public PricingService(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculateSubtotal(Book book, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        double bookPrice = book.getPrice();
        double subtotal = bookPrice * quantity;
        return subtotal;
    }

    public double calculateFinalPrice(double subtotal) {
        if (subtotal < 0) {
            throw new IllegalArgumentException("Subtotal cannot be negative");
        }
        return discountStrategy.applyDiscount(subtotal);
    }
}
