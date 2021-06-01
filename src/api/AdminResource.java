package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    // A static reference
    private static AdminResource adminResource = null;

    // Initialize service classes
    static CustomerService customerService = CustomerService.getInstance();
    static ReservationService reservationService = ReservationService.getInstance();

    private AdminResource() {}

    // A Singleton class
    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public static Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public static void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public static Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    public static void getAllCustomers() {
        customerService.getAllCustomers();
    }

    public static void displayAllReservations() {
        reservationService.printAllReservations();
    }
}

