package model;

public class FreeRoom extends Room {
    protected Double price = 0.00;

    public FreeRoom(String roomNumber, Double price, RoomType enumeration) {
        super();
    }

    @Override
    public String toString() {
        return "The price for free room is " + price;
    }
}
