package solving;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import javax.swing.JProgressBar;
import maze.Maze;
import maze.MazeCell;

/**
 * Created by Jacob on 2016-01-17.
 */
public class AStar extends SolvingAlgorithm {
    
    private PriorityQueue<MazeCell> openSet;
    private HashMap<MazeCell, Double> score;
    private HashMap<MazeCell, MazeCell> cameFrom;
    
    private int numCellsVisited, numSteps;

    public AStar(boolean animate, int speed, JProgressBar progress) {
        super(animate, speed, progress);
    }

    @Override
    public MazeSolution solve(Maze maze) {
        
        running = true;
        
        progress.setMaximum((int) maze.getDistanceFromEnd(maze.getStart()));
        
        numCellsVisited = 0;
        numSteps = 0;
        
        openSet = new PriorityQueue<>((maze.grid.length*maze.grid[0].length)/2, new AStarComparator(maze));
        score = new HashMap<>();
        cameFrom = new HashMap<>();
        
        openSet.add(maze.getStart());
        score.put(maze.getStart(), 0.0);
        
        while (!openSet.isEmpty()) {
            
            MazeCell cur = openSet.poll();
            
            progress.setValue((int) maze.getDistanceFromEnd(maze.getStart())-(int) maze.getDistanceFromEnd(cur));
            
            if (cur.equals(maze.getEnd())) {
                progress.setValue((int) maze.getDistanceFromEnd(maze.getStart()));
                running = false;
                return new MazeSolution(createPath(cur), numCellsVisited, numSteps);
            }
            
            cur.state = MazeCell.VISITED;
            numCellsVisited++;
            
            if (animate && running) {
                maze.render();
                sleep(1000/speed);
            } else if (!running) {
                return null;
            }
            
            for (MazeCell neighbor: maze.getUnvisitedNeighbors(cur)) {
                
                numSteps++;
                
                double tentativeScore = score.get(cur) + 1;
                
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentativeScore >= score.get(neighbor)) {
                    continue;
                }
                
                cameFrom.put(neighbor, cur);
                score.put(neighbor, tentativeScore);
                
                if (animate && running) {
                maze.render();
                sleep(1000/speed);
                } else if (!running) {
                    return null;
                }
                
            }
            
        }
        
        running = false;
        
        return null;
        
    }
    
    private ArrayList<MazeCell> createPath(MazeCell from) {
        
        ArrayList<MazeCell> path = new ArrayList<>();
        
        from.state = MazeCell.IN_PATH;
        path.add(from);
        while (cameFrom.containsKey(from)) {
            from = cameFrom.get(from);
            from.state = MazeCell.IN_PATH;
            path.add(from);
        }
        
        return path;
        
    }

    @Override
    public String toString() {
        return "A* Pathfinder";
    }
    
    private class AStarComparator implements Comparator<MazeCell> {
        
        private final Maze maze;

        public AStarComparator(Maze maze) {
            
            this.maze = maze;
            
        }
        

        @Override
        public int compare(MazeCell o1, MazeCell o2) {
            
            double dist1 = maze.getDistanceFromEnd(o1);
            double dist2 = maze.getDistanceFromEnd(o2);
            
            if (dist1 < dist2) {
                return -1;
            } else if (dist1 == dist2) {
                return 0;
            } else {
                return 1;
            }
            
        }
        
        
    }
    
}
