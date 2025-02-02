package kiwi.command;

import kiwi.exception.KiwiException;

public class Parser {
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

    public static String[] parseDeadlineArgs(String arguments) throws KiwiException {
        String[] parts = arguments.split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new KiwiException("Invalid deadline format! Use: deadline <description> /by <date> <time>");
        }
        
        return new String[]{parts[0].trim(), parts[1].trim()};
    }

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
}