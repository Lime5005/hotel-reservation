package model;

import java.util.Date;

public class Driver {
    public static void main(String[] args) {
        Customer customer = new Customer("Lily", "Rose", "domain@gmail.com");
        System.out.println(customer);
        IRoom room = new Room("111", 100.00, RoomType.DOUBLE, false);
        System.out.println(room);
        Room freeRoom = new FreeRoom("000", 0.00, RoomType.DOUBLE, true);
        System.out.println(freeRoom);
        Reservation reservation = new Reservation(customer, room, new Date(), new Date());
        System.out.println(reservation);
    }
}

