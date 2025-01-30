import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws KiwiException {
        ArrayList<Task> tasks = new ArrayList<>(100);
        File file = new File(filePath);
        if (!file.exists()) {
            return tasks;
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().strip();
                String[] parts = line.split("\\|");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                if (parts.length < 3) {
                    // System.out.println("The format is not correct. Please double-check!");
                    continue;
                }

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                Task task = null;

                // Solution below adapted from ChatGPT with the prompt:
                // "Handle each case of events with switch-case statements"
                switch (type) {
                case "T":
                    task = new Todo(description);
                    break;
                case "D":
                    if (parts.length < 4) {
                        continue;
                    }
                    String by = parts[3];
                    task = new Deadline(description, by);
                    break;
                case "E":
                    if (parts.length < 5) {
                        continue;
                    }
                    String from = parts[3];
                    String to = parts[4];
                    task = new Event(description, from, to);
                    break;
                default:
                    continue;
                }

                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }

            scanner.close();
        } catch (FileNotFoundException exception) {
            throw new KiwiException("The file does not exist. Please try again!");
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws KiwiException {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();

            // Solution below adapted from ChatGPT with the prompt:
            // "Handle the case when the directory does not exist"
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            FileWriter filewriter = new FileWriter(file);
            for (Task task : tasks) {
                String line = getLine(task);
                filewriter.write(line + System.lineSeparator());
            }
            filewriter.close();
        } catch (IOException e) {
            throw new KiwiException("I cannot save the tasks correctly.");
        }
    }

    private static String getLine(Task task) {
        String line = "";
        if (task instanceof Todo todo) {
            line = String.format("T | %d | %s",
                    todo.isDone() ? 1 : 0,
                    todo.getDescription());
        } else if (task instanceof Deadline deadline) {
            line = String.format("D | %d | %s | %s",
                    deadline.isDone() ? 1 : 0,
                    deadline.getDescription(),
                    deadline.getBy());
        } else if (task instanceof Event event) {
            line = String.format("E | %d | %s | %s | %s",
                    event.isDone() ? 1 : 0,
                    event.getDescription(),
                    event.getFrom(),
                    event.getTo());
        }
        return line;
    }
}