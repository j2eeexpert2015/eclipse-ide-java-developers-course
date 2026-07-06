package org.eclipsefeaturesdemo.bookstore.exception;

public class BookNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String isbn;

    public BookNotFoundException(String isbn) {
        super("No book was found for ISBN: " + isbn);
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }
}
