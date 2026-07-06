package org.eclipsefeaturesdemo.bookstore.service;

import org.eclipsefeaturesdemo.bookstore.Book;
import org.eclipsefeaturesdemo.bookstore.CheckoutResult;
import org.eclipsefeaturesdemo.bookstore.exception.InsufficientStockException;

public class CheckoutService {

    private final BookSearchService bookSearchService;
    private final PricingService pricingService;

    public CheckoutService(BookSearchService bookSearchService, PricingService pricingService) {
        this.bookSearchService = bookSearchService;
        this.pricingService = pricingService;
    }

    public CheckoutResult checkoutBook(String isbn, int quantity) {
        Book book = bookSearchService.findBookByIsbn(isbn);

        if (!book.hasEnoughStock(quantity)) {
            throw new InsufficientStockException(isbn, quantity, book.getStockQuantity());
        }

        double subtotal = pricingService.calculateSubtotal(book, quantity);
        double finalPrice = pricingService.calculateFinalPrice(subtotal);
        double discountAmount = subtotal - finalPrice;
        int stockBeforeCheckout = book.getStockQuantity();

        book.reduceStock(quantity);

        int remainingStock = book.getStockQuantity();
        CheckoutResult checkoutResult = new CheckoutResult(
                book.getIsbn(),
                book.getTitle(),
                quantity,
                subtotal,
                discountAmount,
                finalPrice,
                remainingStock);

        if (remainingStock != stockBeforeCheckout - quantity) {
            throw new IllegalStateException("Stock was not updated correctly");
        }

        return checkoutResult;
    }
}
