import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Deadline extends Task {
    protected LocalDate date;
    protected LocalTime time;

    public Deadline(String description, String by) {
        super(description);
        String[] dateTimeParts = by.split(" ", 2);
        this.date = LocalDate.parse(dateTimeParts[0]);

        // Solution adapted from ChatGPT with the prompt:
        // "How to handle the case where time is missing?"
        this.time = dateTimeParts.length > 1
                ? LocalTime.parse(dateTimeParts[1])
                : LocalTime.of(23, 59);
    }

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

    public String getDateTime() {
        return date + " " + time;
    }
}
