package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExplorerTest {

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
    public void testExplorerInitialization() {
        // Test that an explorer gets initialized correctly
        Maze maze = new Maze(testMazeFile.getAbsolutePath());
        Explorer explorer = new Explorer(maze);

        // Verify initial position is at the maze entrance
        assertArrayEquals(maze.getLeftOpening(), explorer.getCurrentPosition());

        // Verify path steps list starts empty
        assertTrue(explorer.getPathSteps().isEmpty());
    }

    @Test
    public void testPathStepsList() {
        // Test that the path steps list is initialized
        Maze maze = new Maze(testMazeFile.getAbsolutePath());
        Explorer explorer = new Explorer(maze);

        // Verify path steps list is initialized (even if empty)
        assertNotNull(explorer.getPathSteps());
    }

    @Test
    public void testRightHandRuleExploration() {
        // Test that right-hand rule exploration generates a path
        String testMazePath = getClass().getClassLoader().getResource("test_maze_straight.txt").getFile();
        Maze maze = new Maze(testMazePath);
        Explorer explorer = new Explorer(maze);

        explorer.exploreRightHandRule();

        // Verify that some path steps were generated
        assertFalse(explorer.getPathSteps().isEmpty());
    }

    @Test
    public void testSolveMazeFromInputValidPath() {
        // Test solving maze with a valid path input
        String testMazePath = getClass().getClassLoader().getResource("test_maze_straight.txt").getFile();
        Maze maze = new Maze(testMazePath);
        Explorer explorer = new Explorer(maze);

        // For a straight maze, we expect a path of "F" to work
        boolean solved = explorer.solveMazeFromInput("FFF");

        // Should reach the end position
        assertArrayEquals(maze.getRightOpening(), explorer.getCurrentPosition());
    }
}