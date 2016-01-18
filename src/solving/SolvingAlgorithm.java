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
public abstract class SolvingAlgorithm implements Runnable {

    protected Maze maze;
    protected volatile boolean running = false;

    public SolvingAlgorithm(Maze maze) {
        this.maze = maze;
    }
}
