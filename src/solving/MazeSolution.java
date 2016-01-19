package solving;

import java.util.ArrayList;
import maze.MazeCell;

/**
 * Created by Jacob on 2016-01-18.
 */
public class MazeSolution {
    
    private ArrayList<MazeCell> path;
    private int numCellsVisited, numSteps;

    public MazeSolution(ArrayList<MazeCell> path, int numCellsVisited, int numSteps) {
        this.path = path;
        this.numCellsVisited = numCellsVisited;
        this.numSteps = numSteps;
        
    }

    @Override
    public String toString() {
        return "Length of Path: " + path.size() + "\nNumber of Cells Visited: " + numCellsVisited + "\nNumber of Steps Taken: " + numSteps;
    }
    
}
