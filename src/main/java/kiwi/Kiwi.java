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

public class Kiwi {
    private final Ui ui;
    private final Storage storage;
    private TaskList tasks;
    private boolean loadError = false;
    private String loadErrorMessage = "";

    public Kiwi(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (KiwiException e) {
            loadError = true;
            loadErrorMessage = ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public String getGreeting() {
        String greeting = ui.showWelcome();
        if (loadError) {
            greeting += "\n" + loadErrorMessage;
        }
        return greeting;
    }

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
        return ui.showEditMessage(tasks.getTask(0), tasks.size());
    }
}
