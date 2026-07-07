package org.eclipsefeaturesdemo.bookstore.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipsefeaturesdemo.bookstore.domain.Book;

public class InMemoryBookRepository implements BookRepository {

    private final List<Book> books = new ArrayList<>();

    @Override
    public void add(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException(
                    "A book with ISBN %s already exists".formatted(book.getIsbn()));
        }

        books.add(book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.matchesIsbn(isbn))
                .findFirst();
    }

    @Override
    public List<Book> getAllBooks() {
        return List.copyOf(books);
    }

    @Override
    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .toList();
    }
}
