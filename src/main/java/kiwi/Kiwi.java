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

import java.util.Map;

/**
 * The Kiwi class represents the main controller for the Kiwi task management application.
 * It handles user input, processes commands, and updates the task list accordingly.
 */
public class Kiwi {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;
    private boolean hasError = false;
    private String loadErrorMessage = "";

    /**
     * Constructs a Kiwi instance, initializing the UI, storage, and task list.
     * Attempts to load tasks from the specified file path.
     *
     * @param filePath The file path to load the task list from.
     */
    public Kiwi(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (KiwiException e) {
            hasError = true;
            loadErrorMessage = ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Returns the greeting message for the user, including any load error messages.
     *
     * @return A string containing the welcome message.
     */
    public String getGreeting() {
        String greeting = ui.showWelcome();
        if (hasError) {
            greeting += "\n" + loadErrorMessage;
        }
        return greeting;
    }

    /**
     * Processes the user's input and returns the appropriate response based on the command.
     *
     * @param input The user's input string containing a command.
     * @return The response message to be displayed.
     */
    public String getResponse(String input) {
        try {
            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();
            String arguments = parts.length > 1 ? parts[1] : "";

            switch (command) {
            case "bye":
                return handleBye();
            case "list":
                return handleList();
            case "mark":
                return handleMark(arguments);
            case "unmark":
                return handleUnmark(arguments);
            case "delete":
                return handleDelete(arguments);
            case "todo":
                return handleTodo(arguments);
            case "deadline":
                return handleDeadline(arguments);
            case "event":
                return handleEvent(arguments);
            case "find":
                return handleFind(arguments);
            case "edit":
                return handleEdit(arguments);
            default:
                throw new KiwiException("I don't understand that command!");
            }
        } catch (KiwiException e) {
            return ui.showError(e.getMessage());
        }
    }

    private String handleBye() throws KiwiException {
        storage.save(tasks.getAllTasks());
        return ui.showGoodbye();
    }

    private String handleList() throws KiwiException {
        return ui.printTasks(tasks);
    }

    private String handleMark(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        tasks.markTask(index);
        storage.save(tasks.getAllTasks());
        return ui.showMarkMessage(tasks.getTask(index));
    }

    private String handleUnmark(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        tasks.unmarkTask(index);
        storage.save(tasks.getAllTasks());
        return ui.showUnmarkMessage(tasks.getTask(index));
    }

    private String handleDelete(String arguments) throws KiwiException {
        int index = Parser.parseIndex(arguments, tasks.size());
        Task removedTask = tasks.deleteTask(index);
        storage.save(tasks.getAllTasks());
        return ui.showDeleteMessage(removedTask, tasks.size());
    }

    private String handleTodo(String arguments) throws KiwiException {
        if (arguments.isEmpty()) {
            throw new KiwiException("Todo description cannot be empty!");
        }
        Task task = new Todo(arguments);
        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        return ui.showAddMessage(task, tasks.size());
    }

    private String handleDeadline(String arguments) throws KiwiException {
        String[] parts = Parser.parseDeadlineArgs(arguments);
        Task task = new Deadline(parts[0], parts[1]);
        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        return ui.showAddMessage(task, tasks.size());
    }

    private String handleEvent(String arguments) throws KiwiException {
        String[] parts = Parser.parseEventArgs(arguments);
        Task task = new Event(parts[0], parts[1], parts[2]);
        tasks.addTask(task);
        storage.save(tasks.getAllTasks());
        return ui.showAddMessage(task, tasks.size());
    }

    private String handleFind(String arguments) throws KiwiException {
        if (arguments.isEmpty()) {
            throw new KiwiException("Please specify a search keyword!");
        }
        TaskList matchingTasks = tasks.findTasks(arguments);
        return ui.showFoundTasks(matchingTasks);
    }

    private String handleEdit(String arguments) throws KiwiException {
        Map<String, String> updates = Parser.parseEditArgs(arguments);
        int index = Integer.parseInt(updates.get("index"));

        Task originalTask = tasks.getTask(index);
        Task updatedTask = createUpdatedTask(originalTask, updates);

        tasks.replaceTask(index, updatedTask);
        storage.save(tasks.getAllTasks());
        return ui.showEditSuccess(updatedTask);
    }

    private Task createUpdatedTask(Task original, Map<String, String> updates) throws KiwiException {
        if (original instanceof Todo) {
            return updateTodo((Todo) original, updates);
        } else if (original instanceof Deadline) {
            return updateDeadline((Deadline) original, updates);
        } else if (original instanceof Event) {
            return updateEvent((Event) original, updates);
        }
        throw new KiwiException("Cannot edit unknown task type");
    }

    private Todo updateTodo(Todo original, Map<String, String> updates) throws KiwiException {
        String newDesc = updates.getOrDefault("desc", original.getDescription());
        return new Todo(newDesc);
    }

    private Deadline updateDeadline(Deadline original, Map<String, String> updates) throws KiwiException {
        String newDesc = updates.getOrDefault("desc", original.getDescription());
        String newBy = updates.getOrDefault("by", original.getBy());
        return new Deadline(newDesc, newBy);
    }

    private Event updateEvent(Event original, Map<String, String> updates) throws KiwiException {
        String newDesc = updates.getOrDefault("desc", original.getDescription());
        String newFrom = updates.getOrDefault("from", original.getFrom());
        String newTo = updates.getOrDefault("to", original.getTo());
        return new Event(newDesc, newFrom, newTo);
    }
}
