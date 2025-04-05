package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MazeTest {

    private File testMazeFile;

    @BeforeEach
    public void setup() throws IOException {
        // Create a temporary test maze file
        testMazeFile = File.createTempFile("test_maze", ".txt");
        FileWriter writer = new FileWriter(testMazeFile);
        writer.write("""
                #####
                #   #
                #   #
                 ###
                #####
                """);
        writer.close();
    }

    @Test
    public void testMazeLoading() {
        // Test that a maze gets loaded correctly
        Maze maze = new Maze(testMazeFile.getAbsolutePath());

        // Verify dimensions (5 rows, 5 columns)
        assertEquals(5, maze.getRows());
        assertEquals(5, maze.getCols());
    }

    @Test
    public void testMazeGridContent() {
        // Test that the maze grid content is correct
        Maze maze = new Maze(testMazeFile.getAbsolutePath());

        // Check a few grid positions
        assertEquals('#', maze.getGridAt(0, 0)); // Wall at top-left
        assertEquals(' ', maze.getGridAt(2, 1)); // Passage
        assertEquals('#', maze.getGridAt(4, 4)); // Wall at bottom-right
    }

    @Test
    public void testMazeOpenings() {
        // Test that maze openings (entrance/exit) are detected correctly
        Maze maze = new Maze(testMazeFile.getAbsolutePath());

        // Verify left opening is null (since it's at index 0,3, not on left edge)
        assertNotNull(maze.getLeftOpening());

        // We expect openings to be [0, 3] for left and [4, 3] for right in our test
        // maze
        assertArrayEquals(new int[] { 0, 3 }, maze.getLeftOpening());
    }
}