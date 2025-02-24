package kiwi.command;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kiwi.exception.KiwiException;

/**
 * Provides utility methods for parsing user input arguments into specific components required by the application.
 */
public class Parser {
    /**
     * Parses a task index from the input arguments and validates it against the current task count.
     * Converts a 1-based index (user input) to a 0-based index (internal representation).
     *
     * @param arguments The input string representing the task number.
     * @param taskCount The total number of tasks currently available.
     * @return The validated 0-based index of the task.
     * @throws KiwiException If the input is not a valid integer, or if the index is out of bounds.
     */
    public static int parseIndex(String arguments, int taskCount) throws KiwiException {
        try {
            int index = Integer.parseInt(arguments) - 1;
            if (index < 0 || index >= taskCount) {
                throw new KiwiException("Invalid task number! Use 1-" + taskCount);
            }
            return index;
        } catch (NumberFormatException e) {
            throw new KiwiException("Please enter a valid task number");
        }
    }

    /**
     * Parses arguments for a deadline command into its description and date/time components.
     * Expects the input format: "&lt;description&gt; /by &lt;date&gt; &lt;time&gt;".
     *
     * @param arguments The input string containing the deadline description and time.
     * @return An array of two strings: [description, dateTime].
     * @throws KiwiException If the input does not conform to the expected format.
     */
    public static String[] parseDeadlineArgs(String arguments) throws KiwiException {
        String[] parts = arguments.split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new KiwiException("Invalid deadline format! Use: deadline <description> /by <date> <time>");
        }

        return new String[]{parts[0].trim(), parts[1].trim()};
    }

    /**
     * Parses arguments for an event command into its description, start time, and end time components.
     * Expects the input format: "&lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;".
     *
     * @param arguments The input string containing the event description and time range.
     * @return An array of three strings: [description, startTime, endTime].
     * @throws KiwiException If the input does not contain valid "/from" and "/to" delimiters,
     *                       or if any component is missing.
     */
    public static String[] parseEventArgs(String arguments) throws KiwiException {
        String[] parts = arguments.split("/from", 2);
        if (parts.length < 2) {
            throw new KiwiException("Invalid event format! Use: event <description> /from <start> /to <end>");
        }

        String[] timeParts = parts[1].split("/to", 2);
        if (timeParts.length < 2) {
            throw new KiwiException("Invalid event format! Use: event <description> /from <start> /to <end>");
        }

        return new String[]{
                parts[0].trim(),
                timeParts[0].trim(),
                timeParts[1].trim()
        };
    }

    /**
     * Parses arguments for an edit command into its index and update fields.
     * Expects the input format: "index [/desc DESCRIPTION] [/by DEADLINE] [/from START] [/to END]".
     *
     * @param arguments The input string containing the edit parameters
     * @return An array where first element is index and subsequent elements are key-value pairs
     * @throws KiwiException If the input format is invalid
     */
    public static Map<String, String> parseEditArgs(String arguments) throws KiwiException {
        Map<String, String> updates = new HashMap<>();
        String[] parts = arguments.split("\\s+/(desc|by|from|to)\\s+", -1);

        if (parts.length < 1) {
            throw new KiwiException("Invalid edit format!"
                    + "Use: edit <index> [/desc ...] [/by ...] [/from ...] [/to ...]");
        }

        try {
            int index = Integer.parseInt(parts[0].trim()) - 1;
            updates.put("index", String.valueOf(index));
        } catch (NumberFormatException e) {
            throw new KiwiException("Invalid task number format!");
        }

        Pattern pattern = Pattern.compile("/(desc|by|from|to)\\s+([^/]+)");
        Matcher matcher = pattern.matcher(arguments);

        while (matcher.find()) {
            String field = matcher.group(1);
            String value = matcher.group(2).trim();
            updates.put(field, value);
        }

        if (updates.size() == 1) { // Only index present
            throw new KiwiException("No fields to update! Use at least one of: /desc, /by, /from, /to");
        }

        return updates;
    }
}
