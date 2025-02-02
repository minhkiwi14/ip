package kiwi.command;

import kiwi.exception.KiwiException;
import kiwi.task.Task;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int index) throws KiwiException {
        try {
            return tasks.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new KiwiException("Invalid task number!");
        }
    }

    public Task getTask(int index) throws KiwiException {
        try {
            return tasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new KiwiException("Invalid task number!");
        }
    }

    public void markTask(int index) throws KiwiException {
        Task task = getTask(index);
        task.markAsDone();
    }

    public void unmarkTask(int index) throws KiwiException {
        Task task = getTask(index);
        task.markAsUndone();
    }

    /**
     * Finds tasks containing the specified keyword in their description.
     * The search is case-insensitive.
     *
     * @param keyword The search term to match
     * @return A new TaskList containing all matching tasks
     */
    public TaskList findTasks(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        String searchTerm = keyword.toLowerCase();

        for (Task task : tasks) {
            if (task.getDescription().toLowerCase().contains(searchTerm)) {
                matches.add(task);
            }
        }
        return new TaskList(matches);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}