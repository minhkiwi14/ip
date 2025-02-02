package kiwi.storage;

import kiwi.exception.KiwiException;
import kiwi.task.Deadline;
import kiwi.task.Event;
import kiwi.task.Task;
import kiwi.task.Todo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles persistent storage of tasks by reading from and writing to a file.
 * Supports loading tasks from a predefined file format and saving tasks in a format
 * suitable for later reconstruction.
 */
public class Storage {
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
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }

        try {
            if (parts.length < 3)
                return null;

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];

            Task task = null;

            switch (type) {
            case "T":
                if (parts.length < 3)
                    throw new KiwiException("Invalid todo format");
                task = new Todo(description);
                break;

            case "D":
                if (parts.length < 4)
                    throw new KiwiException("Invalid deadline format");
                String by = parts[3];
                task = new Deadline(description, by);
                break;

            case "E":
                if (parts.length < 5)
                    throw new KiwiException("Invalid event format");
                String from = parts[3];
                String to = parts[4];
                task = new Event(description, from, to);
                break;

            default:
                return null;
            }

            if (task != null && isDone) {
                task.markAsDone();
            }
            return task;

        } catch (DateTimeParseException e) {
            throw new KiwiException("Invalid date/time format in line: " + line);
        } catch (IndexOutOfBoundsException e) {
            throw new KiwiException("Missing fields in line: " + line);
        } catch (Exception e) {
            throw new KiwiException("Error parsing line: " + line + " - " + e.getMessage());
        }
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
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(task.toFileFormat() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new KiwiException("Error saving tasks to file");
        }
    }
}