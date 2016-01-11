/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solving;

import maze.Maze;

/**
 *
 * @author sawaj6311
 */
public interface SolvingAlgorithm extends Runnable {
    
    MazeSolution solve(Maze maze);
    
}
