import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

public class AdminMenu {
    // AdminResource is for admin
    private static AdminResource adminResource = AdminResource.getInstance();

    // To read input
    private final static Scanner scanner = new Scanner(System.in);

    public void prompt() {
        boolean shouldQuit = false;

        while (!shouldQuit) {
            // Initialize option
            int option;

            do {
                System.out.println("--------------------------------------------------");
                System.out.printf("Welcome back to Admin Panel, what would you like to do?%n");
                System.out.println(" 1) See all Customers");
                System.out.println(" 2) See all Rooms");
                System.out.println(" 3) See all Reservations");
                System.out.println(" 4) Add a Room");
                System.out.println(" 5) Back to Main Menu");
                System.out.println();

                // Scan the option
                System.out.print("Enter option: ");
                option = scanner.nextInt();

                // Tell admin the input is wrong
                if (option < 1 || option > 5) {
                    System.out.println("Invalid option. Please choose 1~5");
                }
            } while (option < 1 || option > 5);

            // Once option valid, process the option
            switch (option) {
                case 1 -> seeAllCustomers();
                case 2 -> seeAllRooms();
                case 3 -> seeAllReservations();
                case 4 -> addRooms();
                case 5 -> shouldQuit = true;
            }
        }
    }

    public void addRooms() {
        ArrayList<IRoom> rooms = new ArrayList<>();

        boolean addRoom = true;
        while (addRoom) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter the room ID:");
            String roomNumber = scanner.nextLine();

            System.out.println("Enter the price per night:");
            double price = scanner.nextDouble();

            RoomType roomType = parseRoomType();

            IRoom room = new Room(roomNumber, price, roomType, true);
            rooms.add(room);

            addRoom = ScannerUtils.parseYesNo("Would you like to add another room? y/n");
        }

        adminResource.addRoom(rooms);
    }

    public void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();

        if (!customers.isEmpty()) {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        } else {
            System.out.println("There is no customers yet.");
        }
    }

    public void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (!rooms.isEmpty()) {
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("There are no rooms yet.");
        }
    }

    public void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private RoomType parseRoomType() {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter room type: 1 - Single bed, 2 - Double bed:");
            try {
                int userInput = scanner.nextInt();

                if (userInput == 1) {
                    return RoomType.SINGLE;
                } else if (userInput == 2) {
                    return RoomType.DOUBLE;
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please try again.");
            }
        }
    }
}
