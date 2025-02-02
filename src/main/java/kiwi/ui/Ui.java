package kiwi.ui;

import kiwi.command.TaskList;
import kiwi.exception.KiwiException;
import kiwi.task.Task;

/**
 * The Ui class handles all user interactions, such as displaying messages and errors,
 * and printing tasks in the list.
 */
public class Ui {
    /**
     * Displays a welcome message with the Kiwi logo.
     */
    public void showWelcome() {
        String logo = " _   __  _              _    __     __\n"
                + "| | / / |_|            |_|  /  \\   /  \\\n"
                + "| |/ /   _  __      __  _  /_/\\_\\ /_/\\_\\\n"
                + "|   <   | | \\ \\    / / | |\n"
                + "| |\\ \\  | |  \\ \\/\\/ /  | |\n"
                + "|_| \\_\\ |_|   \\_/\\_/   |_|\n";
        System.out.println("Hello from\n" + logo);
        showMessage("Bonjour! What should I do now?");
    }

    /**
     * Displays a goodbye message when the user exits the program.
     */
    public void showGoodbye() {
        showMessage("Au revoir! I'm happy to serve you today. See you again soon!");
    }

    /**
     * Displays a generic message to the user.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        System.out.println("    " + message);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        System.out.println("    Error: " + message);
    }

    /**
     * Displays a divider line for better visual separation in the output.
     */
    public void showDivider() {
        System.out.println("    ______________________________");
    }

    /**
     * Displays an error message when loading tasks fails.
     */
    public void showLoadingError() {
        showError("Error loading tasks. Starting with empty list.");
    }

    /**
     * Prints all tasks in the given task list.
     *
     * @param tasks The list of tasks to be printed.
     */
    public void printTasks(TaskList tasks) {
        System.out.println("    Here are your tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            try {
                System.out.println("    " + (i + 1) + ". " + tasks.getTask(i));
            } catch (KiwiException e) {
                System.out.println("    Error displaying task " + (i + 1) + ": " + e.getMessage());
            }
        }
    }

    /**
     * Displays the list of tasks matching a search query.
     * Shows a special message if no matches are found.
     *
     * @param matchingTasks The list of tasks that match the search criteria
     */
    public void showFoundTasks(TaskList matchingTasks) {
        if (matchingTasks.size() == 0) {
            showMessage("No tasks found matching your search.");
        } else {
            System.out.println("    Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                try {
                    System.out.println("    " + (i + 1) + ". " + matchingTasks.getTask(i));
                } catch (KiwiException e) {
                    System.out.println("    Error displaying task " + (i + 1));
                }
            }
        }
    }

    /**
     * Displays a message when a task is marked as done.
     *
     * @param task The task that was marked as done.
     */
    public void showMarkMessage(Task task) {
        showMessage("Congratulations on having done it!\n    " + task);
    }

    /**
     * Displays a message when a task is unmarked (set as not done).
     *
     * @param task The task that was unmarked.
     */
    public void showUnmarkMessage(Task task) {
        showMessage("Uh oh! Do it soon!\n    " + task);
    }

    /**
     * Displays a message when a task is deleted, and shows the remaining number of tasks.
     *
     * @param task           The task that was deleted.
     * @param remainingTasks The number of tasks left after deletion.
     */
    public void showDeleteMessage(Task task, int remainingTasks) {
        showMessage("OK! I've removed this task:\n    " + task);
        showMessage("Now you have " + remainingTasks + " tasks in the list.");
    }

    /**
     * Displays a message when a new task is added, and shows the total number of tasks.
     *
     * @param task       The task that was added.
     * @param totalTasks The total number of tasks after the addition.
     */
    public void showAddMessage(Task task, int totalTasks) {
        showMessage("Got it! I've added your task:\n    " + task);
        showMessage("Now you have " + totalTasks + " tasks in the list.");
    }
}
