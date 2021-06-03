import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

    private final HotelResource hotelResource = HotelResource.getInstance();

    // To read input
    private final Scanner scanner = new Scanner(System.in);

    public void prompt() {
        boolean shouldQuit = false;

        while (!shouldQuit) {
            int option;

            do {
                System.out.println("--------------------------------------------------");
                System.out.printf("Welcome to Golden Hotel, what do you like to do?%n");
                System.out.println(" 1) Find and reserve a room");
                System.out.println(" 2) See my reservations");
                System.out.println(" 3) Create an account");
                System.out.println(" 4) Admin");
                System.out.println(" 5) Exit");
                System.out.println();

                // Scan the option
                System.out.print("Enter option: ");
                option = scanner.nextInt();

                // Check input
                if (option < 1 || option > 5) {
                    System.out.println("Invalid option. Please choose 1~5");
                }
            } while (option < 1 || option > 5);

            // Once option valid, process the option
            switch (option) {
                case 1 -> bookARoom();
                case 2 -> showReservationsByCustomer();
                case 3 -> createAccount();
                case 4 -> new AdminMenu().prompt();
                case 5 -> shouldQuit = true;
            }
        }
    }

    public void bookARoom() {
        Date checkIn = null, checkOut = null;

        boolean checkInOutDatesValid = false;
        while (!checkInOutDatesValid) {
            checkIn = ScannerUtils.parseInputDate("Enter the check-in date: yyyy-MM-dd");
            checkOut = ScannerUtils.parseInputDate("Enter the check-out date: yyyy-MM-dd");
            checkInOutDatesValid = verifyCheckInCheckOutDates(checkIn, checkOut);
        }

        // Find the all available rooms
        Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);
        if (availableRooms.isEmpty()) {
            // No rooms available. Search for recommendations 7 days later
            Date recommendedCheckIn = addSevenDaysToDate(checkIn);
            Date recommendedCheckOut = addSevenDaysToDate(checkOut);

            Collection<IRoom> recommendedRooms = hotelResource.findARoom(recommendedCheckIn, recommendedCheckOut);
            if (recommendedRooms.isEmpty()) {
                System.out.println("There are no rooms available for the given dates.");
            } else {
                System.out.printf("There are no rooms available for the given dates,%n" +
                        "however, we've got available rooms a week later if you're interested.%n");
                performBooking(recommendedRooms, recommendedCheckIn, recommendedCheckOut);
            }
        } else {
            performBooking(availableRooms, checkIn, checkOut);
        }
    }

    public void showReservationsByCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your email:");
        String email = scanner.next();
        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);
        if (!reservations.isEmpty()) {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        } else {
            System.out.println("You haven't reserved any room yet.");
        }
    }

    private boolean verifyCheckInCheckOutDates(Date checkIn, Date checkOut) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date todayDate = dateFormat.parse(dateFormat.format(calendar.getTime()));

            if (checkIn.before(todayDate) || checkOut.before(checkIn)) {
                System.err.printf("Invalid dates. Check-in date should be today onwards%n" +
                        "and check-out date should be equal or later than check-in date.%n");
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void createAccount() {
        Scanner scanner = new Scanner(System.in);

        String firstName;
        String lastName;
        String email;

        do {
            System.out.println("Enter your first name: (at least 2 characters)");
            firstName = scanner.nextLine();

            System.out.println("Enter your last name: (at least 2 characters)");
            lastName = scanner.nextLine();

            System.out.println("Enter your email: (name@domain.com)");
            email = scanner.nextLine();

            if (firstName.equals("") || lastName.equals("") || email.equals("")) {
                System.out.println("Invalid input. Please try again.");
            }
        } while (firstName.equals("") || lastName.equals("") || email.equals(""));

        try {
            hotelResource.createACustomer(email, firstName, lastName);
        } catch (IllegalArgumentException ex) {
            ex.getLocalizedMessage();
        }
    }

    private Date addSevenDaysToDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 7);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(dateFormat.format(calendar.getTime()));
        } catch (ParseException ignored) {
        }
        return null;
    }

    private void performBooking(Collection<IRoom> availableRooms, Date checkIn, Date checkOut) {
        // Display available rooms
        System.out.println("--------------------------------------------------");
        System.out.println("Room(s) available for the given dates: " + checkIn + " -> " + checkOut);
        availableRooms.forEach(System.out::println);
        System.out.println();

        if (ScannerUtils.parseYesNo("Would you like to book a room? y/n")) {
            boolean hasAccount = ScannerUtils.parseYesNo("Do you have an account with us? y/n");
            if (hasAccount) {
                System.out.println("Please enter your email");
                String email = scanner.next();
                Customer customer = hotelResource.getCustomer(email);

                if (customer == null) {
                    System.err.println("Customer not found. Please try again.");
                } else {
                    System.out.println("Enter the room# that you want from the room(s) list below:");
                    availableRooms.forEach(System.out::println);
                    String roomNumber = scanner.next();
                    IRoom room = hotelResource.getRoom(roomNumber);
                    hotelResource.bookARoom(email, room, checkIn, checkOut);
                    System.out.println();
                    System.out.printf("So room# %s is reserved for you, thank you %s %s!%n", roomNumber, customer.getFirstName(), customer.getLastName());
                }
            } else {
                System.out.println("You'll have to create an account first.");
                createAccount();
            }
        } else {
            prompt();
        }
    }

}
