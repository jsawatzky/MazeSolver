package solving;

import maze.Maze;
import maze.MazeCell;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;

/**
 * Created by Jacob on 2016-01-16.
 */
public class RecursiveBacktracker extends SolvingAlgorithm {

    private final Random r = new Random();

    private ArrayList<MazeCell> path = new ArrayList<>();
    
    private JPanel canvas;

    public RecursiveBacktracker(Maze maze, JPanel canvas) {
        super(maze);
        this.canvas = canvas;
    }

    @Override
    public void run() {

        path.add(maze.getStart());

        ArrayList<MazeCell> unvisitedNeighbors = maze.getUnvisitedNeighbors(maze.getStart());

        MazeCell cur = unvisitedNeighbors.get(r.nextInt(unvisitedNeighbors.size()));
        cur.state = MazeCell.SELECTED;
        maze.getStart().state = MazeCell.VISITED;
        
        //maze.render(canvas, 0);
        //sleep(100);

        while (!cur.equals(maze.getStart()) || unvisitedNeighbors.size() > 0) {

            unvisitedNeighbors = maze.getUnvisitedNeighbors(cur);

            if (unvisitedNeighbors.size() > 0) {
                cur.state = MazeCell.IN_PATH;
                path.add(cur);
                //TODO: Optimize by chosing direction based on direction to end
                cur = unvisitedNeighbors.get(r.nextInt(unvisitedNeighbors.size()));
                if (cur.equals(maze.getEnd())) {
                    return;
                }
                cur.state = MazeCell.SELECTED;
            } else {
                cur.state = MazeCell.VISITED;
                cur = path.remove(path.size()-1);
                cur.state = MazeCell.SELECTED;
            }
            
            //maze.render(canvas, 0);
            //sleep(100);

        }

    }
    
    private static void sleep(int ms) {
        
        try {
            Thread.sleep(ms);
        } catch (Exception e) {}
        
    }

}
