package org.eclipsefeaturesdemo.bookstore.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.eclipsefeaturesdemo.bookstore.domain.Book;
import org.eclipsefeaturesdemo.bookstore.repository.BookRepository;

public class CatalogService {

    private final BookRepository bookRepository;

    public CatalogService(BookRepository bookRepository) {
        this.bookRepository = Objects.requireNonNull(
                bookRepository,
                "Book repository cannot be null");
    }

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public List<Book> getAvailableBooks() {
        return bookRepository.getAvailableBooks();
    }

    public List<Book> getBooksSortedByTitle() {
        return bookRepository.getAllBooks()
                .stream()
                .sorted(Comparator.comparing(Book::getTitle))
                .toList();
    }
}
