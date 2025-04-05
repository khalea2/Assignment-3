package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class AllTests {

    @BeforeEach
    public void setup() {
        // reset the singleton instance between tests
        InputHandler.reset();
    }

    // ====== additional path tests ======

    @Test
    public void testEmptyPath() {
        // test creating a path with empty list
        List<String> moves = Arrays.asList();
        Path path = new Path(moves);

        assertEquals("", path.getFormattedPath());
        assertEquals("", path.getFactorizedPath());
    }

    @Test
    public void testSingleMovePath() {
        // test with a single move
        List<String> moves = Arrays.asList("F");
        Path path = new Path(moves);

        assertEquals("F", path.getFormattedPath());
        assertEquals("F", path.getFactorizedPath());
    }

    @Test
    public void testComplexPath() {
        // test with a more complex path
        List<String> moves = Arrays.asList("F", "F", "L", "L", "F", "F", "F", "R", "R", "F", "F");
        Path path = new Path(moves);

        assertEquals("FF LL FFF RR FF", path.getFormattedPath());
        assertEquals("2F 2L 3F 2R 2F", path.getFactorizedPath());
    }

    @Test
    public void testMixedPathFormat() {
        // test with mixed spacing in the input path
        String inputPath = "2F  L   3F R";
        Path path = new Path(inputPath);

        assertEquals("FF L FFF R", path.getFormattedInputtedPath());
    }

    // ====== additional inputHandler tests ======

    @Test
    public void testParseEmptyArgs() {
        // test with empty arguments
        InputHandler handler = InputHandler.getInstance();
        String[] args = {};

        boolean result = handler.parseArgs(args);

        // might return true with empty args (succeeds but no options set)
        assertTrue(result);
        assertNull(handler.getInputFilePath());
        assertNull(handler.getMazePath());
    }

    @Test
    public void testParseInvalidOption() {
        // test with an invalid option
        InputHandler handler = InputHandler.getInstance();
        String[] args = { "-x", "something" };

        boolean result = handler.parseArgs(args);

        // commons CLI appears to return false for unknown options
        assertFalse(result);
        assertNull(handler.getInputFilePath());
        assertNull(handler.getMazePath());
    }

    @Test
    public void testParseOnlyPathOption() {
        // test with only path option
        InputHandler handler = InputHandler.getInstance();
        String[] args = { "-p", "FFLRF" };

        boolean result = handler.parseArgs(args);

        assertTrue(result);
        assertNull(handler.getInputFilePath());
        assertEquals("FFLRF", handler.getMazePath());
    }
}