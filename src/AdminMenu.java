import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    // To read input
    final static Scanner scanner = new Scanner(System.in);

    // AdminResource is for admin
    static AdminResource adminResource = AdminResource.getInstance();

    public AdminMenu() {
    }

    public static void adminMenuPrompt() {
        // Initialize option
        int option = 0;

        do {
            System.out.println("--------------------------------------------------");
            System.out.printf("Welcome back to Admin Panel, what would you like to do?%n");
            System.out.println(" 1) See all Customers");
            System.out.println(" 2) See all Rooms");
            System.out.println(" 3) See all Reservations");
            System.out.println(" 4) Add a Room");
            System.out.println(" 5) Back to Main Menu");

            // Scan the option
            System.out.println();
            System.out.print("Enter option: ");
            option = scanner.nextInt();

            // Tell admin the input is wrong
            if (option < 1 || option > 5) {
                System.out.println("Invalid option. Please choose 1~5");
            }
        } while (option < 1 || option > 5);

        // Once option valid, process the option
        switch (option) {
            case 1:
                AdminMenu.seeAllCustomers();
                break;
            case 2:
                AdminMenu.seeAllRooms();
                break;
            case 3:
                AdminMenu.seeAllReservations();
                break;
            case 4:
                AdminMenu.adminAddRooms();
                break;
            case 5:
                MainMenu mainMenu = new MainMenu();
                mainMenu.mainMenuPrompt();
                break;
        }

        // Re-display until admin quit admin panel
        if (option != 5) {
            AdminMenu.adminMenuPrompt();
        }
    }

    public static void adminAddRooms() {

        String addRoom;
        int typeOption;

        do {
            RoomType roomType = null;
            scanner.nextLine();
            System.out.println("Enter the room ID:");
            String roomNumber = scanner.nextLine();
            System.out.println("Enter the price per night:");
            Double price = scanner.nextDouble();
            do {
                System.out.println("Enter room type: 1 - Single bed, 2 - Double bed:");
                typeOption = scanner.nextInt();

                if (typeOption == 1) {
                    roomType = RoomType.SINGLE;
                } else if (typeOption == 2) {
                    roomType = RoomType.DOUBLE;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } while (typeOption != 1 && typeOption != 2);

            IRoom room = new Room(roomNumber, price, roomType, price == 0.0 ? true : false);
            List<IRoom> rooms = new ArrayList<IRoom>();
            rooms.add(room);
            adminResource.addRoom(rooms);
            do {
                System.out.println("Would you like to add another room? y/n");
                addRoom = scanner.next().toLowerCase().trim();
            } while (!addRoom.equals("y") && !addRoom.equals("n"));

        } while (addRoom.equals("y"));
    }

    public static void seeAllCustomers() {
        adminResource.getAllCustomers();
    }

    public static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if (!rooms.isEmpty()) {
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        } else {
            System.out.println("There is no rooms yet.");
        }
    }

    public static void seeAllReservations() {
        adminResource.displayAllReservations();
    }
}
