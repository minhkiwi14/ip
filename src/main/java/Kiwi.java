import java.util.Scanner;

public class Kiwi {
    /**
     * Prints each item in the provided array with numbering, indented by 4 spaces.
     * After printing all items, it prints a separator line.
     *
     * @param array      The array of strings.
     * @param numOfItems The number of items in the array.
     */
    public static void printList(Task[] array, int numOfItems) {
        System.out.println("    Here are your tasks:");
        for (int i = 0; i < numOfItems; i++) {
            System.out.println("    " + (i + 1) + ". " + array[i]);
        }
        System.out.println("    ______________________________");
    }

    public static Task getTodo(String userInput) throws KiwiException {
        try {
            String[] splitUserInput = userInput.split("todo");
            String task = splitUserInput[1].strip();
            Task todoTask = new Todo(task);
            return todoTask;
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new KiwiException("The description of the task cannot be empty.");
        }
    }

    public static Task getDeadline(String userInput) throws KiwiException {
        try {
            String[] splitUserInput = userInput.split("deadline");
            String deadlineDetails = splitUserInput[1].strip();
            String[] splitDeadlineDetails = deadlineDetails.split("/by");
            String task = splitDeadlineDetails[0].strip();
            String deadline = splitDeadlineDetails[1].strip();
            Task deadlineTask = new Deadline(task, deadline);
            return deadlineTask;
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new KiwiException("The description of the task cannot be empty.");
        }
    }

    public static Task getEvent(String userInput) throws KiwiException {
        try {
            String[] splitUserInput = userInput.split("event");
            String eventDetails = splitUserInput[1].strip();
            String[] splitEventDetails = eventDetails.split("/from");
            String task = splitEventDetails[0].strip();
            String period = splitEventDetails[1].strip();
            String[] startEndTime = period.split("/to");
            String startTime = startEndTime[0].strip();
            String endTime = startEndTime[1].strip();
            Task eventTask = new Event(task, startTime, endTime);
            return eventTask;
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new KiwiException("The description of the task cannot be empty.");
        }
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

        Task[] taskList = new Task[100];
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
            } else if (userInput.startsWith("mark")) {
                String[] splitUserInput = userInput.split(" ");
                int index = Integer.parseInt(splitUserInput[1]) - 1;
                taskList[index].markAsDone();
                System.out.println("    Congratulations on having done it!\n    " + taskList[index]);
                System.out.println("    ______________________________");
            } else if (userInput.startsWith("unmark")) {
                String[] splitUserInput = userInput.split(" ");
                int index = Integer.parseInt(splitUserInput[1]) - 1;
                taskList[index].markAsUndone();
                System.out.println("    Uh oh! Do it soon!\n    " + taskList[index]);
                System.out.println("    ______________________________");
            } else if (userInput.startsWith("todo")) {
                try {
                    Task todoTask = getTodo(userInput);
                    taskList[numOfItems++] = todoTask;
                    System.out.println("    Got it! I've added your task:\n    " + todoTask);
                    System.out.println("    Now you have " + numOfItems + " tasks in this list.");
                } catch (KiwiException exception) {
                    System.out.println("    " + exception.getMessage());
                }
                System.out.println("    ______________________________");
            } else if (userInput.startsWith("deadline")) {
                try {
                    Task deadlineTask = getDeadline(userInput);
                    taskList[numOfItems++] = deadlineTask;
                    System.out.println("    Got it! I've added your task:\n    " + deadlineTask);
                    System.out.println("    Now you have " + numOfItems + " tasks in this list.");
                } catch (KiwiException exception) {
                    System.out.println("    " + exception.getMessage());
                }
                System.out.println("    ______________________________");
            } else if (userInput.startsWith("event")) {
                try {
                    Task eventTask = getEvent(userInput);
                    taskList[numOfItems++] = eventTask;
                    System.out.println("    Got it! I've added your task:\n    " + eventTask);
                    System.out.println("    Now you have " + numOfItems + " tasks in this list.");
                } catch (KiwiException exception) {
                    System.out.println("    " + exception.getMessage());
                }
                System.out.println("    ______________________________");
            } else {
                try {
                    throw new KiwiException("This is not a valid command. Please try again!");
                } catch (KiwiException exception) {
                    System.out.println("    " + exception.getMessage());
                }
                System.out.println("    ______________________________");
            }
        }

        scanner.close();
    }
}
