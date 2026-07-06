package org.eclipsefeaturesdemo.bookstore;

public record CheckoutResult(
        String isbn,
        String bookTitle,
        int quantity,
        double subtotal,
        double discountAmount,
        double finalPrice,
        int remainingStock) {
}
