package org.eclipsefeaturesdemo.bookstore;

import java.util.Objects;

public class Customer {

    private final String customerId;
    private String name;
    private String email;

    public Customer(String customerId, String name, String email) {
        this.customerId = requireText(customerId, "Customer ID");
        this.name = requireText(name, "Name");
        this.email = requireText(email, "Email");
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = requireText(name, "Name");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = requireText(email, "Email");
    }

    @Override
    public String toString() {
        return "Customer[customerId=%s, name=%s, email=%s]"
                .formatted(customerId, name, email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Customer other)) {
            return false;
        }
        return customerId.equals(other.customerId);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank");
        }
        return value;
    }
}
