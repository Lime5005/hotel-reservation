package service;

import model.*;

import java.util.*;

public class ReservationService {

    //Initialize
    private static ReservationService reservationService = null;
    static Set<IRoom> rooms;
    static Set<Reservation> reservations;

    private ReservationService() {
        this.rooms = new HashSet<>();
        this.reservations = new HashSet<>();
    }

    // A Singleton class
    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public static void addRoom(IRoom room) {
        rooms.add(room);
    }

    /**
     * Getting a room object by roomNumber
     * @param roomId roomNumber
     * @return a room object
     */
    public static IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if(room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Getting all the rooms for admin
     * @return rooms in a list
     */
    public static Collection<IRoom> getAllRooms() {
        return rooms;
    }

    /**
     * Book a room, store a reservation in the collection.
     * @param customer the Customer object
     * @param room the Room object
     * @param checkInDate date from
     * @param checkOutDate date to
     * @return a reservation object
     */
    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        if (reservations.isEmpty()) {
            return rooms;
        } else {
            return findAvailableRooms(checkInDate, checkOutDate);
        }
    }

    /**
     * Check in all the reservations list, if any rooms are available, store them in a new list.
     * @param checkInDate user input check in date
     * @param checkOutDate user input check out date
     * @return a new list contains all the available rooms.
     */
    static Collection<IRoom> findAvailableRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<IRoom>();
        for (Reservation reservation : reservations) {
            if ((!checkInDate.equals(reservation.getCheckInDate()) && !checkOutDate.equals(reservation.getCheckOutDate())) ||
                    !reservation.getCheckInDate().after(checkInDate)  && !reservation.getCheckOutDate().before(checkOutDate)) {
                for (IRoom room : rooms) {
                    if (!reservation.getRoom().equals(room)) {
                        availableRooms.add(room);
                    }
                }
            }
        }
        return availableRooms;
    }

    public static Collection<IRoom> recommendRooms(Date checkIn, Date checkOut) {
        List<IRoom> recommendRooms = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if ((!checkIn.equals(reservation.getCheckInDate()) && !checkOut.equals(reservation.getCheckOutDate())) ||
                    !reservation.getCheckInDate().after(checkIn)  && !reservation.getCheckOutDate().before(checkOut)) {
                for (IRoom room : rooms) {
                    if (!reservation.getRoom().equals(room)) {
                        recommendRooms.add(room);
                    }
                }
            }
        }
        return recommendRooms;
    }

    /**
     * Getting all the reservations of a customer
     * @param customer the Customer object
     * @return an array list of the reservations
     */
    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> reservationsByCustomer = new ArrayList<Reservation>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                reservationsByCustomer.add(reservation);
            }
        }
        return reservationsByCustomer;
    }

    /**
     * Print all the reservations for admin
     */
    public static void printAllReservations() {
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } else {
            System.out.printf("There is no reservations yet.%n");
        }
    }

}

