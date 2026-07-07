package org.eclipsefeaturesdemo.bookstore.app;

import org.eclipsefeaturesdemo.bookstore.domain.Book;
import org.eclipsefeaturesdemo.bookstore.export.BookJsonExporter;
import org.eclipsefeaturesdemo.bookstore.export.BookTextFormatter;
import org.eclipsefeaturesdemo.bookstore.repository.BookRepository;
import org.eclipsefeaturesdemo.bookstore.repository.InMemoryBookRepository;
import org.eclipsefeaturesdemo.bookstore.service.CatalogService;

public final class CatalogReportApplication {

    private CatalogReportApplication() {
    }

    public static void main(String[] args) {
        BookRepository bookRepository = createSampleRepository();
        CatalogService catalogService = new CatalogService(bookRepository);
        BookTextFormatter textFormatter = new BookTextFormatter();

        printHeading("Books sorted by title");
        for (Book book : catalogService.getBooksSortedByTitle()) {
            System.out.println(textFormatter.format(book));
        }

        printHeading("Available books");
        for (Book book : catalogService.getAvailableBooks()) {
            System.out.println(textFormatter.format(book));
        }

        printHeading("JSON catalog");
        BookJsonExporter jsonExporter = new BookJsonExporter();
        System.out.println(jsonExporter.toJson(catalogService.getAllBooks()));
    }

    private static BookRepository createSampleRepository() {
        BookRepository bookRepository = new InMemoryBookRepository();
        bookRepository.add(new Book("9780134685991", "Effective Java", "Joshua Bloch", 50.00, 4));
        bookRepository.add(new Book("9781617294945", "Spring in Action", "Craig Walls", 45.00, 3));
        bookRepository.add(new Book("9781492078005", "Java Performance", "Scott Oaks", 55.00, 0));
        return bookRepository;
    }

    private static void printHeading(String heading) {
        System.out.printf("%n=== %s ===%n", heading);
    }
}
