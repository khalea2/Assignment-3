package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class PathTest {

    @Test
    public void testPathFormatting() {
        // Test that the path gets formatted correctly with spaces
        List<String> moves = Arrays.asList("F", "F", "F", "L", "F", "F", "R", "F");
        Path path = new Path(moves);

        assertEquals("FFF L FF R F", path.getFormattedPath());
    }

    @Test
    public void testPathFactorization() {
        // Test that repeated moves get factorized correctly
        List<String> moves = Arrays.asList("F", "F", "F", "L", "F", "F", "R", "F");
        Path path = new Path(moves);

        assertEquals("3F L 2F R F", path.getFactorizedPath());
    }

    @Test
    public void testInputPathParsing() {
        // Test that an input string gets parsed correctly
        String inputPath = "3F L 2F R F";
        Path path = new Path(inputPath);

        assertEquals("FFF L FF R F", path.getFormattedInputtedPath());
    }
}