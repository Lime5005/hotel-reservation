package service;

import model.Customer;
import java.util.*;

public class CustomerService {

    // Initiate
    private static CustomerService customerService = null;
    private static Collection<Customer> customers;

    private CustomerService() {
        this.customers = new ArrayList<>();
    }

    // A Singleton class, also called as a "static reference"
    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    /**
     * Create a new customer with all the valid inputs
     * @param email email input
     * @param firstName first name
     * @param lastName last name
     */
    public static void addCustomer(String email, String firstName, String lastName) {
        try {
            Customer customer = new Customer(firstName, lastName, email);
            customers.add(customer);
        } catch (IllegalArgumentException ex) {
            ex.getLocalizedMessage();
        }
    }

    public static Customer getCustomer(String customerEmail) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }

    public static void getAllCustomers() {
        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        } else {
            System.out.println("There is no customers yet.");
        }
    }

}


