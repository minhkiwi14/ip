package kiwi.ui;

import kiwi.command.TaskList;
import kiwi.exception.KiwiException;
import kiwi.task.Task;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ui {
    public String showWelcome() {
        return "Hello! I'm Kiwi, your friendly task manager!\nWhat can I do for you?";
    }

    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    public String showMarkMessage(Task task) {
        return "Nice! I've marked this task as done:\n  " + task;
    }

    public String showUnmarkMessage(Task task) {
        return "OK, I've marked this task as not done yet:\n  " + task;
    }

    public String showDeleteMessage(Task task, int size) {
        return "Noted. I've removed this task:\n  " + task + "\nNow you have " + size + " tasks in the list.";
    }

    public String showAddMessage(Task task, int size) {
        return "Got it. I've added this task:\n  " + task + "\nNow you have " + size + " tasks in the list.";
    }

    public String showEditMessage(Task task, int size) {
        return "I've edited this task to:\n  " + task;
    }

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
     * Shows success message after editing a task
     *
     * @param task The updated task
     * @return Formatted success message
     */
    public String showEditSuccess(Task task) {
        return "Task updated successfully!\n    " + task;
    }
}