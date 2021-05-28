package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    //static reference for Reservation class
    private static ReservationService reservationService;
    public static ReservationService getInstance(){
        if (reservationService == null){
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    // Create collections to store and retrieve a Reservation:
    public Collection<Reservation> reservations = new HashSet<>();
    public Collection<IRoom> rooms = new HashSet<>();
    private ReservationService() {
    }

    public void addRoom(IRoom room) {
        rooms.add(room);
    }

    public IRoom getARoom(String roomId){
        for (IRoom room: rooms) {
            if ((room.getRoomNumber()).equals(roomId)){
                return room;
            }
        }
        return null;
    }

    public Collection<IRoom> getAllRooms() {
        return rooms;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservedRooms = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservedRooms);
        return reservedRooms;
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){
        for (IRoom room : rooms) {
            if (checkInDate.after(checkInDate) && checkOutDate.before(checkOutDate)) {
                Iterator<IRoom> iRoomIterator = rooms.iterator();
                while (iRoomIterator.hasNext()) {
                    System.out.println(iRoomIterator.next());
                }
                return rooms;
            }
        }
        return null;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){
        CustomerService.getInstance().getCustomer(customer.getEmail());
        return reservations;
    }

    public void printAllReservations(){
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

}
