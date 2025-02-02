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

public class Kiwi {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;

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

    private void handleMarkCommand(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        tasks.markTask(index);
        ui.showMarkMessage(tasks.getTask(index));
    }

    private void handleUnmarkCommand(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        tasks.unmarkTask(index);
        ui.showUnmarkMessage(tasks.getTask(index));
    }

    private void handleDeleteCommand(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        Task removed = tasks.deleteTask(index);
        ui.showDeleteMessage(removed, tasks.size());
    }

    /**
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

    private void handleTodoCommand(String arguments) throws KiwiException {
        if (arguments.isEmpty()) {
            throw new KiwiException("kiwi.task.Todo description cannot be empty!");
        }
        Task task = new Todo(arguments);
        tasks.addTask(task);
        ui.showAddMessage(task, tasks.size());
    }

    private void handleDeadlineCommand(String arguments) throws KiwiException {
        String[] parts = Parser.parseDeadlineArgs(arguments);
        Task task = new Deadline(parts[0], parts[1]);
        tasks.addTask(task);
        ui.showAddMessage(task, tasks.size());
    }

    private void handleEventCommand(String arguments) throws KiwiException {
        String[] parts = Parser.parseEventArgs(arguments);
        Task task = new Event(parts[0], parts[1], parts[2]);
        tasks.addTask(task);
        ui.showAddMessage(task, tasks.size());
    }

    public static void main(String[] args) {
        new Kiwi("./data/kiwi.txt").run();
    }
}
