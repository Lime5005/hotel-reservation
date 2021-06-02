import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ScannerUtils {

    public static Date parseInputDate(String message) {
        Date date = null;
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        while (date == null) {
            System.out.println(message);
            String userInput = scanner.nextLine();

            try {
                date = dateFormat.parse(userInput);
            } catch (ParseException e) {
                System.err.println("Invalid date");
            }
        }

        return date;
    }

    public static boolean parseYesNo(String message) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(message);
            String userInput = scanner.next().toLowerCase().trim();
            if (userInput.equals("y")) {
                return true;
            } else if (userInput.equals("n")) {
                return false;
            } else {
                System.err.println("Invalid input. Please try again.");
            }
        }
    }

}
