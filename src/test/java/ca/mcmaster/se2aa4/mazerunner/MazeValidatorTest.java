package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeValidatorTest {

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
    public void testMazeValidatorCreation() {
        // Test that a MazeValidator can be created
        Maze maze = new Maze(testMazeFile.getAbsolutePath());
        Explorer explorer = new Explorer(maze);

        // Create a path with a list of moves
        List<String> moves = new ArrayList<>();
        moves.add("F");
        Path path = new Path(moves);

        // Create a validator
        MazeValidator validator = new MazeValidator(maze, explorer, path);

        // Just test that the validator was created successfully
        assertNotNull(validator);
    }

    @Test
    public void testMazeValidatorGetIsValid() {
        // Test that getIsValid method returns a boolean
        Maze maze = new Maze(testMazeFile.getAbsolutePath());
        Explorer explorer = new Explorer(maze);

        // Create a path with a list of moves
        List<String> moves = new ArrayList<>();
        moves.add("F");
        Path path = new Path(moves);

        // Create a validator
        MazeValidator validator = new MazeValidator(maze, explorer, path);

        // The value doesn't matter as much as that it returns a boolean type
        assertFalse(validator.getIsValid()); // Expecting false since F won't solve the maze
    }
}