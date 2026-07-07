package org.eclipsefeaturesdemo.bookstore.export;

import java.util.Objects;

import org.eclipsefeaturesdemo.bookstore.domain.Book;

public class BookTextFormatter {

    public String format(Book book) {
        Objects.requireNonNull(book, "Book cannot be null");

        return "%s by %s | ISBN: %s | Price: $%.2f | Stock: %d"
                .formatted(
                        book.getTitle(),
                        book.getAuthor(),
                        book.getIsbn(),
                        book.getPrice(),
                        book.getStockQuantity());
    }
}
