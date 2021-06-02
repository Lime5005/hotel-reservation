package service;

import model.*;

import java.util.*;

public class ReservationService {

    //Initialize
    private static ReservationService reservationService = null;

    private Set<IRoom> rooms;
    private Set<Reservation> reservations;

    private ReservationService() {
        rooms = new HashSet<>();
        reservations = new HashSet<>();
    }

    // A Singleton class
    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    public IRoom getARoom(String roomId) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>();

        for (IRoom room : rooms) {
            // Check if room is available
            for (Reservation reservation : reservations) {
                if (reservation.getRoom() == room) {
                    boolean checkInKO = checkInDate.equals(reservation.getCheckInDate()) ||
                            (checkInDate.after(reservation.getCheckInDate()) &&
                                    checkInDate.before(reservation.getCheckOutDate()));
                    boolean checkOutKO = checkOutDate.after(reservation.getCheckInDate()) &&
                            checkOutDate.before(reservation.getCheckOutDate());

                    if (checkInKO || checkOutKO) {
                        // Room is occupied
                    } else {
                        availableRooms.add(room);
                    }
                }
            }
        }

        return availableRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> reservationsByCustomer = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                reservationsByCustomer.add(reservation);
            }
        }
        return reservationsByCustomer;
    }

    public void printAllReservation() {
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } else {
            System.out.printf("There are no reservations yet.%n");
        }
    }

    /**
     * Getting all the rooms for admin
     * @return rooms in a list
     */
    public Collection<IRoom> getAllRooms() {
        return rooms;
    }
}
