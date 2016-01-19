package solving;

import maze.Maze;
import maze.MazeCell;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JProgressBar;

/**
 * Created by Jacob on 2016-01-16.
 */
public class RecursiveBacktracker extends SolvingAlgorithm {

    //Fields
    private final Random r = new Random();

    private ArrayList<MazeCell> path;
    
    //Constructor
    public RecursiveBacktracker(boolean animate, int speed, JProgressBar progress) {
        super(animate, speed, progress);
    }

    //Solves the given maze
    @Override
    public MazeSolution solve(Maze maze) {
        
        running = true;
        
        progress.setMaximum((int) maze.getDistanceFromEnd(maze.getStart()));
        
        numCellsVisited = 0;
        numSteps = 0;
        
        path = new ArrayList<>();

        //Get the start cel and its neighbors
        MazeCell cur = maze.getStart();
        ArrayList<MazeCell> unvisitedNeighbors = maze.getUnvisitedNeighbors(cur);
        cur.state = MazeCell.SELECTED;
        progress.setValue((int) maze.getDistanceFromEnd(maze.getStart())-(int) maze.getDistanceFromEnd(cur));
        numCellsVisited++;
        
        //Animate if the user wants it and kill if thread was stopped
        if (animate && running) {
            maze.render();
            sleep(1000/speed);
        } else if (!running) {
            return null;
        }

        //Loop until there are no more options
        while (!cur.equals(maze.getStart()) || unvisitedNeighbors.size() > 0) {

            if (unvisitedNeighbors.size() > 0) {
                
                //Add the current cell to the path and pick a random neighbor
                cur.state = MazeCell.IN_PATH;
                path.add(cur);
                cur = unvisitedNeighbors.get(r.nextInt(unvisitedNeighbors.size()));
                
                //Return solution if at end
                if (cur.equals(maze.getEnd())) {
                    path.add(cur);
                    cur.state = MazeCell.IN_PATH;
                    progress.setValue((int) maze.getDistanceFromEnd(maze.getStart()));
                    running = false;
                    return new MazeSolution(path, numCellsVisited, numSteps);
                }
                
                cur.state = MazeCell.SELECTED;
                numCellsVisited++;
                
            } else {
                
                //Go back to last cell
                cur.state = MazeCell.VISITED;
                cur = path.remove(path.size()-1);
                cur.state = MazeCell.SELECTED;
                
            }
            
            //Update neighbors
            unvisitedNeighbors = maze.getUnvisitedNeighbors(cur);
            
            progress.setValue((int) maze.getDistanceFromEnd(maze.getStart())-(int) maze.getDistanceFromEnd(cur));
            
            numSteps++;
            
            //Animate if the user wants it and kill if thread was stopped
            if (animate && running) {
                maze.render();
                sleep(1000/speed);
            } else if (!running) {
                return null;
            }

        }
        
        running = false;
        
        return null;

    }

    @Override
    public String toString() {
        return "Recursive Backtracker";
    }

}
