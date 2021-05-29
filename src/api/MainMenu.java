package api;


import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.*;

public class MainMenu {
    public void startActions() {
        char option = '\0';
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");

        do {
            System.out.println();
            System.out.println("Enter an option: ");
            option = scanner.next().charAt(0);
            switch (option) {
                case '1':
                    Queue<String> queuedReserve = new LinkedList<String>();
//                    queuedReserve.add("Enter a check in date: (Year-month-date) ");
//                    queuedReserve.add("Enter a check out date: (Year-month-date)");
//                    while (!queuedReserve.isEmpty()) {
//                        System.out.println(queuedReserve.poll());
//                    }
                    System.out.println("Enter a check in date: (Year-month-date) ");
                    String strCheckIn = scanner.nextLine();
                    Date checkIn = null;
                    if (strCheckIn.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            checkIn = f.parse(strCheckIn);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Error format");
                    }
                    System.out.println("Enter a check out date: (Year-month-date)");
                    String strCheckOut = scanner.nextLine();
                    Date checkOut = null;
                    if (strCheckOut.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}")) {
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            checkOut = f.parse(strCheckOut);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Error format");
                    }
                    System.out.println("These rooms are available: ");
                    HotelResource.getInstance().findARoom(checkIn, checkOut);
                    break;
                case '2':
                    System.out.println("Enter your email: ");
                    String customerEmail = scanner.nextLine();
                    HotelResource.getInstance().getCustomersReservations(customerEmail);
                    break;
                case '3':
                    System.out.println("Enter your first name: ");
                    String firstName = scanner.nextLine();
                    System.out.println("Enter your last name: ");
                    String lastName = scanner.nextLine();
                    System.out.println("Enter your email: ");
                    String email = scanner.nextLine();
                    try {
                        HotelResource.getInstance().createACustomer(email, firstName, lastName);
                    } catch (Exception ex) {
                        ex.getLocalizedMessage();
                    }
                    System.out.println("Congratulations! Your account has been created!");
                    break;
                case '4':
                    AdminMenu aMenu = new AdminMenu();
                    aMenu.startMenu();
                    break;
                default:
                    System.out.println("Error: invalid option. Please enter 1, 2, 3, 4, 5");
                    break;
            }

        } while (option != '5');
        System.out.println("Thank you for booking with us!");
    }

    public static void main(String args[]){
        MainMenu menuObject = new MainMenu();
        menuObject.startActions();
    }
}
