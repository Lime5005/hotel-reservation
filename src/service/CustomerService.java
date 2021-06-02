package service;

import model.Customer;
import java.util.*;

public class CustomerService {

    // Initiate
    private static CustomerService customerService;
    private static Collection<Customer> customers;

    private CustomerService() {
        customers = new ArrayList<>();
    }

    // A Singleton class, also called as a "static reference"
    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    /**
     * Creates a new customer with all the valid inputs
     */
    public void addCustomer(String email, String firstName, String lastName) {
        try {
            Customer customer = new Customer(firstName, lastName, email);
            customers.add(customer);
        } catch (IllegalArgumentException ex) {
            ex.getLocalizedMessage();
        }
    }

    public Customer getCustomer(String customerEmail) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public Collection<Customer> getAllCustomers() {
        return customers;
    }
}
