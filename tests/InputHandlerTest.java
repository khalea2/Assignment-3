package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerTest {

    @Test
    public void testParseArgsSuccess() {
        // Test successful parsing of command line arguments
        InputHandler handler = new InputHandler();
        String[] args = { "-i", "maze.txt" };

        boolean result = handler.parseArgs(args);

        assertTrue(result);
        assertEquals("maze.txt", handler.getInputFilePath());
        assertNull(handler.getMazePath());
    }

    @Test
    public void testParseArgsWithPath() {
        // Test parsing with both input file and path
        InputHandler handler = new InputHandler();
        String[] args = { "-i", "maze.txt", "-p", "FFLRF" };

        boolean result = handler.parseArgs(args);

        assertTrue(result);
        assertEquals("maze.txt", handler.getInputFilePath());
        assertEquals("FFLRF", handler.getMazePath());
    }
}