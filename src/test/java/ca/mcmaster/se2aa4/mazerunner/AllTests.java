package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class AllTests {

    // ====== Additional Path Tests ======

    @Test
    public void testEmptyPath() {
        // Test creating a path with empty list
        List<String> moves = Arrays.asList();
        Path path = new Path(moves);

        assertEquals("", path.getFormattedPath());
        assertEquals("", path.getFactorizedPath());
    }

    @Test
    public void testSingleMovePath() {
        // Test with a single move
        List<String> moves = Arrays.asList("F");
        Path path = new Path(moves);

        assertEquals("F", path.getFormattedPath());
        assertEquals("F", path.getFactorizedPath());
    }

    @Test
    public void testComplexPath() {
        // Test with a more complex path
        List<String> moves = Arrays.asList("F", "F", "L", "L", "F", "F", "F", "R", "R", "F", "F");
        Path path = new Path(moves);

        assertEquals("FF LL FFF RR FF", path.getFormattedPath());
        assertEquals("2F 2L 3F 2R 2F", path.getFactorizedPath());
    }

    @Test
    public void testMixedPathFormat() {
        // Test with mixed spacing in the input path
        String inputPath = "2F  L   3F R";
        Path path = new Path(inputPath);

        assertEquals("FF L FFF R", path.getFormattedInputtedPath());
    }

    // ====== Additional InputHandler Tests ======

    @Test
    public void testParseEmptyArgs() {
        // Test with empty arguments
        InputHandler handler = new InputHandler();
        String[] args = {};

        boolean result = handler.parseArgs(args);

        // Might return true with empty args (succeeds but no options set)
        assertTrue(result);
        assertNull(handler.getInputFilePath());
        assertNull(handler.getMazePath());
    }

    @Test
    public void testParseInvalidOption() {
        // Test with an invalid option
        InputHandler handler = new InputHandler();
        String[] args = { "-x", "something" };

        boolean result = handler.parseArgs(args);

        // Commons CLI appears to return false for unknown options
        assertFalse(result);
        assertNull(handler.getInputFilePath());
        assertNull(handler.getMazePath());
    }

    @Test
    public void testParseOnlyPathOption() {
        // Test with only path option
        InputHandler handler = new InputHandler();
        String[] args = { "-p", "FFLRF" };

        boolean result = handler.parseArgs(args);

        assertTrue(result);
        assertNull(handler.getInputFilePath());
        assertEquals("FFLRF", handler.getMazePath());
    }
}