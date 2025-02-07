package kiwi.ui;

import kiwi.command.TaskList;
import kiwi.exception.KiwiException;

public class Ui {
    public String showWelcome() {
        return "Hello! I'm Kiwi, your friendly task manager!\nWhat can I do for you?";
    }

    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    public String showMarkMessage(kiwi.task.Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    public String showUnmarkMessage(kiwi.task.Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    public String showDeleteMessage(kiwi.task.Task task, int size) {
        return "Noted. I've removed this task:\n  " + task + "\nNow you have " + size + " tasks in the list.";
    }

    public String showAddMessage(kiwi.task.Task task, int size) {
        return "Got it. I've added this task:\n  " + task + "\nNow you have " + size + " tasks in the list.";
    }

    public String printTasks(TaskList tasks) throws KiwiException {
        if (tasks.size() == 0) {
            return "Your task list is empty!";
        } else {
            StringBuilder sb = new StringBuilder("Here are the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((i + 1) + "." + tasks.getTask(i) + "\n");
            }
            return sb.toString().trim();
        }
    }

    public String showError(String message) {
        return "Error: " + message;
    }

    public String showLoadingError() {
        return "Error loading tasks from file. Starting with an empty task list.";
    }

    public String showFoundTasks(TaskList matchingTasks) throws KiwiException {
        if (matchingTasks.size() == 0) {
            return "No tasks found matching your search.";
        } else {
            StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append((i + 1) + "." + matchingTasks.getTask(i) + "\n");
            }
            return sb.toString().trim();
        }
    }
}