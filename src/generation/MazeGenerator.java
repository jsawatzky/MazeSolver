package generation;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import maze.Maze;
import maze.MazeCell;
import util.Direction;

public class MazeGenerator implements Runnable {
    
    //Fields
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

    //Constructor
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
        
        //Kill the currently running thread (If there is one)
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ex) {}
        }
        
        //Create a new maze object
        maze = new Maze(xSize, ySize, zSize, canvas);
        
        //Create and start the new thread
        thread = new Thread(this);
        
        thread.start();
        
        //Return the new maze
        return maze;
        
    }

    @Override
    public void run() {
        
        running = true;
        
        frontier = new ArrayList<>();
        
        progressBar.setMaximum(xSize*ySize*zSize);
        
        //Animate if the user wants it and kill if thread was stopped
        if (animate && running) {
            maze.render();
            sleep(1000/speed);
        } else if (!running) {
            return;
        }

        //Pick a random start point
        int xStart = r.nextInt(xSize);
        int yStart = r.nextInt(ySize);
        int zStart = r.nextInt(zSize);
        MazeCell start = maze.grid[xStart][yStart][zStart];

        //Add the start to the maze
        start.state = MazeCell.IN;
        progressBar.setValue(1);

        //Add the starts neighbors to the frontier
        addToFronteir(maze.getOutNeighbors(start));

        //Animate if the user wants it and kill if thread was stopped
        if (animate && running) {
            maze.render();
            sleep(1000/speed);
        } else if (!running) {
            return;
        }

        while (!frontier.isEmpty() && running) {

            //Get a random cell from the frontier
            MazeCell cur = frontier.remove(r.nextInt(frontier.size()));
            cur.state = MazeCell.SELECTED;
            
            //Animate if the user wants it and kill if thread was stopped
            if (animate && running) {
                maze.render();
                sleep(1000/speed);
            } else if (!running) {
                return;
            }

            //Pick a random neighbor of the current cell that is already in the maze
            ArrayList<MazeCell> neighbors = maze.getInNeighbors(cur);
            MazeCell other = neighbors.get(r.nextInt(neighbors.size()));

            //Make a traversable path between the two cells
            Direction dir = Direction.getDirectionBetweenAdjacent(cur, other);
            cur.addDirection(dir);
            other.addDirection(dir.getOpposite());
            
            //Add the current cells neighbors that aren't in the maze to the maze
            addToFronteir(maze.getOutNeighbors(cur));
            
            //Add the cell to the maze.
            cur.state = MazeCell.IN;
            progressBar.setValue(progressBar.getValue()+1);

            //Animate if the user wants it and kill if thread was stopped
            if (animate && running) {
                maze.render();
                sleep(1000/speed);
            } else if (!running) {
                return;
            }

        }

        if (running) {
        
            //Set maze as complete and render.
            maze.complete = true;
            
            maze.render();
            
            running = false;
            
        }

    }
    
    private void addToFronteir(ArrayList<MazeCell> cells) {
        
        //Add all given cells to frontier
        for (MazeCell cell: cells) {
            cell.state = MazeCell.FRONTIER;
            frontier.add(cell);
        }
        
    }
    
    //Pause the thread
    private static void sleep(int ms) {
        
        try {
            Thread.sleep(ms);
        } catch (Exception e) {}
        
    }

    //Getters and Setters
    
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

    public boolean isRunning() {
        return running;
    }
    
}
