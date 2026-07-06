package org.eclipsefeaturesdemo.bookstore.exception;

public class InsufficientStockException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String isbn;
    private final int requestedQuantity;
    private final int availableQuantity;

    public InsufficientStockException(String isbn, int requestedQuantity, int availableQuantity) {
        super("Insufficient stock for ISBN %s: requested=%d, available=%d"
                .formatted(isbn, requestedQuantity, availableQuantity));
        this.isbn = isbn;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getRequestedQuantity() {
        return requestedQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
