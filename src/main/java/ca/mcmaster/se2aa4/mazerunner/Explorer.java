package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.strategy.ExplorationStrategy;
import ca.mcmaster.se2aa4.mazerunner.strategy.RightHandStrategy;
import ca.mcmaster.se2aa4.mazerunner.strategy.StrategyFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

// handles maze traversal and path finding logic
public class Explorer {
    private static final Logger logger = LogManager.getLogger();

    private Maze maze;
    private int[] currentPos;
    private int[] start;
    private int[] end;
    private List<String> moves;
    // direction represents current facing: 0=right, 1=down, 2=left, 3=up
    private int direction = 0;

    // the exploration strategy to use
    private ExplorationStrategy strategy;

    public Explorer(Maze mazeMap) {
        this(mazeMap, "righthand"); // default to right-hand rule
    }

    public Explorer(Maze mazeMap, String strategyName) {
        this.maze = mazeMap;
        this.currentPos = mazeMap.getLeftOpening();
        this.start = currentPos;
        this.end = mazeMap.getRightOpening();
        this.moves = new ArrayList<>();
        this.direction = 0;
        this.strategy = StrategyFactory.createStrategy(strategyName);
    }

    public void setStrategy(ExplorationStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(String strategyName) {
        this.strategy = StrategyFactory.createStrategy(strategyName);
    }

    // explores the maze using the selected strategy
    public void exploreMaze() {
        if (currentPos == null) {
            logger.error("No valid starting point found in the maze.");
            return;
        }

        logger.info("Starting exploration at position: ({}, {})", currentPos[0], currentPos[1]);
        this.moves = strategy.explore(maze, start, end);
        logger.info("Exploration completed with {} moves", moves.size());
    }

    // for backward compatibility, use the right-hand rule strategy
    public void exploreRightHandRule() {
        setStrategy("righthand");
        exploreMaze();
    }

    // validates and executes a sequence of moves from input
    public boolean solveMazeFromInput(String input) {
        int stepsTaken = 0; // tracks number of instructions executed
        if (input == null || input.isEmpty()) {
            logger.error("Input string is empty or null.");
            return false;
        }

        // Reset position to start
        this.currentPos = this.start.clone();
        this.direction = 0;

        logger.info("Starting maze exploration from position: ({}, {})", currentPos[0], currentPos[1]);
        for (char instruction : input.toCharArray()) { // process each navigation command
            stepsTaken++;
            if (instruction == 'F') {
                if (!canMoveForward()) {
                    logger.warn("Hit a wall during move or out of bounds.");
                    break;
                }
                moveForward();
            } else if (instruction == 'R') {
                turnRight();
            } else if (instruction == 'L') {
                turnLeft();
            } else {
                logger.warn("Invalid move character encountered: {}", instruction);
                return false;
            }

            if (reachedEnd() && stepsTaken == input.length()) { // verify solution reaches end exactly
                logger.info("Maze solved! Reached the end at position: ({}, {})", currentPos[0], currentPos[1]);
                return true;
            } else if (reachedEnd() && stepsTaken != input.length()) { // detect if path continues past maze exit
                logger.warn("Reached end but path is still continuing!");
                return false;
            }
        }
        logger.warn("Finished processing input, but did not reach the end. End position is: ({},{})", currentPos[0],
                currentPos[1]);
        return false;
    }

    private boolean reachedEnd() {
        return currentPos[0] == end[0] && currentPos[1] == end[1];
    }

    // helper methods for checking possible moves in different directions
    private boolean canMoveForward() {
        int newX = currentPos[0];
        int newY = currentPos[1];

        if (direction == 0) {
            newX++; // Move right
        } else if (direction == 1) {
            newY++; // Move down
        } else if (direction == 2) {
            newX--; // Move left
        } else if (direction == 3) {
            newY--; // Move up
        }
        return isValidMove(newX, newY);
    }

    private void moveForward() {
        int newX = currentPos[0];
        int newY = currentPos[1];

        if (direction == 0) {
            newX++; // Move right
        } else if (direction == 1) {
            newY++; // Move down
        } else if (direction == 2) {
            newX--; // Move left
        } else if (direction == 3) {
            newY--; // Move up
        }

        if (isValidMove(newX, newY)) {
            currentPos = new int[] { newX, newY };
        }
    }

    private void turnRight() {
        direction = (direction + 1) % 4;
    }

    private void turnLeft() {
        direction = (direction + 3) % 4;
    }

    private boolean isValidMove(int x, int y) {
        boolean inBoundary = x >= 0 && x < maze.getCols() && y >= 0 && y < maze.getRows();
        boolean isPath = inBoundary && maze.getGridAt(x, y) != '#';
        return inBoundary && isPath;
    }

    public int[] getCurrentPosition() {
        return currentPos;
    }

    public List<String> getPathSteps() {
        return moves;
    }
}
