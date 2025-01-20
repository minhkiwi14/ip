import java.util.Scanner;

public class Kiwi {
    /**
     * Prints each item in the provided array with numbering, indented by 4 spaces.
     * After printing all items, it prints a separator line.
     *
     * @param array The array of strings.
     * @param numOfItems The number of items in the array.
     */
    public static void printList(String[] array, int numOfItems) {
        for (int i = 0; i < numOfItems; i++) {
            System.out.println("    " + (i + 1) + ". " + array[i]);
        }
        System.out.println("    ______________________________");
    }

    public static void main(String[] args) {
        String logo = " _   __  _              _    __     __\n"
                + "| | / / |_|            |_|  /  \\   /  \\\n"
                + "| |/ /   _  __      __  _  /_/\\_\\ /_/\\_\\\n"
                + "|   <   | | \\ \\    / / | |\n"
                + "| |\\ \\  | |  \\ \\/\\/ /  | |\n"
                + "|_| \\_\\ |_|   \\_/\\_/   |_|\n";
        String greetingMessage = "Bonjour!\n    What should I do now?";
        String byeMessage = "Au revoir!\n    I'm happy to serve you today. See you again soon!";

        System.out.println(logo);
        System.out.println("    " + greetingMessage);
        System.out.println("    ______________________________");

        Scanner scanner = new Scanner(System.in);
        String userInput = "";

        String[] taskList = new String[100];
        int numOfItems = 0;

        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            System.out.println("    ______________________________");

            if (userInput.equals("bye")) {
                System.out.println("    " + byeMessage);
                break;
            }

            if (userInput.equals("list")) {
                printList(taskList, numOfItems);
            } else {
                taskList[numOfItems] = userInput;
                numOfItems++;
                System.out.println("    added: " + userInput);
                System.out.println("    ______________________________");
            }
        }

        scanner.close();
    }
}
