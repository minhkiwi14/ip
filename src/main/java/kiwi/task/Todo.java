package kiwi.task;

/**
 * Represents a basic task without any date/time constraints.
 * Inherits core task functionality from {@link Task}.
 * Todo tasks are stored with minimal formatting requirements.
 */
public class Todo extends Task {
    /**
     * Creates a todo task with the specified description.
     *
     * @param description Text summary of the task
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Formats todo for display as: [T][Status] Description
     * Example: [T][X] Buy milk
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Generates file storage entry with format:
     * T | [0/1] | [description]
     * Where 0=not done, 1=done
     */
    @Override
    public String toFileFormat() {
        return String.format("T | %d | %s", isDone ? 1 : 0, description);
    }
}
