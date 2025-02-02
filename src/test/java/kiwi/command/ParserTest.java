package kiwi.command;

import kiwi.exception.KiwiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ParserTest {
    @Test
    public void testParseDeadline_success() throws KiwiException {
        String deadline = "doing homework /by 2025-04-02 23:00";

        assertEquals("doing homework", Parser.parseDeadlineArgs(deadline)[0]);
        assertEquals("2025-04-02 23:00", Parser.parseDeadlineArgs(deadline)[1]);
    }

    @Test
    public void testParseDeadline_exceptionThrown() throws KiwiException {
        String d1 = "doing homework by 2025-04-02 23:00";

        try {
            assertEquals("doing homework", Parser.parseDeadlineArgs(d1)[0]);
            fail();
        } catch (KiwiException exception) {
            assertEquals("Invalid deadline format! Use: deadline <description> /by <date> <time>",
                    exception.getMessage());
        }
    }
}
