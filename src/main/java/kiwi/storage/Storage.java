package kiwi.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import kiwi.exception.KiwiException;
import kiwi.task.Deadline;
import kiwi.task.Event;
import kiwi.task.Task;
import kiwi.task.Todo;

/**
 * Handles persistent storage of tasks by reading from and writing to a file.
 * Supports loading tasks from a predefined file format and saving tasks in a format
 * suitable for later reconstruction.
 */
public class Storage {
    private static final String TASK_TYPE_TODO = "T";
    private static final String TASK_TYPE_DEADLINE = "D";
    private static final String TASK_TYPE_EVENT = "E";

    private static final String TASK_COMPLETED = "1";

    private static final int MIN_TODO_PARTS = 3;
    private static final int MIN_DEADLINE_PARTS = 4;
    private static final int MIN_EVENT_PARTS = 5;

    private final String filePath;

    /**
     * Creates a Storage instance associated with the specified file path.
     *
     * @param filePath The path to the file used for task persistence.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file. Creates an empty file if none exists.
     *
     * @return An ArrayList of reconstructed Task objects.
     * @throws KiwiException If file reading fails or data formatting errors occur.
     */
    public ArrayList<Task> load() throws KiwiException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return tasks;
            }

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Task task = parseLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new KiwiException("Error loading tasks from file");
        }
        return tasks;
    }

    /**
     * Parses a single file line into a Task object.
     * Expected formats:
     * <ul>
     *   <li>Todo: T | [0/1] | [description]</li>
     *   <li>Deadline: D | [0/1] | [description] | [dueDateTime]</li>
     *   <li>Event: E | [0/1] | [description] | [start] | [end]</li>
     * </ul>
     *
     * @param line A line from the storage file
     * @return Reconstructed Task object, or null for invalid/empty lines
     * @throws KiwiException For malformed lines, invalid dates, or missing fields
     */
    private Task parseLine(String line) throws KiwiException {
        String[] parts = line.split("\\|");
        trimParts(parts);

        if (parts.length < MIN_TODO_PARTS) {
            return null;
        }

        String type = parts[0];
        boolean isDone = parseIsDone(parts[1]);
        String description = parts[2];

        Task task = switch (type) {
        case TASK_TYPE_TODO -> parseTodo(description);
        case TASK_TYPE_DEADLINE -> parseDeadline(parts);
        case TASK_TYPE_EVENT -> parseEvent(parts);
        default -> null;
        };

        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }

    private boolean parseIsDone(String status) {
        return TASK_COMPLETED.equals(status);
    }

    private Task parseTodo(String description) throws KiwiException {
        if (description.isEmpty()) {
            throw new KiwiException("Invalid todo format");
        }
        return new Todo(description);
    }

    private Task parseDeadline(String[] parts) throws KiwiException {
        if (parts.length < MIN_DEADLINE_PARTS) {
            throw new KiwiException("Invalid deadline format");
        }
        String description = parts[2];
        String by = parts[3];
        return new Deadline(description, by);
    }

    private Task parseEvent(String[] parts) throws KiwiException {
        if (parts.length < MIN_EVENT_PARTS) {
            throw new KiwiException("Invalid event format");
        }
        String description = parts[2];
        String from = parts[3];
        String to = parts[4];
        return new Event(description, from, to);
    }

    /**
     * Saves all tasks to the storage file in a machine-readable format.
     * Overwrites existing file contents completely.
     *
     * @param tasks The list of tasks to persist
     * @throws KiwiException If file writing fails
     */
    public void save(ArrayList<Task> tasks) throws KiwiException {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new KiwiException("Error saving tasks to file");
        }
    }

    /**
     * Trims leading and trailing whitespaces from each element in the array.
     */
    private void trimParts(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
    }
}
