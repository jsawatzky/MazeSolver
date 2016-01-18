package solving;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import maze.Maze;
import maze.MazeCell;

/**
 * Created by Jacob on 2016-01-17.
 */
public class AStarSolver extends SolvingAlgorithm {
    
    private PriorityQueue<MazeCell> openSet;
    private HashMap<MazeCell, Double> score = new HashMap<>();
    private HashMap<MazeCell, MazeCell> cameFrom = new HashMap<>();

    public AStarSolver(Maze maze) {
        super(maze);
        openSet = new PriorityQueue<>(new AStarComparator(maze));
    }

    @Override
    public void run() {
        
        openSet.add(maze.getStart());
        score.put(maze.getStart(), 0.0);
        maze.getStart().state = MazeCell.VISITED;
        
        while (!openSet.isEmpty()) {
            
            MazeCell cur = openSet.poll();
            
            if (cur.equals(maze.getEnd())) {
                createPath(cur);
            }
            
            cur.state = MazeCell.VISITED;
            
            for (MazeCell neighbor: maze.getUnvisitedNeighbors(cur)) {
                
                double tentativeScore = maze.getDistanceBetween(cur, neighbor);
                
                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                } else if (tentativeScore >= score.get(neighbor)) {
                    continue;
                }
                
                cameFrom.put(neighbor, cur);
                score.put(neighbor, tentativeScore);
                
            }
            
        }
        
    }
    
    private void createPath(MazeCell from) {
        
        from.state = MazeCell.IN_PATH;
        while (cameFrom.containsKey(from)) {
            from = cameFrom.get(from);
            from.state = MazeCell.IN_PATH;
        }
        
    }
    
    private class AStarComparator implements Comparator<MazeCell> {
        
        private Maze maze;

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
