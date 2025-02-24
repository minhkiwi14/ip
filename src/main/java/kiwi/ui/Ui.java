package kiwi.ui;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import kiwi.command.TaskList;
import kiwi.exception.KiwiException;
import kiwi.task.Task;

/**
 * The Ui class handles user interaction and displays messages
 * related to task management in the Kiwi chatbot.
 */
public class Ui {

    /**
     * Returns a welcome message for the user.
     *
     * @return The welcome message.
     */
    public String showWelcome() {
        return "Hello! I'm Kiwi, your friendly task manager!\nWhat can I do for you?";
    }

    /**
     * Returns a goodbye message for the user.
     *
     * @return The goodbye message.
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns a message indicating that a task has been marked as done.
     *
     * @param task The task that was marked as done.
     * @return The message indicating the task is marked as done.
     */
    public String showMarkMessage(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    /**
     * Returns a message indicating that a task has been marked as not done.
     *
     * @param task The task that was marked as not done.
     * @return The message indicating the task is marked as not done.
     */
    public String showUnmarkMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    /**
     * Returns a message indicating that a task has been deleted.
     *
     * @param task The task that was deleted.
     * @param size The number of tasks remaining in the task list.
     * @return The message indicating the task is deleted.
     */
    public String showDeleteMessage(Task task, int size) {
        return "Noted. I've removed this task:\n  " + task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns a message indicating that a new task has been added.
     *
     * @param task The task that was added.
     * @param size The number of tasks in the task list after adding the new task.
     * @return The message indicating the task is added.
     */
    public String showAddMessage(Task task, int size) {
        return "Got it. I've added this task:\n  " + task + "\nNow you have " + size + " tasks in the list.";
    }

    /**
     * Returns a message indicating that a task has been edited.
     *
     * @param task The task that was edited.
     * @param size The number of tasks in the task list.
     * @return The message indicating the task is edited.
     */
    public String showEditMessage(Task task, int size) {
        return "I've edited this task to:\n  " + task;
    }

    /**
     * Returns a list of tasks in the task list.
     *
     * @param tasks The TaskList containing tasks.
     * @return A formatted string listing all tasks.
     * @throws KiwiException If there is an error retrieving tasks.
     */
    public String printTasks(TaskList tasks) throws KiwiException {
        if (tasks.size() == 0) {
            return "Your task list is empty!";
        } else {
            String tasksList = IntStream.range(0, tasks.size())
                    .mapToObj(i -> {
                        try {
                            return (i + 1) + "." + tasks.getTask(i);
                        } catch (KiwiException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.joining("\n"));
            return ("Here are the tasks in your list:\n" + tasksList).trim();
        }
    }

    /**
     * Returns an error message.
     *
     * @param message The error message to display.
     * @return The formatted error message.
     */
    public String showError(String message) {
        return "Error: " + message;
    }

    /**
     * Returns a message indicating that there was an error loading tasks.
     *
     * @return The error loading message.
     */
    public String showLoadingError() {
        return "Error loading tasks from file. Starting with an empty task list.";
    }

    /**
     * Returns a list of tasks that match the search criteria.
     *
     * @param matchingTasks The TaskList containing matching tasks.
     * @return A formatted string listing all matching tasks.
     * @throws KiwiException If there is an error retrieving tasks.
     */
    public String showFoundTasks(TaskList matchingTasks) throws KiwiException {
        if (matchingTasks.size() == 0) {
            return "No tasks found matching your search.";
        } else {
            String tasksList = IntStream.range(0, matchingTasks.size())
                    .mapToObj(i -> {
                        try {
                            return (i + 1) + "." + matchingTasks.getTask(i);
                        } catch (KiwiException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.joining("\n"));
            return ("Here are the matching tasks in your list:\n" + tasksList).trim();
        }
    }

    /**
     * Shows success message after editing a task.
     *
     * @param task The updated task.
     * @return Formatted success message.
     */
    public String showEditSuccess(Task task) {
        return "Task updated successfully!\n    " + task;
    }
}
