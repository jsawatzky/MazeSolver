package generation;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JProgressBar;
import maze.Maze;
import maze.MazeCell;
import util.Direction;

public class MazeGenerator {
    
    private Random r = new Random();
    
    private ArrayList<MazeCell> frontier;
    
    public Maze generate(int xSize, int ySize, Graphics g, int x, int y, int width, int height, boolean animate, int speed, JProgressBar progress) {
        
        Maze maze = new Maze(xSize, ySize);
        
        progress.setMaximum(xSize*ySize);
        
        if (animate) {
            maze.render(g, x, y, width, height);
            sleep(1000/speed);
        }
        
        frontier = new ArrayList<>();
        
        int xStart = r.nextInt(xSize);
        int yStart = r.nextInt(ySize);
        MazeCell start = maze.grid[xStart][yStart];
        
        start.state = MazeCell.IN;
        progress.setValue(1);
        
        addToFronteir(maze.getOutOfMazeNeighbors(start));
        
        if (animate) {
            maze.render(g, x, y, width, height);
            sleep(1000/speed);
        }
        
        while (!frontier.isEmpty()) {
            
            MazeCell cur = frontier.remove(r.nextInt(frontier.size()));
            addToFronteir(maze.getOutOfMazeNeighbors(cur));
            cur.state = MazeCell.IN;
            progress.setValue(progress.getValue()+1);
            
            ArrayList<MazeCell> neighbors = maze.getInMazeNeighbors(cur);
                
            MazeCell other = neighbors.get(r.nextInt(neighbors.size()));

            Direction dir = Direction.getDirectionBetween(cur, other);

            cur.addDirection(dir);
            other.addDirection(dir.getOpposite());
            
            if (animate) {
                maze.render(g, x, y, width, height);
                sleep(1000/speed);
            }
            
        }
        
        maze.render(g, x, y, width, height);
        
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
    
}
