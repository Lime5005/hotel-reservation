package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType enumeration, boolean isFree) {
        super(roomNumber, 0.00, enumeration, true);
    }

    @Override
    public String toString() {
        return String.format("Room#" + roomNumber +
                " for " + enumeration + " bed " + "and it's free.");
    }
}

