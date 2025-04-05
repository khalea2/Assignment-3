package ca.mcmaster.se2aa4.mazerunner.strategy;

import ca.mcmaster.se2aa4.mazerunner.Maze;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * implementation of Tremaux's algorithm for maze exploration
 */
public class TremauxStrategy implements ExplorationStrategy {

    private static final Logger logger = LogManager.getLogger();

    // direction: 0=right, 1=down, 2=left, 3=up
    private int direction = 0;
    private int[] currentPos;
    private List<String> moves;

    // tracking visited passages (key: x,y, value: number of times visited)
    private Map<String, Integer> visited;

    @Override
    public List<String> explore(Maze maze, int[] startPos, int[] endPos) {
        logger.info("Starting Tremaux exploration from: ({}, {})", startPos[0], startPos[1]);

        this.currentPos = new int[] { startPos[0], startPos[1] };
        this.moves = new ArrayList<>();
        this.visited = new HashMap<>();

        // mark starting position as visited
        markVisited(startPos[0], startPos[1]);

        while (!hasReachedEnd(endPos)) {
            // get all possible directions we can move
            List<Integer> possibleDirections = getUnvisitedDirections(maze);

            if (possibleDirections.isEmpty()) {
                // if there are no unvisited passages, backtrack through least visited
                List<Integer> leastVisitedDirections = getLeastVisitedDirections(maze);
                if (!leastVisitedDirections.isEmpty()) {
                    // turn to the direction of the least visited passage
                    turnToDirection(leastVisitedDirections.getFirst());
                    moveForward(maze);
                } else {
                    // we're stuck with no way out (shouldn't happen in a proper maze)
                    logger.error("Exploration failed: no possible moves from ({}, {})", currentPos[0], currentPos[1]);
                    break;
                }
            } else {
                // if there are unvisited passages, explore them
                int nextDirection = possibleDirections.getFirst();
                turnToDirection(nextDirection);
                moveForward(maze);
            }
        }

        logger.info("Tremaux exploration completed at: ({}, {})", currentPos[0], currentPos[1]);
        logger.info("Total moves: {}", moves.size());

        return moves;
    }

    @Override
    public String getName() {
        return "Tremaux";
    }

    private boolean hasReachedEnd(int[] endPos) {
        return currentPos[0] == endPos[0] && currentPos[1] == endPos[1];
    }

    private void turnToDirection(int newDirection) {
        // calculate the number of right turns needed to face the new direction
        int turns = (newDirection - direction + 4) % 4;

        if (turns == 1) {
            // single right turn
            turnRight();
        } else if (turns == 2) {
            // turn around
            turnAround();
        } else if (turns == 3) {
            // single left turn
            turnLeft();
        }
        // if turns == 0, we're already facing the right direction
    }

    private List<Integer> getUnvisitedDirections(Maze maze) {
        List<Integer> directions = new ArrayList<>();

        // check all four directions for unvisited valid paths
        for (int dir = 0; dir < 4; dir++) {
            int[] newPos = getPositionInDirection(dir);
            if (isValidMove(maze, newPos[0], newPos[1]) && !isVisited(newPos[0], newPos[1])) {
                directions.add(dir);
            }
        }

        return directions;
    }

    private List<Integer> getLeastVisitedDirections(Maze maze) {
        List<Integer> directions = new ArrayList<>();
        int minVisits = Integer.MAX_VALUE;

        // find the minimum number of visits across all valid directions
        for (int dir = 0; dir < 4; dir++) {
            int[] newPos = getPositionInDirection(dir);
            if (isValidMove(maze, newPos[0], newPos[1])) {
                int visits = getVisitCount(newPos[0], newPos[1]);
                if (visits < minVisits) {
                    minVisits = visits;
                    directions.clear();
                    directions.add(dir);
                } else if (visits == minVisits) {
                    directions.add(dir);
                }
            }
        }

        return directions;
    }

    private int[] getPositionInDirection(int dir) {
        int newX = currentPos[0];
        int newY = currentPos[1];

        if (dir == 0) {
            newX++; // right
        } else if (dir == 1) {
            newY++; // down
        } else if (dir == 2) {
            newX--; // left
        } else if (dir == 3) {
            newY--; // up
        }

        return new int[] { newX, newY };
    }

    private void moveForward(Maze maze) {
        int[] newPos = getPositionInDirection(direction);

        if (isValidMove(maze, newPos[0], newPos[1])) {
            currentPos = newPos;
            markVisited(newPos[0], newPos[1]);
            moves.add("F");
            logger.trace("Moved to: ({}, {})", currentPos[0], currentPos[1]);
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

    private String getPositionKey(int x, int y) {
        return x + "," + y;
    }

    private void markVisited(int x, int y) {
        String key = getPositionKey(x, y);
        visited.put(key, visited.getOrDefault(key, 0) + 1);
    }

    private boolean isVisited(int x, int y) {
        return visited.containsKey(getPositionKey(x, y));
    }

    private int getVisitCount(int x, int y) {
        return visited.getOrDefault(getPositionKey(x, y), 0);
    }
}