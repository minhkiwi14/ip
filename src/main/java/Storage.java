import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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