package solving;

import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import maze.Maze;

public class MazeSolver {
    
    private Thread thread;
    
    private Maze maze;
    private MazeSolution solution;
    
    private SolvingAlgorithm algorithm;
    
    private JPanel canvas;
    private Graphics g;
    private boolean animate;
    private int speed;
    
    private JProgressBar progressBar;

    public MazeSolver(JPanel canvas, Graphics g, boolean animate, int speed, JProgressBar progressBar) {
        this.canvas = canvas;
        this.g = g;
        this.animate = animate;
        this.speed = speed;
        this.progressBar = progressBar;
    }
    
    public MazeSolution solve(Maze maze, SolvingAlgorithm algorithm) {
        
        solution = new MazeSolution();
        
        
        
        return solution;
        
    }
    
}
