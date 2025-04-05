package ca.mcmaster.se2aa4.mazerunner.strategy;

import ca.mcmaster.se2aa4.mazerunner.Maze;
import java.util.List;

/**
 * strategy interface for different maze exploration algorithms
 */
public interface ExplorationStrategy {

    /**
     * explores the maze using a specific algorithm
     * 
     * @param maze     the maze to be explored
     * @param startPos the starting position in the maze
     * @param endPos   the ending position in the maze
     * @return a list of moves (F, L, R) that solve the maze
     */
    List<String> explore(Maze maze, int[] startPos, int[] endPos);

    /**
     * gets the name of the strategy
     * 
     * @return the strategy name
     */
    String getName();
}