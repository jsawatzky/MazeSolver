package solving;

import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import maze.Maze;

public class MazeSolver {
    
    private Thread thread;
    
    private Maze maze;
    
    private SolvingAlgorithm algorithm;
    
    private final JPanel canvas;
    private boolean animate;
    private int speed;
    
    private final JProgressBar progressBar;

    public MazeSolver(JPanel canvas, boolean animate, int speed, JProgressBar progressBar) {
        this.canvas = canvas;
        this.animate = animate;
        this.speed = speed;
        this.progressBar = progressBar;
    }
    
    public void solve(Maze maze, SolvingAlgorithm algorithm) {
        
        
        
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setAnimate(boolean animate) {
        this.animate = animate;
    }
    
}
