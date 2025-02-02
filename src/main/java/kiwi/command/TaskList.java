package kiwi.command;

import kiwi.exception.KiwiException;
import kiwi.task.Task;

import java.util.ArrayList;

/**
 * Manages a list of {@link Task} objects, providing methods to manipulate and access tasks.
 * Supports adding, removing, marking, unmarking, and retrieving tasks by index.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a {@code TaskList} initialized with the provided list of tasks.
     *
     * @param tasks The initial list of tasks to populate the task list.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns a task from the task list by its 0-based index.
     *
     * @param index The 0-based index of the task to remove.
     * @return The removed task.
     * @throws KiwiException If the index is out of bounds (less than 0 or greater than/equal to list size).
     */
    public Task deleteTask(int index) throws KiwiException {
        try {
            return tasks.remove(index);
        } catch (IndexOutOfBoundsException e) {
            throw new KiwiException("Invalid task number!");
        }
    }

    /**
     * Retrieves a task from the task list by its 0-based index.
     *
     * @param index The 0-based index of the task to retrieve.
     * @return The task at the specified index.
     * @throws KiwiException If the index is out of bounds.
     */
    public Task getTask(int index) throws KiwiException {
        try {
            return tasks.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new KiwiException("Invalid task number!");
        }
    }

    /**
     * Marks the task at the specified 0-based index as done.
     *
     * @param index The 0-based index of the task to mark.
     * @throws KiwiException If the index is out of bounds.
     */
    public void markTask(int index) throws KiwiException {
        Task task = getTask(index);
        task.markAsDone();
    }

    /**
     * Marks the task at the specified 0-based index as not done.
     *
     * @param index The 0-based index of the task to unmark.
     * @throws KiwiException If the index is out of bounds.
     */
    public void unmarkTask(int index) throws KiwiException {
        Task task = getTask(index);
        task.markAsUndone();
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The current size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the internal list of tasks.
     * Note: This provides direct access to the underlying list; modifications will affect the original data.
     *
     * @return The full list of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }
}