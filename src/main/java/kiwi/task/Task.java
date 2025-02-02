package kiwi.task;

/**
 * Represents a generic task with a description and completion status.
 * Serves as the base class for specific task types (Todo, Deadline, Event).
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description Textual description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + description;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    /**
     * Converts the task to a standardized format for file storage.
     * Must be implemented by concrete subclasses.
     *
     * @return A string representation suitable for persistent storage
     */
    public String toFileFormat() {
        return "";
    }
}
