package generation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JProgressBar;
import maze.Maze;
import maze.MazeCell;
import util.Direction;

public class MazeGenerator implements Runnable {
    
    private final Random r = new Random();
    
    private Thread thread;
    
    private Maze maze;
    
    private int xSize, ySize;
    private ArrayList<MazeCell> frontier = new ArrayList<>();
    
    private Graphics g;
    private int x, y, width, height;
    private boolean animate;
    private int speed;
    
    private JProgressBar progressBar;

    public MazeGenerator(int xSize, int ySize, Graphics g, int x, int y, int width, int height, boolean animate, int speed, JProgressBar progress) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.g = g;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.animate = animate;
        this.speed = speed;
        this.progressBar = progress;
    }
    
    public Maze generate() {
        
        if (thread != null) {
            thread.interrupt();
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
        
        maze = new Maze(xSize, ySize);
        
        progressBar.setMaximum(xSize*ySize);
        
        if (animate) {
            maze.render(g, x, y, width, height);
            sleep(1000/speed);
        }

        int xStart = r.nextInt(xSize);
        int yStart = r.nextInt(ySize);
        MazeCell start = maze.grid[xStart][yStart];

        start.state = MazeCell.IN;
        progressBar.setValue(1);

        addToFronteir(maze.getOutOfMazeNeighbors(start));

        if (animate) {
            maze.render(g, x, y, width, height);
            sleep(1000/speed);
        }

        while (!frontier.isEmpty()) {

            MazeCell cur = frontier.remove(r.nextInt(frontier.size()));
            cur.state = MazeCell.SELECTED;
            
            if (animate) {
                maze.render(g, x, y, width, height);
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

            if (animate) {
                maze.render(g, x, y, width, height);
                sleep(1000/speed);
            }

        }

        maze.render(g, x, y, width, height);
        
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
