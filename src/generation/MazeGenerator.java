package generation;

import java.util.ArrayList;
import java.util.Random;
import maze.Maze;
import maze.MazeCell;
import util.Direction;

public class MazeGenerator {
    
    private static Random r = new Random();
    
    public static Maze generate(int xSize, int ySize) {
        
        System.out.println(2|16);
        
        Maze maze = new Maze(xSize, ySize);
        
        ArrayList<MazeCell> frontier = new ArrayList<>();
        
        int xStart = r.nextInt(xSize);
        int yStart = r.nextInt(ySize);
        MazeCell start = maze.grid[xStart][yStart];
        
        start.inMaze = true;
        
        frontier.addAll(maze.getOutOfMazeNeighbors(start));
        
        while (!frontier.isEmpty()) {
            
            MazeCell cur = frontier.remove(r.nextInt(frontier.size()));
            frontier.addAll(maze.getOutOfMazeNeighbors(cur));
            cur.inMaze = true;
            
            ArrayList<MazeCell> neighbors = maze.getInMazeNeighbors(cur);
                
            MazeCell other = neighbors.get(r.nextInt(neighbors.size()));

            Direction dir = Direction.getDirectionBetween(cur, other);

            cur.addDirection(dir);
            other.addDirection(dir.getOpposite());
            
        }
        
        return maze;
        
    }
    
}
