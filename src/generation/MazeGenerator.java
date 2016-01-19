package generation;

import java.util.ArrayList;
import java.util.Random;
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
    
    private int xSize, ySize, zSize;
    private ArrayList<MazeCell> frontier;
    
    private final JPanel canvas;
    private boolean animate;
    private int speed;
    
    private final JProgressBar progressBar;

    public MazeGenerator(int xSize, int ySize, int zSize, JPanel canvas, boolean animate, int speed, JProgressBar progress) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
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
        
        maze = new Maze(xSize, ySize, zSize, canvas);
        
        thread = new Thread(this);
        
        thread.start();
        
        return maze;
        
    }

    @Override
    public void run() {
        
        running = true;
        
        frontier = new ArrayList<>();
        
        progressBar.setMaximum(xSize*ySize*zSize);
        
        if (animate && running) {
            maze.render();
            sleep(1000/speed);
        } else if (!running) {
            return;
        }

        int xStart = r.nextInt(xSize);
        int yStart = r.nextInt(ySize);
        int zStart = r.nextInt(zSize);
        MazeCell start = maze.grid[xStart][yStart][zStart];

        start.state = MazeCell.IN;
        progressBar.setValue(1);

        addToFronteir(maze.getOutNeighbors(start));

        if (animate && running) {
            maze.render();
            sleep(1000/speed);
        } else if (!running) {
            return;
        }

        while (!frontier.isEmpty() && running) {

            MazeCell cur = frontier.remove(r.nextInt(frontier.size()));
            cur.state = MazeCell.SELECTED;
            
            if (animate && running) {
                maze.render();
                sleep(1000/speed);
            } else if (!running) {
                return;
            }

            ArrayList<MazeCell> neighbors = maze.getInNeighbors(cur);

            MazeCell other = neighbors.get(r.nextInt(neighbors.size()));

            Direction dir = Direction.getDirectionBetweenAdjacent(cur, other);

            cur.addDirection(dir);
            other.addDirection(dir.getOpposite());
            
            addToFronteir(maze.getOutNeighbors(cur));
            cur.state = MazeCell.IN;
            progressBar.setValue(progressBar.getValue()+1);

            if (animate && running) {
                maze.render();
                sleep(1000/speed);
            } else if (!running) {
                return;
            }

        }

        if (running) {
        
            maze.complete = true;
            
            maze.render();
            
        }

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

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public void setAnimate(boolean animate) {
        this.animate = animate;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public void setzSize(int zSize) {
        this.zSize = zSize;
    }
    
}
