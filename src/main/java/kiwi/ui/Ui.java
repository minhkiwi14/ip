package kiwi.ui;

import kiwi.command.TaskList;
import kiwi.exception.KiwiException;
import kiwi.task.Task;

public class Ui {
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

    public void showGoodbye() {
        showMessage("Au revoir! I'm happy to serve you today. See you again soon!");
    }

    public void showMessage(String message) {
        System.out.println("    " + message);
    }

    public void showError(String message) {
        System.out.println("    Error: " + message);
    }

    public void showDivider() {
        System.out.println("    ______________________________");
    }

    public void showLoadingError() {
        showError("Error loading tasks. Starting with empty list.");
    }

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

    public void showMarkMessage(Task task) {
        showMessage("Congratulations on having done it!\n    " + task);
    }

    public void showUnmarkMessage(Task task) {
        showMessage("Uh oh! Do it soon!\n    " + task);
    }

    public void showDeleteMessage(Task task, int remainingTasks) {
        showMessage("OK! I've removed this task:\n    " + task);
        showMessage("Now you have " + remainingTasks + " tasks in the list");
    }

    public void showAddMessage(Task task, int totalTasks) {
        showMessage("Got it! I've added your task:\n    " + task);
        showMessage("Now you have " + totalTasks + " tasks in the list");
    }
}