package solving;

import java.util.ArrayList;
import javax.swing.JProgressBar;
import maze.Maze;

public class MazeSolver implements Runnable {
    
    private Thread thread;
    private volatile boolean running = false;
    
    private ArrayList<SolvingAlgorithm> algorithms = new ArrayList<>();
    private SolvingAlgorithm curAlgorithm;
    
    private Maze curMaze;
    
    private boolean animate;
    private int speed;
    
    private final JProgressBar progressBar;

    public MazeSolver(boolean animate, int speed, JProgressBar progressBar) {
        
        this.animate = animate;
        this.speed = speed;
        this.progressBar = progressBar;
        
        algorithms.add(new RecursiveBacktracker(animate, speed, progressBar));
        algorithms.add(new AStar(animate, speed, progressBar));
        
    }
    
    public void solve(Maze maze, SolvingAlgorithm algorithm) {
        
        if (!maze.complete) {
            return;
        }
        
        if (running) {
            running = false;
            curAlgorithm.setRunning(false);
            try {
                thread.join();
            } catch (InterruptedException ex) {}
        }
        
        curAlgorithm = algorithm;
        curAlgorithm.setAnimate(animate);
        curAlgorithm.setSpeed(speed);
        
        curMaze = maze;
        
        thread = new Thread(this);
        
        thread.start();
        
    }
    
    @Override
    public void run() {
        
        running = true;
        
        curAlgorithm.solve(curMaze);
        
        curMaze.render();
        
        running = false;
        
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
        if (curAlgorithm != null) {
            curAlgorithm.setSpeed(speed);
        }
    }
    
    public void setAnimate(boolean animate) {
        this.animate = animate;
        if (curAlgorithm != null) {
            curAlgorithm.setAnimate(animate);
        }
    }

    public ArrayList<SolvingAlgorithm> getAlgorithms() {
        return algorithms;
    }
    
}
