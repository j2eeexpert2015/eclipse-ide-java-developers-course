package org.eclipsefeaturesdemo.bookstore.domain;

import java.util.Objects;

import org.eclipsefeaturesdemo.bookstore.exception.InsufficientStockException;



public class Book {

    private final String isbn;
    private final String title;
    private final String author;
    private final double price;
    private int stockQuantity;

    public Book(String isbn, String title, String author, double price, int stockQuantity) {
        this.isbn = requireText(isbn, "ISBN");
        this.title = requireText(title, "Title");
        this.author = requireText(author, "Author");

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }

        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public boolean matchesIsbn(String requestedIsbn) {
        return isbn.equals(requestedIsbn);
    }

    public boolean isAvailable() {
        return stockQuantity > 0;
    }

    public boolean hasEnoughStock(int requestedQuantity) {
        return requestedQuantity > 0 && stockQuantity >= requestedQuantity;
    }

    public void reduceStock(int requestedQuantity) {
        if (requestedQuantity <= 0) {
            throw new IllegalArgumentException("Requested quantity must be greater than zero");
        }
        if (!hasEnoughStock(requestedQuantity)) {
            throw new InsufficientStockException(isbn, requestedQuantity, stockQuantity);
        }
        stockQuantity -= requestedQuantity;
    }

    public String getFormattedSummary() {
        return "%s by %s | ISBN: %s | Price: $%.2f | Stock: %d"
                .formatted(title, author, isbn, price, stockQuantity);
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.stockQuantity = stockQuantity;
    }

    @Override
    public String toString() {
        return getFormattedSummary();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Book other)) {
            return false;
        }
        return isbn.equals(other.isbn);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value;
    }
}
