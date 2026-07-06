package org.eclipsefeaturesdemo.bookstore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.eclipsefeaturesdemo.bookstore.repository.BookRepository;


public class BookCatalog implements BookRepository {

    private final List<Book> books = new ArrayList<>();

    public void add(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (findByIsbn(book.getIsbn()).isPresent()) {
            throw new IllegalArgumentException("A book with ISBN %s already exists".formatted(book.getIsbn()));
        }
        books.add(book);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        for (Book book : books) {
            if (book.matchesIsbn(isbn)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
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

    public List<Book> getBooksSortedByTitle() {
        List<Book> sortedBooks = new ArrayList<>(books);
        sortedBooks.sort(Comparator.comparing(Book::getTitle));
        return List.copyOf(sortedBooks);
    }
}
