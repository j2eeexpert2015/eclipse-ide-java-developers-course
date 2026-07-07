package org.eclipsefeaturesdemo.bookstore.export;

import java.util.List;

import org.eclipsefeaturesdemo.bookstore.domain.Book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookJsonExporter {

    private final ObjectMapper objectMapper;

    public BookJsonExporter() {
        this(new ObjectMapper());
    }

    BookJsonExporter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(List<Book> books) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(books);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Could not convert the book catalog to JSON", exception);
        }
    }
}
