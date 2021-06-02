import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.awt.desktop.SystemEventListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainMenu {
    // To read input
    final static Scanner scanner = new Scanner(System.in);
    // HotelResource is for customer in main menu
    static HotelResource hotelResource = HotelResource.getInstance();

//    public MainMenu() {}

    public static void mainMenuPrompt() {

        // Initialize option
        int option = 0;

        do {
            System.out.println("--------------------------------------------------");
            System.out.printf("Welcome to Golden Hotel, what do you like to do?%n");
            System.out.println(" 1) Find and reserve a room");
            System.out.println(" 2) See my reservations");
            System.out.println(" 3) Create an account");
            System.out.println(" 4) Admin");
            System.out.println(" 5) Exit");

            // Scan the option
            System.out.println();
            System.out.print("Enter option: ");
            option = scanner.nextInt();

            // Tell customer the input is wrong
            if (option < 1 || option > 5) {
                System.out.println("Invalid option. Please choose 1~5");
            }

        } while (option < 1 || option > 5);

        // Once option valid, process the option
        switch (option) {
            case 1:
                MainMenu.bookARoom(scanner);
                break;
            case 2:
                MainMenu.showReservationsByCustomer(scanner);
                break;
            case 3:
                MainMenu.createAccount(scanner);
                break;
            case 4:
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.adminMenuPrompt();
                break;
            case 5:
                scanner.nextLine();
                break;
        }

        // Re-display the menu until customer quit
        if (option != 5) {
            MainMenu.mainMenuPrompt();
        }
    }

    public static void createAccount(Scanner scanner) {
        String firstName;
        String lastName;
        String email;
        scanner.nextLine();
        do {
            System.out.println("Enter your first name: (at least 2 characters)");
            firstName = scanner.nextLine();
            System.out.println("Enter your last name: (at least 2 characters)");
            lastName = scanner.nextLine();
            System.out.println("Enter your email: (name@domain.com)");
            email = scanner.nextLine();

            if (firstName == "" || lastName == "" || email == "") {
                System.out.println("Invalid input. Please try again.");
            }
            try {
                hotelResource.createACustomer(email, firstName, lastName);
            } catch (IllegalArgumentException ex) {
                ex.getLocalizedMessage();
            }


        } while (firstName == "" || lastName == "" || email == "");
    }

    public static void bookARoom(Scanner scanner) {

        // Initialize all data needed
        Date checkIn;
        Date checkOut;
        String bookARoom;
        String hasAccount;

        Calendar calendar = Calendar.getInstance();

        final String PATTERN = "yyyy-MM-dd";
        SimpleDateFormat date = new SimpleDateFormat(PATTERN);
        scanner.nextLine();


        try {
            System.out.println("Enter the check-in date: yyyy-MM-dd");
            Date todayDate = date.parse(date.format(calendar.getTime()));
            checkIn = date.parse(scanner.nextLine());
            System.out.println("Enter the check-out date: yyyy-MM-dd");
            checkOut = date.parse(scanner.nextLine());
            if (!checkIn.before(todayDate) && !checkOut.before(checkIn)) {
                // Find the all available rooms
                Collection<IRoom> rooms = hotelResource.findARoom(checkIn, checkOut);

                if (!rooms.isEmpty()) {
                    do {
                        System.out.println("--------------------------------------------------");
                        System.out.println("Available room(s) list as below for chosen dates:");
                        rooms.forEach(System.out::println);
                        System.out.println("Would you like to book a room? y/n");
                        bookARoom = scanner.next().toLowerCase().trim();
                        if (bookARoom.equals("y")) {
                            scanner.nextLine();
                            System.out.println("Do you have an account with us? y/n");
                            hasAccount = scanner.next().toLowerCase().trim();
                            if (hasAccount.equals("n")) {
                                System.out.println("You'll have to create an account first.");
                                createAccount(scanner);
                            } else if (hasAccount.equals("y")) {
                                scanner.nextLine();
                                System.out.println("Please enter your email");
                                String email = scanner.next();
                                Customer customer = hotelResource.getCustomer(email);
                                if (customer != null) {
                                    System.out.println("Enter the room# that you want from below the room(s) list:");
                                    rooms.forEach(System.out::println);
                                    String roomNumber = scanner.next();
                                    IRoom room = hotelResource.getRoom(roomNumber);
                                    hotelResource.bookARoom(email, room, checkIn, checkOut);
                                    System.out.println();
                                    System.out.printf("So room# %s is reserved for you, thank you %s %s!%n", roomNumber, customer.getFirstName(), customer.getLastName());
                                } else if (customer == null) {
                                    System.out.println("Invalid input. Please try again.");
                                }

                            } else {
                                System.out.println("Invalid input. Please try again.");
                            }
                        } else if (bookARoom.equals("n")) {
                            mainMenuPrompt();
                        } else {
                            System.out.println("Invalid input. Please try again.");
                        }

                    } while (!bookARoom.equals("y") && !bookARoom.equals("n"));

                } else {
                    System.out.println("There is no room available in chosen dates.");
                    System.out.printf("Recommended room(s) for 7 days later: ");
                    recommendedRoom(checkIn, checkOut, scanner);
                }
            } else {
                System.out.printf("Invalid dates. Check-in date should be today onwards%n" +
                        "and check-out date should be equal or later than check-in date.%n" +
                        "Type ENTER to start again.");
                bookARoom(scanner);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void showReservationsByCustomer(Scanner scanner) {
        scanner.nextLine();
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

    public static void recommendedRoom(Date checkIn, Date checkOut, Scanner scanner) {

        // Calculate with 7 days added in case no room available, to propose customer
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(checkIn);
        calendar.add(Calendar.DATE, 7);
        Date sevenDaysLaterCheckIn = calendar.getTime(); // add 7 days

        calendar.setTime(checkOut);
        calendar.add(Calendar.DATE, 7);
        Date sevenDaysLaterCheckOut = calendar.getTime();

        // Find available rooms in newly defined dates:
        Collection<IRoom> rooms = hotelResource.getRecommendRooms(sevenDaysLaterCheckIn, sevenDaysLaterCheckOut);

        // Show user if we have rooms:
        if (!rooms.isEmpty()) {
            System.out.printf("These rooms: from" + sevenDaysLaterCheckIn + " to " + sevenDaysLaterCheckOut + "%n");
            rooms.forEach(System.out::println);
            // Continue to book for recommend dates:
            System.out.println("Want to book for recommend dates? y/n");
            String bookRecommend = scanner.nextLine().toLowerCase().trim();
            if (bookRecommend.equals("y")) {
                scanner.nextLine();
                System.out.println("Please enter your email");
                String email = scanner.next();
                Customer customer = hotelResource.getCustomer(email);
                if (customer != null) {
                    System.out.println("Enter the room# that you want from below the room(s) list:");
                    rooms.forEach(System.out::println);
                    String roomNumber = scanner.next();
                    IRoom room = hotelResource.getRoom(roomNumber);
                    hotelResource.bookARoom(email, room, sevenDaysLaterCheckIn, sevenDaysLaterCheckOut);
                    System.out.println();
                    System.out.printf("So room# %s is reserved for you, thank you %s %s!%n", roomNumber, customer.getFirstName(), customer.getLastName());
                } else if (customer == null) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        } else {
            System.out.println("No rooms, please try other dates.");
            bookARoom(scanner);
        }

    }

}
