package api;

import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.Scanner;

public class AdminMenu {
    public void startMenu() {
        char option = '\0';
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Back to Main Menu");

        do {
            System.out.println();
            System.out.println("Enter an option: ");
            option = scanner.next().charAt(0);
            switch (option) {
                case '1':
                    AdminResource.getInstance().getAllCustomers();
                    break;
                case '2':
                    AdminResource.getInstance().getAllRooms();
                    break;
                case '3':
                    AdminResource.getInstance().displayAllReservations();
                case '4':
                    System.out.println("Enter a room Number: ");
                    String roomNumber = scanner.nextLine();
                    System.out.println("Enter the price: ");
                    Double price = scanner.nextDouble();
                    System.out.println("Enter the room type: ");
                    RoomType enumeration = RoomType.valueOf(scanner.nextLine());
                    System.out.println("Is it free room?");
                    boolean isFree = scanner.hasNextBoolean();

                    IRoom room = new Room(roomNumber, price, enumeration, isFree);
                    AdminResource.getInstance().addRoom(room);
                    System.out.println("Room has been added!");
                    break;
            }
        } while (option != 5);
        MainMenu mainMenu = new MainMenu();
        mainMenu.startActions();
    }

    public static void main(String args[]){
        //you can start admin menu here if you wish
        AdminMenu menuObject = new AdminMenu();
        menuObject.startMenu();
    }
}
