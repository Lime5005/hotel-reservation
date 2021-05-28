package model;

public class Room implements IRoom {
    private String roomNumber;
    private Double price;
    private RoomType enumeration;
    private boolean isFree;

    public Room() {
        super();
    }

    @Override
    public String toString() {
        return "The room number is: " + roomNumber + "The price is: " + price + "Room type is: " + enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree() {
        return isFree;
    }
}
