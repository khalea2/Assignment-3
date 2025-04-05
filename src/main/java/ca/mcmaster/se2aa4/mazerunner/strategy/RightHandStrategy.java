package ca.mcmaster.se2aa4.mazerunner.strategy;

import ca.mcmaster.se2aa4.mazerunner.Maze;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * implementation of the right-hand rule maze exploration strategy
 */
public class RightHandStrategy implements ExplorationStrategy {

    private static final Logger logger = LogManager.getLogger();

    // direction: 0=right, 1=down, 2=left, 3=up
    private int direction = 0;
    private int[] currentPos;
    private List<String> moves;

    @Override
    public List<String> explore(Maze maze, int[] startPos, int[] endPos) {
        logger.info("Starting right-hand rule exploration from: ({}, {})", startPos[0], startPos[1]);

        this.currentPos = new int[] { startPos[0], startPos[1] };
        this.moves = new ArrayList<>();

        // continue exploration until we reach the end position
        while (!hasReachedEnd(endPos)) {
            if (canMoveRight(maze)) {
                turnRight();
                moveForward(maze);
            } else if (canMoveForward(maze)) {
                moveForward(maze);
            } else if (canMoveLeft(maze)) {
                turnLeft();
                moveForward(maze);
            } else {
                // for dead-ends, turn around and move forward (u-turns)
                turnAround();
                moveForward(maze);
            }
        }

        logger.info("Right-hand rule exploration completed at: ({}, {})", currentPos[0], currentPos[1]);
        logger.info("Total moves: {}", moves.size());

        return moves;
    }

    @Override
    public String getName() {
        return "Right Hand Rule";
    }

    private boolean hasReachedEnd(int[] endPos) {
        return currentPos[0] == endPos[0] && currentPos[1] == endPos[1];
    }

    private boolean canMoveForward(Maze maze) {
        int newX = currentPos[0];
        int newY = currentPos[1];

        if (direction == 0) {
            newX++; // move right
        } else if (direction == 1) {
            newY++; // move down
        } else if (direction == 2) {
            newX--; // move left
        } else if (direction == 3) {
            newY--; // move up
        }

        return isValidMove(maze, newX, newY);
    }

    private boolean canMoveRight(Maze maze) {
        int originalDirection = direction;
        direction = (direction + 1) % 4; // temporarily rotate right
        boolean canMove = canMoveForward(maze);
        direction = originalDirection; // restore original direction
        return canMove;
    }

    private boolean canMoveLeft(Maze maze) {
        int originalDirection = direction;
        direction = (direction + 3) % 4; // temporarily rotate left
        boolean canMove = canMoveForward(maze);
        direction = originalDirection; // restore original direction
        return canMove;
    }

    private void moveForward(Maze maze) {
        int newX = currentPos[0];
        int newY = currentPos[1];

        if (direction == 0) {
            newX++; // move right
        } else if (direction == 1) {
            newY++; // move down
        } else if (direction == 2) {
            newX--; // move left
        } else if (direction == 3) {
            newY--; // move up
        }

        if (isValidMove(maze, newX, newY)) {
            currentPos = new int[] { newX, newY };
            moves.add("F");
        }
    }

    private void turnRight() {
        direction = (direction + 1) % 4;
        moves.add("R");
        logger.trace("Turning right, new direction: {}", direction);
    }

    private void turnLeft() {
        direction = (direction + 3) % 4;
        moves.add("L");
        logger.trace("Turning left, new direction: {}", direction);
    }

    private void turnAround() {
        direction = (direction + 2) % 4;
        moves.add("L");
        moves.add("L");
        logger.trace("Turning around, new direction: {}", direction);
    }

    private boolean isValidMove(Maze maze, int x, int y) {
        boolean inBoundary = x >= 0 && x < maze.getCols() && y >= 0 && y < maze.getRows();
        boolean isPath = inBoundary && maze.getGridAt(x, y) != '#';
        return isPath;
    }
}