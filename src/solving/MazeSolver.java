package solving;

import java.util.ArrayList;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
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
    private final JTextArea results;

    public MazeSolver(boolean animate, int speed, JProgressBar progressBar, JTextArea results) {
        
        this.animate = animate;
        this.speed = speed;
        this.progressBar = progressBar;
        this.results = results;
        
        algorithms.add(new RecursiveBacktracker(animate, speed, progressBar));
        algorithms.add(new AStar(animate, speed, progressBar));
        
    }
    
    public void solve(Maze maze, SolvingAlgorithm algorithm) {
        
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
        
        curMaze.reset();
        
        MazeSolution solution = curAlgorithm.solve(curMaze);
        
        curMaze.render();
        
        if (solution != null) {
            results.setText(solution.toString());
        } else {
            results.setText("No Solution Found!");
        }
        
        running = false;
        
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (curAlgorithm != null) {
            curAlgorithm.setRunning(running);
        }
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
