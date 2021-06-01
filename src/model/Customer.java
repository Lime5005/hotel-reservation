package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    // For email validation
    private final String emailRegex = "^(.+)@(.+).(.+)$";
    private final Pattern PATTERN = Pattern.compile(emailRegex);

    public Customer (String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = validateEmail(email);
    }

    private String validateEmail(String email) {
        if (PATTERN.matcher(email).matches()) {
            return email;
        }
        throw new IllegalArgumentException("The email format is not valid.");
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }

    @Override
    public String toString() {
        return String.format("Customer: " + firstName + " " + lastName + " Email: " + email);
    }
}

