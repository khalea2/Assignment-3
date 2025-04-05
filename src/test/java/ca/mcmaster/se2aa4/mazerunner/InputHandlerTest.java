package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerTest {

    @BeforeEach
    public void setup() {
        // reset the singleton instance between tests
        InputHandler.reset();
    }

    @Test
    public void testParseArgsSuccess() {
        // test successful parsing of command line arguments
        InputHandler handler = InputHandler.getInstance();
        String[] args = { "-i", "maze.txt" };

        boolean result = handler.parseArgs(args);

        assertTrue(result);
        assertEquals("maze.txt", handler.getInputFilePath());
        assertNull(handler.getMazePath());
    }

    @Test
    public void testParseArgsWithPath() {
        // test parsing with both input file and path
        InputHandler handler = InputHandler.getInstance();
        String[] args = { "-i", "maze.txt", "-p", "FFLRF" };

        boolean result = handler.parseArgs(args);

        assertTrue(result);
        assertEquals("maze.txt", handler.getInputFilePath());
        assertEquals("FFLRF", handler.getMazePath());
    }
}