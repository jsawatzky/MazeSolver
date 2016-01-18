/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package solving;

import javax.swing.JProgressBar;
import maze.Maze;

/**
 *
 * @author sawaj6311
 */
public abstract class SolvingAlgorithm {

    protected boolean running = false;
    
    protected boolean animate;
    protected int speed;
    protected JProgressBar progress;

    public SolvingAlgorithm(boolean animate, int speed, JProgressBar progress) {
        
        this.animate = animate;
        this.speed = speed;
        this.progress = progress;
        
    }
    
    public abstract void solve(Maze maze);
    
    protected static void sleep(int ms) {
        
        try {
            Thread.sleep(ms);
        } catch (Exception e) {}
        
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setProgress(JProgressBar progress) {
        this.progress = progress;
    }

    @Override
    public abstract String toString();
    
    
    
}
