package org.eclipsefeaturesdemo.bookstore.service;

import java.util.List;

import org.eclipsefeaturesdemo.bookstore.Book;
import org.eclipsefeaturesdemo.bookstore.exception.BookNotFoundException;
import org.eclipsefeaturesdemo.bookstore.repository.BookRepository;

public class BookSearchService {

	private final BookRepository bookRepository;

	public BookSearchService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Book findBookByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
	}

	public List<Book> findAvailableBooks() {
		return bookRepository.getAvailableBooks();
	}
}