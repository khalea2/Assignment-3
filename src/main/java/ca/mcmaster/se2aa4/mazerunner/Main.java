package ca.mcmaster.se2aa4.mazerunner;

import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        System.out.println("Starting Maze Runner");

        // use the Singleton instance of InputHandler
        InputHandler inputHandler = InputHandler.getInstance();

        if (!inputHandler.parseArgs(args)) {
            logger.error("Failed to parse command-line arguments.");
            return;
        }

        String inputFilePath = inputHandler.getInputFilePath();
        String inputPath = inputHandler.getMazePath();
        String method = inputHandler.getMethod();

        if (inputFilePath == null) {
            logger.error("Failed to read File Path.");
            return;
        }

        Maze maze = new Maze(inputFilePath);

        // create explorer with the specified strategy
        Explorer explorer = new Explorer(maze, method);

        if (inputPath == null) {
            logger.info("No path provided, solving maze.");
        } else {
            System.out.println("Solving maze with path");
            System.out.println("Starting maze at: " + Arrays.toString(maze.getLeftOpening()));
            Path path = new Path(inputPath);

            System.out.println("Inputted canonical path: " + path.getFormattedInputtedPath());
            System.out.println("Inputted factorized path: " + path.getFactorizedInputtedPath());

            MazeValidator mazeValidator = new MazeValidator(maze, explorer, path);
            if (mazeValidator.getIsValid()) {
                System.out.println("Maze solved successfully with inputted path.");
            } else {
                System.out.println("Maze not solved with inputted path!");
                System.out.println("Maze runner stopped at: " + Arrays.toString(explorer.getCurrentPosition()));
            }
            return;
        }

        try {
            System.out.println("Starting maze at: " + Arrays.toString(maze.getLeftOpening()));
            System.out.println("Starting " + (method.equals("tremaux") ? "Tremaux algorithm" : "right hand rule"));
            logger.info("**** Computing path");

            // use the exploreMaze method that uses the strategy
            explorer.exploreMaze();
            List<String> moves = explorer.getPathSteps();
            Path path = new Path(moves);

            System.out.println("Maze solved!");
            System.out.println("Final canonical path: " + path.getFormattedPath());
            System.out.println("Final factorized path: " + path.getFactorizedPath());

        } catch (Exception e) {
            logger.error("/!\\ An error has occurred /!\\ Error:{}", e.getMessage());
        }

        logger.info("** End of MazeRunner");
    }
}
