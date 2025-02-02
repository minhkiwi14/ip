package kiwi;

import kiwi.command.Parser;
import kiwi.command.TaskList;
import kiwi.exception.KiwiException;
import kiwi.storage.Storage;
import kiwi.task.Deadline;
import kiwi.task.Event;
import kiwi.task.Task;
import kiwi.task.Todo;
import kiwi.ui.Ui;

import java.util.Scanner;

/**
 * The main Kiwi class that handles the logic for managing tasks and user interaction.
 * This class initializes the task list, handles user input, processes commands,
 * and saves the task list to a file.
 */
public class Kiwi {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

    /**
     * Constructs a new Kiwi object with the specified file path for loading and saving tasks.
     *
     * @param filePath The file path for storing the task list.
     */
    public Kiwi(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (KiwiException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main logic loop for the Kiwi application.
     * This method listens for user input, processes commands, and updates the task list.
     */
    public void run() {
        ui.showWelcome();
        ui.showDivider();
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = scanner.nextLine();
                ui.showDivider();

                String[] parts = fullCommand.split(" ", 2);
                String command = parts[0].toLowerCase();
                String arguments = parts.length > 1 ? parts[1] : "";

                switch (command) {
                case "bye":
                    ui.showGoodbye();
                    isExit = true;
                    break;

                case "list":
                    ui.printTasks(tasks);
                    break;

                case "mark":
                    handleMarkCommand(arguments);
                    break;

                case "unmark":
                    handleUnmarkCommand(arguments);
                    break;

                case "delete":
                    handleDeleteCommand(arguments);
                    break;

                case "todo":
                    handleTodoCommand(arguments);
                    break;

                case "deadline":
                    handleDeadlineCommand(arguments);
                    break;

                case "event":
                    handleEventCommand(arguments);
                    break;

                case "find":
                    handleFindCommand(arguments);
                    break;

                default:
                    throw new KiwiException("I don't understand that command!");
                }

                if (!isExit) {
                    storage.save(tasks.getAllTasks());
                }

            } catch (KiwiException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showDivider();
            }
        }
        scanner.close();
    }

    /**
     * Handles the "mark" command by marking a task as completed.
     *
     * @param arguments The arguments containing the index of the task to mark.
     * @throws KiwiException If the index is invalid or the task cannot be marked.
     */
    private void handleMarkCommand(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        tasks.markTask(index);
        ui.showMarkMessage(tasks.getTask(index));
    }

    /**
     * Handles the "unmark" command by marking a task as not completed.
     *
     * @param arguments The arguments containing the index of the task to unmark.
     * @throws KiwiException If the index is invalid or the task cannot be unmarked.
     */
    private void handleUnmarkCommand(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        tasks.unmarkTask(index);
        ui.showUnmarkMessage(tasks.getTask(index));
    }

    /**
     * Handles the "delete" command by removing a task from the task list.
     *
     * @param arguments The arguments containing the index of the task to delete.
     * @throws KiwiException If the index is invalid or the task cannot be deleted.
     */
    private void handleDeleteCommand(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        Task removed = tasks.deleteTask(index);
        ui.showDeleteMessage(removed, tasks.size());
    }

     * Handles the find command by searching tasks matching the given keyword.
     *
     * @param keyword The search term to look for in task descriptions
     * @throws KiwiException If the keyword is empty
     */
    private void handleFindCommand(String keyword) throws KiwiException {
        if (keyword.isEmpty()) {
            throw new KiwiException("Please specify a search keyword!");
        }
        TaskList matchingTasks = tasks.findTasks(keyword);
        ui.showFoundTasks(matchingTasks);
    }

    /**
     * Handles the "todo" command by creating a new todo task and adding it to the task list.
     *
     * @param arguments The arguments containing the description of the todo task.
     * @throws KiwiException If the description of the todo is empty.
     */
    private void handleTodoCommand(String arguments) throws KiwiException {
        if (arguments.isEmpty()) {
            throw new KiwiException("kiwi.task.Todo description cannot be empty!");
        }
        Task task = new Todo(arguments);
        tasks.addTask(task);
        ui.showAddMessage(task, tasks.size());
    }

    /**
     * Handles the "deadline" command by creating a new deadline task and adding it to the task list.
     *
     * @param arguments The arguments containing the description and deadline date.
     * @throws KiwiException If the format of the arguments is incorrect.
     */
    private void handleDeadlineCommand(String arguments) throws KiwiException {
        String[] parts = Parser.parseDeadlineArgs(arguments);
        Task task = new Deadline(parts[0], parts[1]);
        tasks.addTask(task);
        ui.showAddMessage(task, tasks.size());
    }

    /**
     * Handles the "event" command by creating a new event task and adding it to the task list.
     *
     * @param arguments The arguments containing the description, start time, and end time of the event.
     * @throws KiwiException If the format of the arguments is incorrect.
     */
    private void handleEventCommand(String arguments) throws KiwiException {
        String[] parts = Parser.parseEventArgs(arguments);
        Task task = new Event(parts[0], parts[1], parts[2]);
        tasks.addTask(task);
        ui.showAddMessage(task, tasks.size());
    }

    /**
     * The main method that starts the Kiwi application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        new Kiwi("./data/kiwi.txt").run();
    }
}

