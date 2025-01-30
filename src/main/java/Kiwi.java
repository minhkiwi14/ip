import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Kiwi {
    /**
     * Prints each item in the provided array with numbering, indented by 4 spaces.
     * After printing all items, it prints a separator line.
     *
     * @param list The list of tasks.
     */
    public static void printList(ArrayList<Task> list) {
        System.out.println("    Here are your tasks:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("    " + (i + 1) + ". " + list.get(i));
        }
    }

    public static Task getTodo(String userInput) throws KiwiException {
        try {
            String[] splitUserInput = userInput.split("todo");
            String task = splitUserInput[1].strip();
            return new Todo(task);
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
            return new Deadline(task, deadline);
        } catch (ArrayIndexOutOfBoundsException exception) {
            throw new KiwiException(
                    "The description of a deadline cannot be empty. Use 'deadline <task> /by <date> <time>'.");
        } catch (DateTimeParseException exception) {
            throw new KiwiException(
                    "Invalid date or time format. "
                            + "Please use 'yyyy-mm-dd' (e.g., 2025-01-31) and 'hh:mm' (e.g., 03:00).");
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
            return new Event(task, startTime, endTime);
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

        String filePath = "./data/kiwi.txt";
        Storage storage = new Storage(filePath);

        ArrayList<Task> taskList = new ArrayList<>(100);
        try {
            taskList = storage.load();
        } catch (KiwiException exception) {
            System.out.println("    " + exception.getMessage());
        }

        while (scanner.hasNextLine()) {
            userInput = scanner.nextLine();
            System.out.println("    ______________________________");

            String command = userInput.split(" ")[0];

            switch (command) {
            case "bye":
                System.out.println("    " + byeMessage);
                break;

            case "list":
                printList(taskList);
                break;

            case "mark":
                try {
                    int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                    taskList.get(index).markAsDone();
                    System.out.println("    Congratulations on having done it!\n    " + taskList.get(index));
                    storage.save(taskList);
                } catch (Exception e) {
                    System.out.println("    Invalid mark command or index. Please try again!");
                }
                break;

            case "unmark":
                try {
                    int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                    taskList.get(index).markAsUndone();
                    System.out.println("    Uh oh! Do it soon!\n    " + taskList.get(index));
                    storage.save(taskList);
                } catch (Exception e) {
                    System.out.println("    Invalid unmark command or index. Please try again!");
                }
                break;

            case "delete":
                try {
                    int index = Integer.parseInt(userInput.split(" ")[1]) - 1;
                    Task removedTask = taskList.get(index);
                    taskList.remove(index);
                    System.out.println("    OK! I've removed this task:\n    " + removedTask);
                    System.out.println("    Now you have " + taskList.size() + " tasks in this list.");
                    storage.save(taskList);
                } catch (Exception e) {
                    System.out.println("    Invalid delete command or index. Please try again!");
                }
                break;

            case "todo":
                try {
                    Task todoTask = getTodo(userInput);
                    taskList.add(todoTask);
                    System.out.println("    Got it! I've added your task:\n    " + todoTask);
                    System.out.println("    Now you have " + taskList.size() + " tasks in this list.");
                    storage.save(taskList);
                } catch (KiwiException exception) {
                    System.out.println("    " + exception.getMessage());
                }
                break;

            case "deadline":
                try {
                    Task deadlineTask = getDeadline(userInput);
                    taskList.add(deadlineTask);
                    System.out.println("    Got it! I've added your task:\n    " + deadlineTask);
                    System.out.println("    Now you have " + taskList.size() + " tasks in this list.");
                    storage.save(taskList);
                } catch (KiwiException exception) {
                    System.out.println("    " + exception.getMessage());
                }
                break;

            case "event":
                try {
                    Task eventTask = getEvent(userInput);
                    taskList.add(eventTask);
                    System.out.println("    Got it! I've added your task:\n    " + eventTask);
                    System.out.println("    Now you have " + taskList.size() + " tasks in this list.");
                    storage.save(taskList);
                } catch (KiwiException exception) {
                    System.out.println("    " + exception.getMessage());
                }
                break;

            default:
                try {
                    throw new KiwiException("This is not a valid command. Please try again!");
                } catch (KiwiException exception) {
                    System.out.println("    " + exception.getMessage());
                }
            }

            if (command.equals("bye")) {
                break;
            }

            System.out.println("    ______________________________");
        }

        scanner.close();
    }
}