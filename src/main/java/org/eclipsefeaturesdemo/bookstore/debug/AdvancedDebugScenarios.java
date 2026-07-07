package org.eclipsefeaturesdemo.bookstore.debug;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipsefeaturesdemo.bookstore.discount.PremiumDiscountStrategy;
import org.eclipsefeaturesdemo.bookstore.domain.Book;
import org.eclipsefeaturesdemo.bookstore.exception.BookNotFoundException;
import org.eclipsefeaturesdemo.bookstore.repository.BookRepository;
import org.eclipsefeaturesdemo.bookstore.repository.InMemoryBookRepository;
import org.eclipsefeaturesdemo.bookstore.service.BookSearchService;
import org.eclipsefeaturesdemo.bookstore.service.CatalogService;
import org.eclipsefeaturesdemo.bookstore.service.CheckoutService;
import org.eclipsefeaturesdemo.bookstore.service.PricingService;

public final class AdvancedDebugScenarios {

    private AdvancedDebugScenarios() {
    }

    public static void main(String[] args) {
        String scenario = args.length == 0 ? "conditional" : args[0];

        switch (scenario) {
            case "caught-exception" -> runCaughtExceptionScenario();
            case "uncaught-exception" -> runUncaughtExceptionScenario();
            case "conditional" -> runConditionalBreakpointScenario();
            case "logical-structure" -> runLogicalStructureScenario();
            case "drop-to-frame" -> runDropToFrameScenario();
            case "step-filtering" -> runStepFilteringScenario();
            default -> printUsage(scenario);
        }
    }

    private static void runCaughtExceptionScenario() {
        CheckoutService checkoutService = createCheckoutService(createSampleRepository());

        try {
            checkoutService.checkoutBook("ISBN-NOT-FOUND", 1);
        } catch (BookNotFoundException exception) {
            System.out.println("Handled: " + exception.getMessage());
        }
    }

    private static void runUncaughtExceptionScenario() {
        CheckoutService checkoutService = createCheckoutService(createSampleRepository());
        checkoutService.checkoutBook("9780134685991", 20);
    }

    private static void runConditionalBreakpointScenario() {
        Book book = new Book("9780134685991", "Effective Java", "Joshua Bloch", 50.00, 1000);

        for (int orderNumber = 1; orderNumber <= 100; orderNumber++) {
            String orderId = "ORDER-%03d".formatted(orderNumber);
            int quantity = orderNumber == 75 ? 8 : 1;
            double subtotal = processOrder(orderId, book, quantity);

            if (orderNumber % 25 == 0) {
                System.out.printf("Processed %s with subtotal $%.2f%n", orderId, subtotal);
            }
        }
    }

    private static double processOrder(String orderId, Book book, int quantity) {
        double subtotal = book.getPrice() * quantity;
        return subtotal;
    }

    private static void runLogicalStructureScenario() {
        BookRepository bookRepository = createSampleRepository();
        List<Book> books = bookRepository.getAllBooks();

        Map<String, Book> booksByIsbn = new LinkedHashMap<>();
        for (Book book : books) {
            booksByIsbn.put(book.getIsbn(), book);
        }

        Optional<Book> selectedBook = bookRepository.findByIsbn("9781617294945");

        System.out.printf(
                "List size: %d, map size: %d, selected: %s%n",
                books.size(),
                booksByIsbn.size(),
                selectedBook.map(Book::getTitle).orElse("none"));
    }

    private static void runDropToFrameScenario() {
        Book book = new Book("9780134685991", "Effective Java", "Joshua Bloch", 50.00, 4);
        PricingService pricingService = new PricingService(new PremiumDiscountStrategy());

        int quantity = 2;
        double subtotal = pricingService.calculateSubtotal(book, quantity);
        double finalPrice = pricingService.calculateFinalPrice(subtotal);
        double discountAmount = subtotal - finalPrice;

        System.out.printf(
                "Subtotal: $%.2f, discount: $%.2f, final price: $%.2f%n",
                subtotal,
                discountAmount,
                finalPrice);
    }

    private static void runStepFilteringScenario() {
        CatalogService catalogService = new CatalogService(createSampleRepository());
        List<Book> sortedBooks = catalogService.getBooksSortedByTitle();

        for (Book book : sortedBooks) {
            System.out.println(book.getTitle());
        }
    }

    private static CheckoutService createCheckoutService(BookRepository bookRepository) {
        BookSearchService searchService = new BookSearchService(bookRepository);
        PricingService pricingService = new PricingService(new PremiumDiscountStrategy());
        return new CheckoutService(searchService, pricingService);
    }

    private static BookRepository createSampleRepository() {
        BookRepository bookRepository = new InMemoryBookRepository();
        bookRepository.add(new Book("9780134685991", "Effective Java", "Joshua Bloch", 50.00, 4));
        bookRepository.add(new Book("9781617294945", "Spring in Action", "Craig Walls", 45.00, 3));
        bookRepository.add(new Book("9781492078005", "Java Performance", "Scott Oaks", 55.00, 0));
        return bookRepository;
    }

    private static void printUsage(String unknownScenario) {
        System.err.println("Unknown scenario: " + unknownScenario);
        System.err.println(
                "Use caught-exception, uncaught-exception, conditional, "
                        + "logical-structure, drop-to-frame, or step-filtering");
    }
}
