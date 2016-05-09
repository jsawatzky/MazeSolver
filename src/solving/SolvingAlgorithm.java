/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solving;

import maze.Maze;

import javax.swing.*;

/**
 *
 * @author sawaj6311
 */
public abstract class SolvingAlgorithm {

    //Fields
    protected boolean running = false;
    
    protected boolean animate;
    protected int speed;
    protected JProgressBar progress;

    protected int numCellsVisited, numSteps;

    //Constructor
    public SolvingAlgorithm(boolean animate, int speed, JProgressBar progress) {
        
        this.animate = animate;
        this.speed = speed;
        this.progress = progress;
        
    }
    
    //Abstract method to be implemented
    public abstract MazeSolution solve(Maze maze);
    
    //Pause the thread
    protected static void sleep(int ms) {
        
        try {
            Thread.sleep(ms);
        } catch (Exception e) {}
        
    }

    //Getters and Setters

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //Get the name of the algorithm
    @Override
    public abstract String toString();
    
    
    
}
