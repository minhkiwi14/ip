package kiwi.task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

import kiwi.exception.KiwiException;

/**
 * Represents a task with a specific deadline containing both date and optional time components.
 * Formats deadlines as "MMM d yyyy" (e.g., Oct 10 2023) and times as "h a" (e.g., 3PM) in output.
 * Inherits base task functionality from {@link Task}.
 */
public class Deadline extends Task {
    protected LocalDate date;
    protected LocalTime time;

    /**
     * Constructs a deadline task from description and date/time string.
     * Handles missing time by defaulting to 23:59. Input format: "yyyy-MM-dd [HH:mm]".
     *
     * @param description Task description
     * @param by          Combined date and optional time string (e.g., "2023-10-10 15:00")
     */
    public Deadline(String description, String by) throws KiwiException {
        super(description);
        try {
            String[] dateTimeParts = by.split(" ", 2);
            this.date = LocalDate.parse(dateTimeParts[0]);

            this.time = dateTimeParts.length > 1
                    ? LocalTime.parse(dateTimeParts[1])
                    : LocalTime.of(23, 59);
        } catch (DateTimeParseException e) {
            throw new KiwiException("Invalid date/time format. Expected format: yyyy-MM-dd [HH:mm]");
        }
    }

    /**
     * Returns formatted string with deadline details.
     * Includes time only if not default (23:59). Example output:
     * [D][X] Submit report (by: Oct 10 2023 3PM)
     */
    @Override
    public String toString() {
        // Solution adapted from ChatGPT with the prompt:
        // "How to print the time of 15:00 as 3PM using DateTimeFormatter?"
        String timeString = (time.equals(LocalTime.of(23, 59)))
                ? ""
                : " " + time.format(DateTimeFormatter.ofPattern("h a", Locale.ENGLISH)).replace(" ", "");

        return "[D]" + super.toString()
                + " (by: " + date.format(DateTimeFormatter.ofPattern("MMM d yyyy"))
                + timeString + ")";
    }

    /**
     * Returns machine-readable date/time string in "yyyy-MM-dd HH:mm" format.
     * Preserves default 23:59 time if originally unspecified.
     */
    public String getDateTime() {
        return date + " " + time;
    }

    public String getBy() {
        return date.toString() + " " + time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * Generates file storage entry with format:
     * D | [0/1] | [description] | yyyy-MM-dd HH:mm
     * Ensures 24-hour time format for reliable parsing.
     */
    @Override
    public String toFileFormat() {
        return String.format("D | %d | %s | %s %s",
                isDone ? 1 : 0,
                description,
                date,
                time.format(DateTimeFormatter.ofPattern("HH:mm")));
    }
}
