import api.AdminResource;
import api.HotelResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HotelApplication {

    public static void main(String[] args) {
        // Create fake Data
        IRoom room1 = new Room("1", 2.0, RoomType.SINGLE, true);
        IRoom room2 = new Room("2", 2d, RoomType.DOUBLE, true);
        ArrayList<IRoom> rooms = new ArrayList<>();
        rooms.add(room1);
        rooms.add(room2);
        AdminResource.getInstance().addRoom(rooms);

        HotelResource hotelResource = HotelResource.getInstance();
        hotelResource.createACustomer("aa@aa.aa", "aa", "aa");
        Date checkInDate = getDateFromToday(3);
        Date checkOutDate = getDateFromToday(6);
        hotelResource.bookARoom("aa@aa.aa", room1, checkInDate, checkOutDate);
        hotelResource.bookARoom("aa@aa.aa", room2, checkInDate, checkOutDate);









        MainMenu mainMenu = new MainMenu();
        mainMenu.prompt();
    }

    private static Date getDateFromToday(int daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (daysToAdd > 0) {
            calendar.add(Calendar.DAY_OF_YEAR, daysToAdd);
        }

        try {
            return dateFormat.parse(dateFormat.format(calendar.getTime()));
        } catch (ParseException e) {
            // Do nothing
        }
        return null;
    }

}
