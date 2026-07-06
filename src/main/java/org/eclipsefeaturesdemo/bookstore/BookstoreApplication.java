package org.eclipsefeaturesdemo.bookstore;

import org.eclipsefeaturesdemo.bookstore.discount.PremiumDiscountStrategy;
import org.eclipsefeaturesdemo.bookstore.service.BookSearchService;
import org.eclipsefeaturesdemo.bookstore.service.CheckoutService;
import org.eclipsefeaturesdemo.bookstore.service.PricingService;

public final class BookstoreApplication {

    private static final String DEFAULT_ISBN = "9780134685991";
    private static final int DEFAULT_QUANTITY = 2;

    private BookstoreApplication() {
    }

    public static void main(String[] args) {
        String isbn = args.length > 0 ? args[0] : DEFAULT_ISBN;
        int quantity = args.length > 1 ? parseQuantity(args[1]) : DEFAULT_QUANTITY;

        runCheckoutDemo(isbn, quantity);
    }

    private static void runCheckoutDemo(String isbn, int quantity) {
        BookCatalog catalog = createSampleCatalog();
        BookSearchService bookSearchService = new BookSearchService(catalog);
        PricingService pricingService = new PricingService(new PremiumDiscountStrategy());
        CheckoutService checkoutService = new CheckoutService(bookSearchService, pricingService);

        CheckoutResult result = checkoutService.checkoutBook(isbn, quantity);
        printCheckoutResult(result);
    }

    private static BookCatalog createSampleCatalog() {
        BookCatalog catalog = new BookCatalog();
        catalog.add(new Book("9780134685991", "Effective Java", "Joshua Bloch", 50.00, 4));
        catalog.add(new Book("9781617294945", "Spring in Action", "Craig Walls", 45.00, 3));
        catalog.add(new Book("9781492078005", "Java Performance", "Scott Oaks", 55.00, 0));
        return catalog;
    }

    private static void printCheckoutResult(CheckoutResult result) {
        System.out.println("Bookstore checkout completed");
        System.out.printf("Book: %s (%s)%n", result.bookTitle(), result.isbn());
        System.out.printf("Quantity: %d%n", result.quantity());
        System.out.printf("Subtotal: $%.2f%n", result.subtotal());
        System.out.printf("Discount: $%.2f%n", result.discountAmount());
        System.out.printf("Final price: $%.2f%n", result.finalPrice());
        System.out.printf("Remaining stock: %d%n", result.remainingStock());
    }

    private static int parseQuantity(String value) {
        try {
            int quantity = Integer.parseInt(value);
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }
            return quantity;
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Quantity must be a whole number: " + value, exception);
        }
    }
}
