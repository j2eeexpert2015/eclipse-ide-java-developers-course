package org.eclipsefeaturesdemo.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.eclipsefeaturesdemo.bookstore.Book;

public interface BookRepository {

    void add(Book book);

    Optional<Book> findByIsbn(String isbn);

    List<Book> getAllBooks();

    List<Book> getAvailableBooks();
}
