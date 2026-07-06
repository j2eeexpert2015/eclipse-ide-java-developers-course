package org.eclipsefeaturesdemo.bookstore;

import org.eclipsefeaturesdemo.bookstore.export.BookJsonExporter;

public final class CatalogReportApplication {

    private CatalogReportApplication() {
    }

    public static void main(String[] args) {
        BookCatalog catalog = createSampleCatalog();

        printHeading("Books sorted by title");
        for (Book book : catalog.getBooksSortedByTitle()) {
            System.out.println(book.getFormattedSummary());
        }

        printHeading("Available books");
        for (Book book : catalog.getAvailableBooks()) {
            System.out.println(book.getFormattedSummary());
        }

        printHeading("JSON catalog");
        BookJsonExporter exporter = new BookJsonExporter();
        System.out.println(exporter.toJson(catalog.getAllBooks()));
    }

    private static BookCatalog createSampleCatalog() {
        BookCatalog catalog = new BookCatalog();
        catalog.add(new Book("9780134685991", "Effective Java", "Joshua Bloch", 50.00, 4));
        catalog.add(new Book("9781617294945", "Spring in Action", "Craig Walls", 45.00, 3));
        catalog.add(new Book("9781492078005", "Java Performance", "Scott Oaks", 55.00, 0));
        return catalog;
    }

    private static void printHeading(String heading) {
        System.out.printf("%n=== %s ===%n", heading);
    }
}
