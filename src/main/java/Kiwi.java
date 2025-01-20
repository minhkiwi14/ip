import java.util.Scanner;

public class Kiwi {
    public static void main(String[] args) {
        String logo = " _   __  _              _    __     __\n"
                + "| | / / |_|            |_|  /  \\   /  \\\n"
                + "| |/ /   _  __      __  _  /_/\\_\\ /_/\\_\\\n"
                + "|   <   | | \\ \\    / / | |\n"
                + "| |\\ \\  | |  \\ \\/\\/ /  | |\n"
                + "|_| \\_\\ |_|   \\_/\\_/   |_|\n";
        String greetingMessage = "Bonjour!\nWhat should I do now?\n";
        String byeMessage = "Au revoir!\nI'm happy to serve you today. See you again soon!";

        System.out.println(logo);
        System.out.println(greetingMessage);

        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            if (userInput.equals("bye")) {
                System.out.println(byeMessage);
                break;
            }
            System.out.println(userInput + "\n");
        }

        scanner.close();
    }
}
