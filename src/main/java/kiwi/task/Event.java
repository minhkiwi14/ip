package kiwi.task;

/**
 * Represents an event task with a start and end time/duration.
 * Inherits base task functionality from {@link Task}.
 * Events are stored and displayed with user-provided time descriptors without parsing.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates an event task with description and time range.
     *
     * @param description Task summary
     * @param from        Start time description (free-form text)
     * @param to          End time description (free-form text)
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Formats event for display as: [E][Status] Description (from: X to: Y)
     * Inherits status icon formatting from {@link Task#toString()}.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    /**
     * Generates file storage entry with format:
     * E | [0/1] | [description] | [from] | [to]
     * Preserves original user input for from/to values.
     */
    @Override
    public String toFileFormat() {
        return String.format("E | %d | %s | %s | %s",
                isDone ? 1 : 0,
                description,
                from,
                to);
    }
}
