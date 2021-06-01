package model;

import java.util.Objects;

public class Room implements IRoom {
    protected String roomNumber;
    protected Double price;
    protected RoomType enumeration;
    protected boolean isFree;

    public Room(String roomNumber, Double price, RoomType enumeration, boolean isFree) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
        this.isFree = isFree;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.enumeration;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomNumber, room.roomNumber) && Objects.equals(price, room.price) && enumeration == room.enumeration && Objects.equals(isFree, room.isFree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber, price, enumeration, isFree);
    }

    @Override
    public String toString() {
        return String.format("Room#" + roomNumber + " $" + price + "/night" +
                " for " + enumeration + " bed.");
    }

}

