package model;

import java.util.function.DoubleBinaryOperator;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, Double price, RoomType enumeration, boolean isFree) {
        super(roomNumber, 0.0, enumeration, true);
    }

    @Override
    public String toString() {
        return "The price for free room is 0";
    }
}
