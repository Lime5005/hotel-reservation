package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    // A Singletons initiate???
    private static CustomerService customerService;
    public static CustomerService getInstance(){
        if(customerService == null){
            customerService = new CustomerService();
        }
        return customerService;
    }

    // Create a Set:
    public Collection<Customer> setOfCustomers = new HashSet<>();

    // Add a new customer into the Set:
    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(email, firstName, lastName);
        setOfCustomers.add(customer);
    }

    // Retrieve one customer:
    public Customer getCustomer(String customerEmail) {
        for (Customer customer : setOfCustomers) {
            if((customer.getEmail()).equals(customerEmail)){
                return customer;
            }
        }
        return null;
    }

    // Return all of the customers in the collection:
    public Collection<Customer> getAllCustomers(){
        return setOfCustomers;
    }

}
