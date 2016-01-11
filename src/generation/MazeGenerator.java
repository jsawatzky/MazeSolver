package generation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import maze.Maze;
import maze.MazeCell;
import util.Direction;

public class MazeGenerator implements Runnable {
    
    private final Random r = new Random();
    
    private Thread thread;
    private volatile boolean running = false;
    
    private Maze maze;
    
    private int xSize, ySize;
    private ArrayList<MazeCell> frontier;
    
    private JPanel canvas;
    private Graphics g;
    private int x, y, width, height;
    private boolean animate;
    private int speed;
    
    private JProgressBar progressBar;

    public MazeGenerator(int xSize, int ySize, JPanel canvas, boolean animate, int speed, JProgressBar progress) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.canvas = canvas;
        this.animate = animate;
        this.speed = speed;
        this.progressBar = progress;
    }
    
    public Maze generate() {
        
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ex) {}
        }
        
        thread = new Thread(this);
        
        thread.start();
        
        return maze;
        
    }
    
    private void addToFronteir(ArrayList<MazeCell> cells) {
        
        for (MazeCell cell: cells) {
            cell.state = MazeCell.FRONTIER;
            frontier.add(cell);
        }
        
    }
    
    private static void sleep(int ms) {
        
        try {
            Thread.sleep(ms);
        } catch (Exception e) {}
        
    }

    @Override
    public void run() {
        
        running = true;
        
        maze = new Maze(xSize, ySize);
        
        frontier = new ArrayList<>();
        
        progressBar.setMaximum(xSize*ySize);
        
        if (animate && running) {
            maze.render(canvas);
            sleep(1000/speed);
        }

        int xStart = r.nextInt(xSize);
        int yStart = r.nextInt(ySize);
        MazeCell start = maze.grid[xStart][yStart];

        start.state = MazeCell.IN;
        progressBar.setValue(1);

        addToFronteir(maze.getOutOfMazeNeighbors(start));

        if (animate && running) {
            maze.render(canvas);
            sleep(1000/speed);
        }

        while (!frontier.isEmpty() && running) {

            MazeCell cur = frontier.remove(r.nextInt(frontier.size()));
            cur.state = MazeCell.SELECTED;
            
            if (animate && running) {
                maze.render(canvas);
                sleep(1000/speed);
            }

            ArrayList<MazeCell> neighbors = maze.getInMazeNeighbors(cur);

            MazeCell other = neighbors.get(r.nextInt(neighbors.size()));

            Direction dir = Direction.getDirectionBetween(cur, other);

            cur.addDirection(dir);
            other.addDirection(dir.getOpposite());
            
            addToFronteir(maze.getOutOfMazeNeighbors(cur));
            cur.state = MazeCell.IN;
            progressBar.setValue(progressBar.getValue()+1);

            if (animate && running) {
                maze.render(canvas);
                sleep(1000/speed);
            }

        }

        if (running) {
            
            maze.render(canvas);
        
            maze.complete = true;
            
        }

    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
    }
    
}
