package kiwi.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EventTest {
    @Test
    public void testFileFormatConversion() {
        Event e1 = new Event("team meeting", "3pm", "4pm");
        Event e2 = new Event("watch tennis", "4pm", "5pm");
        e2.markAsDone();

        assertEquals("E | 0 | team meeting | 3pm | 4pm", e1.toFileFormat());
        assertEquals("E | 1 | watch tennis | 4pm | 5pm", e2.toFileFormat());
    }
}
