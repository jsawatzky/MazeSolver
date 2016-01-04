package generation;

import java.util.ArrayList;
import java.util.Random;
import maze.Maze;

public class MazeGenerator {
    
    private Random r = new Random();
    
    public static Maze generate(int xSize, int ySize) {
        
        Maze maze = new Maze(xSize, ySize);
        
        ArrayList<int[]> frontier = new ArrayList<>();
        
        return maze;
        
    }
    
}
